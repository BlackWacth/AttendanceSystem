package qzu.com.attendance.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.util.List;

import qzu.com.attendance.R;
import qzu.com.attendance.entity.AttendClassInfo;
import qzu.com.attendance.entity.StudentAttendInfo;

/**
 * 
 */
public class AttendInfoAdapter extends BaseAdapter{

    private Context mContext;
    private List<StudentAttendInfo> mList;

    public AttendInfoAdapter(Context mContext, List<StudentAttendInfo> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        AttendClassHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.card_attend_teacher, parent, false);
            holder = new AttendClassHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.card_icon);
            holder.name = (TextView) convertView.findViewById(R.id.card_student_name);
            holder.sex = (TextView) convertView.findViewById(R.id.card_sex);
            holder.studentId = (TextView) convertView.findViewById(R.id.card_student_id);
            holder.className = (TextView) convertView.findViewById(R.id.card_class_name);
            holder.phone = (TextView) convertView.findViewById(R.id.card_phone);
            holder.status = (RadioGroup) convertView.findViewById(R.id.card_radio_group);

            convertView.setTag(holder);
        }else {
            holder = (AttendClassHolder) convertView.getTag();
        }
        
        return convertView;
    }
    
    class AttendClassHolder {
        ImageView icon;
        TextView name;
        TextView sex; 
        TextView studentId;
        TextView className;
        TextView phone;
        RadioGroup status;
    }
}
