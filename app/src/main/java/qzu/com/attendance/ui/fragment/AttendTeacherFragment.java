package qzu.com.attendance.ui.fragment;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.entity.StudentAttendInfo;
import qzu.com.attendance.service.BluetoothTeacherService;
import qzu.com.attendance.ui.adapter.AttendTeacherAdapter;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.utils.BluetoothUtils;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;

/**
 * 考勤
 */
public class AttendTeacherFragment extends BaseFragment implements View.OnClickListener{

    /**默认签到时间10分钟 */
    public static final int DEFUALT_ATTTEND_TIME = 10 * 60 * 1000;
    /**一秒执行一次 */
    public static final int DEFUALT_EXECUTE_TIME = 1000;

    private RecyclerView mRecyclerView;
    private Button mSend, mOpen;
    
    private List<StudentAttendInfo> mList;
    private AttendTeacherAdapter mAdapter;

    private CountDownTimer mCountDownTimer;
    private String openBtnContent;

    private BluetoothTeacherService mBluetoothServer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_READ:
                    String readMsg = msg.getData().getString(Constants.EXTRA_READ_MASSAGE);
                    showToast(readMsg);
                    mBluetoothServer.write("收到");
                    break;
            }
        }
    };


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attend_teacher;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.attend_teacher_recycler_view);
        mSend = (Button) view.findViewById(R.id.attend_teacher_send);
        mOpen = (Button) view.findViewById(R.id.attend_teacher_open_bluetooth);
        openBtnContent = getResouseString(R.string.attend_teacher_open_bluetooth);
        mSend.setOnClickListener(this);
        mOpen.setOnClickListener(this);
    }
    
    @Override
    protected void initData() {
        if(mList == null) {
            mList = new ArrayList<>();
            mList.add(new StudentAttendInfo());
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
        mAdapter = new AttendTeacherAdapter(getActivity(), mList);
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

                break;
        }
    }

    /**
     * 检查蓝牙是否开启，为开启，打开蓝牙，并打开蓝牙连接服务，等待连接
     */
    private void startBluetoothService() {
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
