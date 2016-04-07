package qzu.com.attendance.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.List;

import qzu.com.attendance.utils.L;

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        FragmentManager fm = getSupportFragmentManager();
//        int index = requestCode >> 16;
//        if(index != 0) {
//            index --;
//            if(fm.getFragments() == null  || index < 0 || index > fm.getFragments().size()){
//                L.i("Activity result fragment index out of range: 0x" + Integer.toHexString(requestCode));
//                return;
//            }
//
//            Fragment fragment = fm.getFragments().get(index);
//            if(fragment == null){
//                L.i("Activity result no fragment exists for index: 0x"  + Integer.toHexString(requestCode));
//            }else {
//                handleResult(fragment, requestCode, resultCode, data);
//            }
//        }
//        return ;
//    }
//
//    private void handleResult(Fragment fragment, int requestCode, int resultCode, Intent data) {
//        fragment.onActivityResult(requestCode&0xffff, resultCode, data);
//        List<Fragment> list = fragment.getChildFragmentManager().getFragments();
//        if(list != null) {
//            for(Fragment f : list) {
//                handleResult(f, requestCode, resultCode, data);
//            }
//        }
//    }
}
