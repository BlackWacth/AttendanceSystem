package qzu.com.attendance.ui.base;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.BaseEntity;
import rx.Observer;
import rx.functions.Action0;
import rx.functions.Action1;


public abstract class BaseActivity extends AppCompatActivity {
    
    public static final String EXTRA_LOGIN_DATA = "EXTRA_LOGIN_DATA";
    public static final String EXTRA_LOGIN_TYPE = "EXTRA_LOGIN_TYPE";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        init();
    }

    protected abstract int getLayoutId();
    
    protected void init(){
        initView();
        initData();
    }

    protected abstract void initView();
    
    protected abstract void initData();
    
    public void mStartActivity(Class<?> cls, int type, BaseEntity entity){
        Intent intent =new Intent(this, cls);
        intent.putExtra(EXTRA_LOGIN_TYPE, type);
        intent.putExtra(EXTRA_LOGIN_DATA, entity);
        startActivity(intent);
    }
    
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
    public String getResouseString(int id) {
        return getResources().getString(id);
    }

}
