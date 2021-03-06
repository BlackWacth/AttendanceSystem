package qzu.com.attendance.ui.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Course;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.L;
import qzu.com.attendance.utils.Utils;

/**
 * 课程
 */
@SuppressLint("ValidFragment")
public class CourseFragment extends BaseFragment {

    private TextView mCourseName;
    private TextView mCourseAddress;
    private TextView mCourseTime;
    private TextView mCourseClass;
    private TextView mCourseTeacher;
    private TextView tv_courseTeacherName;
    private TextView mCoursePhone;
    private TextView mNoCourse;
    private LinearLayout mHaveCourse;

    private String userType;
    private String uid;
    private String sersionId;
    private String AskType = "2";

    public CourseFragment(String userType, String uid, String sersionId) {
        this.userType = userType;
        this.uid = uid;
        this.sersionId = sersionId;
    }

    public CourseFragment() {
        
    }
    

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_course;
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
        mNoCourse = (TextView) view.findViewById(R.id.course_no_course);
        mHaveCourse = (LinearLayout) view.findViewById(R.id.course_layout);
        
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
        
    }

    private void getCourse(boolean isRefresh, final PtrFrameLayout frame) {
        AApplication.mHttpMethod.getCourse(getActivity(), new SubscriberOnNextListener<Course>() {
            @Override
            public void success(Course course) {
                L.i(course.toString());
                isHasLoadedOnce = true;
                if(course.isExist()) {
                    mHaveCourse.setVisibility(View.VISIBLE);
                    mNoCourse.setVisibility(View.GONE);
                    mCourseName.setText(course.getName());
                    mCourseAddress.setText(course.getAddress());
                    mCourseTime.setText(Utils.getTime(course.getStartTime(), course.getEndTime()));
                    mCourseClass.setText(course.getClassName());
                    mCourseTeacher.setText(course.getTeacherName());
                    mCoursePhone.setText(course.getPhone());
                }else {
                    mHaveCourse.setVisibility(View.GONE);
                    mNoCourse.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void error(int code) {
                if(frame != null) {
                    frame.refreshComplete();
                }
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

}
