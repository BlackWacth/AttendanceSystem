package qzu.com.attendance.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Teacher;
import qzu.com.attendance.http.HttpMethod;
import qzu.com.attendance.http.api.LoginApi;
import qzu.com.attendance.ui.base.BaseActivity;
import qzu.com.attendance.utils.L;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import rx.Subscriber;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    
    
    private TextInputEditText mUserName;

    private TextInputEditText mPassword;

    private RadioGroup mRadioGroup;
    
    private Toolbar mToolbar;

    private Button mLogin;

    private HttpMethod mHttpMethod;

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
        mLogin.setOnClickListener(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {
        mHttpMethod = HttpMethod.getInstance();
    }

    private void getData() {
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

        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.type_teacher:
                AApplication.USER_TYPE = AApplication.TYPE_TEACHER;
                break;

            case R.id.type_student:
                AApplication.USER_TYPE = AApplication.TYPE_STUDENT;
                break;

            default:
                AApplication.USER_TYPE = AApplication.TYPE_STUDENT;
                break;
        }
//        mStartActivity(DetailActivity.class);

        mHttpMethod.Login(new Subscriber<Teacher>() {
            @Override
            public void onCompleted() {
                L.i("------completed----");
            }

            @Override
            public void onError(Throwable e) {
                L.i("onError : "+ e.getMessage());
            }

            @Override
            public void onNext(Teacher teacher) {
                L.i(teacher.toString());
            }
        }, AApplication.TYPE_TEACHER, 1010001, "123456");


    }

    private void login(int userID, String pwd){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                getData();
                break;
        }
    }
}
