package qzu.com.attendance.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.entity.StudentAttendInfo;
import qzu.com.attendance.ui.adapter.AttendInfoAdapter;
import qzu.com.attendance.ui.adapter.AttendTeacherAdapter;
import qzu.com.attendance.ui.base.BaseFragment;

/**
 * 考勤
 */
public class AttendTeacherFragment extends BaseFragment {
    
    private RecyclerView mRecyclerView;
    private Button mSend;
    
    private List<StudentAttendInfo> mList;
    private AttendTeacherAdapter mAdapter;
    
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_attend_teacher;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.attend_teacher_recycler_view);
        mSend = (Button) view.findViewById(R.id.attend_teacher_send);
    }
    
    @Override
    protected void initData() {
        if(mList == null) {
            mList = new ArrayList<>();
            for(int i = 0; i < 20; i++) {
                 mList.add(new StudentAttendInfo());
            }
        }
    }
    
    public void initRecyclerView() {
        mAdapter = new AttendTeacherAdapter(getActivity(), mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }
}
