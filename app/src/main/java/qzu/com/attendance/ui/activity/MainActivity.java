package qzu.com.attendance.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.RadioGroup;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.entity.Teacher;
import qzu.com.attendance.http.subscriber.ProgressSubscriber;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.ui.base.BaseActivity;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.L;
import qzu.com.attendance.utils.ViewClick;
import rx.functions.Action1;

/**
 * 登录界面
 */
public class MainActivity extends BaseActivity{
    
    private TextInputEditText mUserName;

    private TextInputEditText mPassword;

    private RadioGroup mRadioGroup;
    
    private Toolbar mToolbar;

    private Button mLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mUserName = (TextInputEditText) findViewById(R.id.user_name);
        mPassword = (TextInputEditText) findViewById(R.id.password);
        mRadioGroup = (RadioGroup) findViewById(R.id.type_radio_group);
        mLogin = (Button) findViewById(R.id.login);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {
        ViewClick.preventShake(mLogin, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                login();
            }
        });
    }

    private void login(){
        String userName = mUserName.getText().toString();
        String password = mPassword.getText().toString();
        if(TextUtils.isEmpty(userName)){
            showToast("学号不能为空");
            return ;
        }
        if(TextUtils.isEmpty(password)){
            showToast("密码不能为空");
            return;
        }
        ProgressSubscriber subscriber = null;
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.type_teacher:
                AApplication.USER_TYPE = AApplication.TYPE_TEACHER;
                subscriber = new ProgressSubscriber(this, new SubscriberOnNextListener<Teacher>() {
                    @Override
                    public void success(Teacher teacher) {
                        L.i(teacher.toString());
                        mStartActivity(DetailActivity.class, AApplication.TYPE_TEACHER, teacher);
                        finish();
                    }

                    @Override
                    public void error(int code) {
                        errorTip(code);
                    }
                });
                break;
            
            case R.id.type_student:
                AApplication.USER_TYPE = AApplication.TYPE_STUDENT;
                subscriber = new ProgressSubscriber(this, new SubscriberOnNextListener<Student>() {
                    @Override
                    public void success(Student student) {
                        L.i(student.toString());
                        mStartActivity(DetailActivity.class, AApplication.TYPE_STUDENT, student);
                        finish();
                    }

                    @Override
                    public void error(int code) {
                        errorTip(code);
                    }
                });
                break;

            default:
                AApplication.USER_TYPE = AApplication.TYPE_STUDENT;
                break;
        }
        AApplication.mHttpMethod.login(subscriber, AApplication.USER_TYPE, Integer.parseInt(userName), password);
    }
    
    private void errorTip(int code){
        String errorText = "";
        switch (code) {
            case 1:
                errorText = "帐号不存在";
                break;

            case 2:
                errorText = "密码错误";
                break;

            case 3:
                errorText = "登陆类型错误";
                break;

            case 4:
                errorText = "登陆输入的帐号密码错误";
                break;
        }
        MDialog.showDialog(this, errorText);
    }
    
}
