package qzu.com.attendance.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.entity.StudentAttendInfo;

public class AttendTeacherAdapter extends RecyclerView.Adapter<AttendTeacherAdapter.AttendTeacherHolder>{

    private Context mContext;
    private List<StudentAttendInfo> mList;

    public AttendTeacherAdapter(Context mContext, List<StudentAttendInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public AttendTeacherHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.card_attend_teacher, parent, false);
        return new AttendTeacherHolder(view);
    }

    @Override
    public void onBindViewHolder(AttendTeacherHolder holder, int position) {
        
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

    class AttendTeacherHolder extends RecyclerView.ViewHolder{

        ImageView icon;
        TextView name;
        TextView sex;
        TextView studentId;
        TextView className;
        TextView phone;
        RadioGroup status;
        
        public AttendTeacherHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.card_icon);
            name = (TextView) itemView.findViewById(R.id.card_student_name);
            sex = (TextView) itemView.findViewById(R.id.card_sex);
            studentId = (TextView) itemView.findViewById(R.id.card_student_id);
            className = (TextView) itemView.findViewById(R.id.card_class_name);
            phone = (TextView) itemView.findViewById(R.id.card_phone);
            status = (RadioGroup) itemView.findViewById(R.id.card_radio_group);
        }
    }
}
