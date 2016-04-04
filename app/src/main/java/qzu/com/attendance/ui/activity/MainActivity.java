package qzu.com.attendance.ui.activity;

import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import qzu.com.attendance.R;
import qzu.com.attendance.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    
    
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
        mLogin.setOnClickListener(this);
        setSupportActionBar(mToolbar);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                mStartActivity(DetailActivity.class);
                break;
        }
    }
}
