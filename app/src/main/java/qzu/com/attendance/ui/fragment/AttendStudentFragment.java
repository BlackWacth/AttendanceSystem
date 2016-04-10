package qzu.com.attendance.ui.fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Attend;
import qzu.com.attendance.entity.Course;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.service.BluetoothStudentService;
import qzu.com.attendance.service.BluetoothServer;
import qzu.com.attendance.ui.activity.DevicesActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;
import qzu.com.attendance.utils.Utils;

/**
 * 签到
 */
public class AttendStudentFragment extends BaseFragment implements View.OnClickListener{

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
    
    private BluetoothServer mBluetoothServer;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.MESSAGE_READ:
                    String readMsg = msg.getData().getString(Constants.EXTRA_READ_MASSAGE);
                    showToast(readMsg);
                    attentEnabled(false);
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
        mStudentAttend.setOnClickListener(this);
        noStudent = (TextView) view.findViewById(R.id.attend_student_no_text);
        noStudent.setVisibility(View.GONE);
        mContent = (LinearLayout) view.findViewById(R.id.attend_student_content_layout);
        mContent.setVisibility(View.VISIBLE);

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
    }

    private void getCourse() {
        AApplication.mHttpMethod.getCourse(getActivity(), new SubscriberOnNextListener<Course>() {
            @Override
            public void success(Course course) {
                L.i("------" + course.toString());
                isHasLoadedOnce = true;
                if(course.isExist()) {
                    mContent.setVisibility(View.VISIBLE);
                    noStudent.setVisibility(View.GONE);
                    mCourseName.setText(course.getName());
                    mCourseAddress.setText(course.getAddress());
                    mCourseTime.setText(Utils.getTime(course.getStartTime(), course.getEndTime()));
                    mCourseClass.setText(course.getClassName());
                    mCourseTeacher.setText(course.getTeacherName());
                    mCoursePhone.setText(course.getPhone());
                }else {
                    mContent.setVisibility(View.GONE);
                    noStudent.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void error(int code) {
                errorTip(code);
            }
        },userType, uid, sersionId, AskType);
    }

    @Override
    protected void lazyLoad() {
        getCourse();
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
                mBluetoothServer.connect(device, true);
            }
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), DevicesActivity.class);
        getActivity().startActivityForResult(intent, Constants.REQUEST_CONNECT_DEVICE);
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
}
