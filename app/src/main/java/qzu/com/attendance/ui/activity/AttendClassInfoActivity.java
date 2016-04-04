package qzu.com.attendance.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.entity.AttendClassInfo;
import qzu.com.attendance.ui.base.BaseActivity;

public class AttendClassInfoActivity extends BaseActivity {

    ListView mListView;
    
    private List<AttendClassInfo> mList;
    
    @Override
    protected int getLayoutId() {
        return R.layout.activity_attend_class_info;
    }

    @Override
    protected void init() {
        if(mList == null) {
            mList  = new ArrayList<>();
            for(int i = 0; i < 20; i++){
                mList.add(new AttendClassInfo());
            }
        }
        
    }

    @Override
    protected void initView() {
        
    }

    @Override
    protected void initData() {

    }
}
