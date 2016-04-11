package qzu.com.attendance.ui.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.entity.Teacher;
import qzu.com.attendance.ui.adapter.PagerAdapter;
import qzu.com.attendance.ui.base.BaseActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.fragment.AttendStudentFragment;
import qzu.com.attendance.ui.fragment.AttendTeacherFragment;
import qzu.com.attendance.ui.fragment.CourseFragment;
import qzu.com.attendance.ui.fragment.AskQuestionFragment;
import qzu.com.attendance.ui.fragment.PersionalInfoFragment;
import qzu.com.attendance.ui.fragment.SyllabusFragment;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;

public class DetailActivity extends BaseActivity {

    Toolbar mToolbar;

    TabLayout mTabLayout;

    ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;

    private long exitTime = 0;

    private List<PagerAdapter.FragmentModel> mModels;

    private int type;
    private Teacher teacher;
    private Student student;
    private String userType;
    private String uid;
    private String sersionId;

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
        getIntentData();
        String syllabus = getResouseString(R.string.syllabus);
        String course = getResouseString(R.string.course);
        String attend = "";
        String info = "";
        
        BaseFragment attendFragemnt = null;
        BaseFragment infoFragemnt = new AskQuestionFragment();
        
        if(AApplication.USER_TYPE == AApplication.TYPE_TEACHER) {
            attend = getResouseString(R.string.attend_teacher);
            info = getResouseString(R.string.info_teacher);
            attendFragemnt = new AttendTeacherFragment(userType, uid, sersionId);
            infoFragemnt = new AskQuestionFragment(userType, uid, sersionId);
        }else if(AApplication.USER_TYPE == AApplication.TYPE_STUDENT) {
            attend = getResouseString(R.string.attend_student);
            info = getResouseString(R.string.info_student);
            attendFragemnt = new AttendStudentFragment(userType, uid, sersionId);
            infoFragemnt = new PersionalInfoFragment(student);
        }

        if(mModels == null) {
            mModels = new ArrayList<>();
            mModels.add(new PagerAdapter.FragmentModel(syllabus, new SyllabusFragment(userType, uid, sersionId)));
            mModels.add(new PagerAdapter.FragmentModel(course, new CourseFragment(userType, uid, sersionId)));
            mModels.add(new PagerAdapter.FragmentModel(attend, attendFragemnt));
            mModels.add(new PagerAdapter.FragmentModel(info, infoFragemnt));
        }
        initViewPager();
    }

    /**
     *  获取登录后用户数据，以便向下传递
     */
    private void getIntentData() {
        Intent intent = getIntent();
        type = intent.getIntExtra(EXTRA_LOGIN_TYPE, AApplication.USER_TYPE);

        BaseEntity entity = (BaseEntity) intent.getSerializableExtra(EXTRA_LOGIN_DATA);
        if(type == AApplication.TYPE_TEACHER) {
            teacher = (Teacher) entity;
            userType = teacher.getUserType();
            uid = teacher.getUID();
            sersionId = teacher.getSersionId();
        }else if(type == AApplication.TYPE_STUDENT) {
            student = (Student) entity;
            userType = student.getUserType();
            uid = student.getUID();
            sersionId = student.getSersionId();
        }
        L.i("userType : " + userType);
        L.i("uid : " + uid);
        L.i("sersionId : " + sersionId);
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(1);
        mPagerAdapter = new PagerAdapter(getSupportFragmentManager(), mModels);
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        for(PagerAdapter.FragmentModel model : mModels) {
            model.getFragment().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
