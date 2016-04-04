package qzu.com.attendance.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    
    public void mStartActivity(Class<?> cls){
        startActivity(new Intent(this, cls));
    }
    
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    
    public String getResouseString(int id) {
        return getResources().getString(id);
    }
}
