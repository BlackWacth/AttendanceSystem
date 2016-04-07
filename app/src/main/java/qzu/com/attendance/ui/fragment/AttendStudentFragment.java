package qzu.com.attendance.ui.fragment;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.service.BluetoothStudentService;
import qzu.com.attendance.service.BluetoothServer;
import qzu.com.attendance.ui.activity.DevicesActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;

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
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attend_student;
    }

    @Override
    protected void initView(View view) {
        mCourseName = (TextView) view.findViewById(R.id.course_name);
        mCourseAddress = (TextView) view.findViewById(R.id.course_address);
        mCourseTime = (TextView) view.findViewById(R.id.course_time);
        mCourseClass = (TextView) view.findViewById(R.id.course_class);
        mCourseTeacher = (TextView) view.findViewById(R.id.course_teacher);
        tv_courseTeacherName = (TextView) view.findViewById(R.id.course_teacher_name);
        mCoursePhone = (TextView) view.findViewById(R.id.course_phone);
        mStudentAttend = (Button) view.findViewById(R.id.attend_student_status);
        mStudentAttend.setOnClickListener(this);

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
