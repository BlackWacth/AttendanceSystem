package qzu.com.attendance.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.ui.adapter.PagerAdapter;
import qzu.com.attendance.ui.base.BaseActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.fragment.AttendStudentFragment;
import qzu.com.attendance.ui.fragment.AttendTeacherFragment;
import qzu.com.attendance.ui.fragment.CourseFragment;
import qzu.com.attendance.ui.fragment.AskQuestionFragment;
import qzu.com.attendance.ui.fragment.PersionalInfoFragment;
import qzu.com.attendance.ui.fragment.SyllabusFragment;

public class DetailActivity extends BaseActivity {

    Toolbar mToolbar;

    TabLayout mTabLayout;

    ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    private List<PagerAdapter.FragmentModel> mModels;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.detail_tool_bar);
        mTabLayout = (TabLayout) findViewById(R.id.detail_tab_layout);
        mViewPager = (ViewPager) findViewById(R.id.detail_view_pager);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {
        String syllabus = getResouseString(R.string.syllabus);
        String course = getResouseString(R.string.course);
        String attend = "";
        String info = "";
        
        BaseFragment attendFragemnt = new AttendStudentFragment();
        BaseFragment infoFragemnt = new AskQuestionFragment();
        
        if(AApplication.USER_TYPE == AApplication.TYPE_TEACHER) {
            attend = getResouseString(R.string.attend_teacher);
            info = getResouseString(R.string.info_teacher);
            attendFragemnt = new AttendTeacherFragment();
            infoFragemnt = new AskQuestionFragment();
        }else if(AApplication.USER_TYPE == AApplication.TYPE_STUDENT) {
            attend = getResouseString(R.string.attend_student);
            info = getResouseString(R.string.info_student);
            attendFragemnt = new AttendStudentFragment();
            infoFragemnt = new PersionalInfoFragment();
        }

        if(mModels == null) {
            mModels = new ArrayList<>();
            mModels.add(new PagerAdapter.FragmentModel(syllabus, new SyllabusFragment()));
            mModels.add(new PagerAdapter.FragmentModel(course, new CourseFragment()));
            mModels.add(new PagerAdapter.FragmentModel(attend, attendFragemnt));
            mModels.add(new PagerAdapter.FragmentModel(info, infoFragemnt));
        }
    }

    private void initViewPager() {
        mPagerAdapter = new PagerAdapter(getFragmentManager(), mModels);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initViewPager();
    }
}
