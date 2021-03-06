package qzu.com.attendance.ui.fragment;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Course;
import qzu.com.attendance.http.progress.ProgressCancelListener;
import qzu.com.attendance.http.progress.ProgressDialogHandler;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.service.BluetoothStudentService;
import qzu.com.attendance.service.BluetoothServer;
import qzu.com.attendance.ui.activity.DevicesActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.BluetoothUtils;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;
import qzu.com.attendance.utils.Utils;
import qzu.com.attendance.utils.ViewClick;
import rx.functions.Action1;

/**
 * 学生签到
 */
@SuppressLint("ValidFragment")
public class AttendStudentFragment extends BaseFragment{

    private TextView mCourseName;
    private TextView mCourseAddress;
    private TextView mCourseTime;
    private TextView mCourseClass;
    private TextView mCourseTeacher;
    private TextView tv_courseTeacherName;
    private TextView mCoursePhone;
    private Button mStudentAttend;
    private LinearLayout mContent;
    private TextView noStudent;

    private String userType;
    private String uid;
    private String sersionId;
    private String AskType = "2";
    
    private BluetoothStudentService mBluetoothServer;

    private ProgressDialogHandler proGressDialogHandler;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case Constants.MESSAGE_STATE_CHANGE:
                    int state = msg.getData().getInt(Constants.EXTRA_BLUETOOTH_STATE, -1);
                    switch (state) {
                        case BluetoothServer.STATE_CONNECTED:
                            mBluetoothServer.write(uid);
                            break;

                    }
                    break;

                case Constants.MESSAGE_READ:
                    String readMsg = msg.getData().getString(Constants.EXTRA_READ_MASSAGE);
                    if(readMsg.equals(AttendTeacherFragment.ATTEND_SUCCESS)) {
                        showToast(readMsg);
                        attentEnabled(false);
                        dismissProgressDialog();
                        mBluetoothServer.stop();
                    }
                    break;
            }
        }
    };

    public AttendStudentFragment() {
        
    }

    public AttendStudentFragment(String userType, String uid, String sersionId) {
        this.userType = userType;
        this.uid = uid;
        this.sersionId = sersionId;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attend_student;
    }

    @Override
    protected void initView(View view) {
        mCourseName = (TextView) view.findViewById(R.id.attend_student_course_name);
        mCourseAddress = (TextView) view.findViewById(R.id.attend_student_course_address);
        mCourseTime = (TextView) view.findViewById(R.id.attend_student_course_time);
        mCourseClass = (TextView) view.findViewById(R.id.attend_student_course_class);
        mCourseTeacher = (TextView) view.findViewById(R.id.attend_student_course_teacher);
        tv_courseTeacherName = (TextView) view.findViewById(R.id.attend_student_course_teacher_name);
        mCoursePhone = (TextView) view.findViewById(R.id.attend_student_course_phone);
        mStudentAttend = (Button) view.findViewById(R.id.attend_student_status);

        noStudent = (TextView) view.findViewById(R.id.attend_student_no_text);
        noStudent.setVisibility(View.GONE);
        mContent = (LinearLayout) view.findViewById(R.id.attend_student_content_layout);
        mContent.setVisibility(View.VISIBLE);

        proGressDialogHandler = new ProgressDialogHandler(getContext(), true, new ProgressCancelListener() {
            @Override
            public void onCancelProgress() {
                mBluetoothServer.stop();
            }
        });

        String teacher = "";
        if(AApplication.USER_TYPE == AApplication.TYPE_TEACHER) {
            teacher = getResouseString(R.string.course_teacher_name_cls);
        }else if(AApplication.USER_TYPE == AApplication.TYPE_STUDENT) {
            teacher = getResouseString(R.string.course_teacher_name);
        }
        tv_courseTeacherName.setText(teacher);
    }
    
    @Override
    protected void initData() {
        mBluetoothServer = new BluetoothStudentService(mHandler);

        ViewClick.preventShake(mStudentAttend, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if(!BluetoothUtils.isSupportBluetooth()) {
                    showToast("该机型不支持蓝牙");
                    return;
                }
                Intent intent = new Intent(getActivity(), DevicesActivity.class);
                getActivity().startActivityForResult(intent, Constants.REQUEST_CONNECT_DEVICE);
            }
        });
    }

    private void getCourse(boolean isRefresh, final PtrFrameLayout frame) {
        AApplication.mHttpMethod.getCourse(getActivity(), new SubscriberOnNextListener<Course>() {
            @Override
            public void success(Course course) {
                L.i("------" + course.toString());
                isHasLoadedOnce = true;
                if(course.isExist()) {
                    attentEnabled(true);
                    mContent.setVisibility(View.VISIBLE);
                    noStudent.setVisibility(View.GONE);
                    mCourseName.setText(course.getName());
                    mCourseAddress.setText(course.getAddress());
                    mCourseTime.setText(Utils.getTime(course.getStartTime(), course.getEndTime()));
                    mCourseClass.setText(course.getClassName());
                    mCourseTeacher.setText(course.getTeacherName());
                    mCoursePhone.setText(course.getPhone());
                }else {
                    attentEnabled(false);
                    mContent.setVisibility(View.GONE);
                    noStudent.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void error(int code) {
                errorTip(code);
            }
        },userType, uid, sersionId, AskType, isRefresh, frame);
    }

    @Override
    protected void lazyLoad() {
        getCourse(false, null);
    }

    @Override
    public boolean checkCanDoRefresh() {
        return true;
    }

    @Override
    public void update(PtrFrameLayout frame) {
        getCourse(true, frame);
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

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
        if(mBluetoothServer != null) {
            mBluetoothServer.stop();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_CONNECT_DEVICE) {
            if(resultCode == -1) {
                BluetoothDevice device = data.getParcelableExtra(Constants.EXTRA_CONNECT_DEVICE);
                L.i(">>>> device : " + device);
                mBluetoothServer.connect(device, true);
                showProgressDialog();
            }
        }
    }

    /**
     * 改变开始考勤按钮状态
     * @param isEnabled
     */
    private void attentEnabled(boolean isEnabled){
        if(isEnabled) {
            mStudentAttend.setEnabled(true);
            mStudentAttend.setBackgroundColor(getActivity().getResources().getColor(R.color.colorPrimary));
        }else {
            mStudentAttend.setEnabled(false);
            mStudentAttend.setBackgroundColor(getActivity().getResources().getColor(R.color.gray));
        }
    }

    private void showProgressDialog() {
        proGressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
    }

    private void dismissProgressDialog() {
        proGressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
    }
}
