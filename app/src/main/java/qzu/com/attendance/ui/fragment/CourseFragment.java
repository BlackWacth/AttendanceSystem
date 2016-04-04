package qzu.com.attendance.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.ui.base.BaseFragment;

/**
 * 课程
 */
public class CourseFragment extends BaseFragment {

    private TextView mCourseName;
    private TextView mCourseAddress;
    private TextView mCourseTime;
    private TextView mCourseClass;
    private TextView mCourseTeacher;
    private TextView tv_courseTeacherName;
    private TextView mCoursePhone;
    
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
}
