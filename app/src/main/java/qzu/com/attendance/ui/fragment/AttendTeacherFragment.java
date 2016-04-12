package qzu.com.attendance.ui.fragment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Attend;
import qzu.com.attendance.entity.AttendBody;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.service.BluetoothTeacherService;
import qzu.com.attendance.ui.adapter.AttendTeacherAdapter;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.BluetoothUtils;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;
import retrofit2.http.Body;

/**
 * 考勤
 */
public class AttendTeacherFragment extends BaseFragment implements View.OnClickListener{

    /**默认签到时间10分钟 */
    public static final int DEFUALT_ATTTEND_TIME = 10 * 60 * 1000;
    /**一秒执行一次 */
    public static final int DEFUALT_EXECUTE_TIME = 1000;

    public static final String ATTEND_SUCCESS = "ATTEND_SUCCESS";

    private RecyclerView mRecyclerView;
    private Button mSend, mOpen;
    private TextView noStudent;
    private List<Attend.ScheduleBean> mList;
    private AttendTeacherAdapter mAdapter;

    private CountDownTimer mCountDownTimer;
    private String openBtnContent;
    
    
    private String userType;
    private String uid;
    private String sersionId;
    private String AskType = "3";

    private AttendBody body;
    private String scheduleId;

    private BluetoothTeacherService mBluetoothServer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_READ:
                    synchronized (AttendTeacherFragment.class){
                        String uid = msg.getData().getString(Constants.EXTRA_READ_MASSAGE);
                        showToast(uid);
                        mBluetoothServer.write(ATTEND_SUCCESS);
                        changeCheck(uid);
                    }
                    break;
            }
        }
    };

    public AttendTeacherFragment() {
        
    }

    public AttendTeacherFragment(String userType, String uid, String sersionId) {
        this.userType = userType;
        this.uid = uid;
        this.sersionId = sersionId;
        body = new AttendBody();
        body.setUserType(userType);
        body.setUID(uid);
        body.setSersionId(sersionId);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attend_teacher;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.attend_teacher_recycler_view);
        mSend = (Button) view.findViewById(R.id.attend_teacher_send);
        mOpen = (Button) view.findViewById(R.id.attend_teacher_open_bluetooth);
        noStudent = (TextView) view.findViewById(R.id.attend_teacher_no_text);
        noStudent.setVisibility(View.GONE);
        
        openBtnContent = getResouseString(R.string.attend_teacher_open_bluetooth);
        mSend.setOnClickListener(this);
        mOpen.setOnClickListener(this);
    }
    
    @Override
    protected void initData() {
        if(mList == null) {
            mList = new ArrayList<>();
        }

        mBluetoothServer = new BluetoothTeacherService(mHandler);
        mCountDownTimer = new CountDownTimer(DEFUALT_ATTTEND_TIME, DEFUALT_EXECUTE_TIME) {

            @Override
            public void onTick(long millisUntilFinished) {
                int temp = (int) (millisUntilFinished / 1000);
                int m = temp / 60;
                int s = temp % 60;
                mOpen.setText("正在考勤" + "(" + m + ":" + s + ")");
            }

            @Override
            public void onFinish() {
                cancelTimer();
            }
        };
    }

    /**
     * 修改对于uid的考勤状态
     * @param uid
     */
    private void changeCheck(String uid) {
        for(Attend.ScheduleBean bean : mList) {
            if(uid.equals(bean.getStuId())) {
                bean.setCheck(0);
                break;
            }
        }
        mAdapter.notifyDataSetChanged();
    }

    private void getAttendStudent() {
        AApplication.mHttpMethod.attend(getActivity(), new SubscriberOnNextListener<Attend>() {
            @Override
            public void success(Attend attend) {
                L.i("+++++" + attend.toString());
                scheduleId = attend.getScheduleId();
                isHasLoadedOnce = true;
                if(attend.getStuCount() <= 0) {
                    noStudent.setVisibility(View.VISIBLE);
                    return;
                }
                noStudent.setVisibility(View.GONE);
                mList.addAll(attend.getSchedule());
            }

            @Override
            public void error(int code) {
                errorTip(code);
            }
        },userType, uid, sersionId, AskType);
    }

    @Override
    protected void lazyLoad() {
        getAttendStudent();
    }

    @Override
    public boolean checkCanDoRefresh() {
        return false;
    }

    @Override
    public void update(PtrFrameLayout frame) {

    }

    private void errorTip(int code){
        String errorText = "";
        switch (code) {
            case 1:
                errorText = "请求错误";
                break;

            case 2:
                errorText = "登陆验证失效";
                break;
            default:
                errorText = "未知异常";
                break;
        }
        MDialog.showDialog(getContext(), errorText);
    }

    private List<AttendBody.CheckBean> getBodyChecks() {
        List<AttendBody.CheckBean> cheks = new ArrayList<>();
        for(Attend.ScheduleBean bean: mList) {
            cheks.add(new AttendBody.CheckBean(bean.getStuId(), scheduleId, bean.getCheck()));
        }
        return cheks;
    }

    private void submit(){
        body.setChecks(getBodyChecks());
        AApplication.mHttpMethod.sublime(getContext(), new SubscriberOnNextListener<BaseEntity>() {
            @Override
            public void success(BaseEntity baseEntity) {
                showToast("提交成功");
            }

            @Override
            public void error(int code) {
                errorTip(code);
            }
        }, body);
    }

    private void cancelTimer() {
        if(mCountDownTimer != null) {
            mCountDownTimer.cancel();
            openEnabled(true);
            mOpen.setText(openBtnContent);
        }
        if(mBluetoothServer != null) {
            mBluetoothServer.stop();
        }
    }

    public void initRecyclerView() {
        mAdapter = new AttendTeacherAdapter(getActivity(), this, mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.attend_teacher_open_bluetooth:
                startBluetoothService();
                break;

            case R.id.attend_teacher_send:
                submit();
                break;
        }
    }

    /**
     * 检查蓝牙是否开启，为开启，打开蓝牙，并打开蓝牙连接服务，等待连接
     */
    private void startBluetoothService() {
        if(!BluetoothUtils.isSupportBluetooth()) {
            showToast("该机型不支持蓝牙");
            return;
        }
        if(!BluetoothUtils.isBluetoothEnabled()) {
            BluetoothUtils.openBluetooth(getActivity());
        }else {
            startServiceAndTimer();
        }
    }

    private void startServiceAndTimer() {
        mBluetoothServer.start();
        showToast("蓝牙服务开启，等待连接……");
        mCountDownTimer.start();
        openEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case BluetoothUtils.CODE_ENABLE_BLUETOOTH :
                if(resultCode == getActivity().RESULT_OK) {
                    showToast("蓝牙开启");
                    startServiceAndTimer();
                }
                break;
        }
    }

    /**
     * 改变开始考勤按钮状态
     * @param isEnabled
     */
    private void openEnabled(boolean isEnabled){
        if(isEnabled) {
            mOpen.setEnabled(true);
            mOpen.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        }else {
            mOpen.setEnabled(false);
            mOpen.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        cancelTimer();
    }
}
