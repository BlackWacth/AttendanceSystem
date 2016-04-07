package qzu.com.attendance.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import qzu.com.attendance.R;
import qzu.com.attendance.entity.SyllabusCourse;

/**
 * 课程表适配器
 */
public class SyllabusAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context mContext;
    private LayoutInflater mInflater;
    private SyllabusCourse[] syllabus;

    public SyllabusAdapter(Context mContext, SyllabusCourse[] syllabus) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
        this.syllabus = syllabus;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;
        if(viewType == SyllabusCourse.ITEM_TYPE.ITEM_TYPE_TEXT.ordinal()) {
            holder = new SyllabusTextHolder(mInflater.inflate(R.layout.normal_item_syllabus, parent, false));
        }else if(viewType == SyllabusCourse.ITEM_TYPE.ITEM_TYPE_CARD.ordinal()){
            holder = new SyllabusCardHolder(mInflater.inflate(R.layout.card_item_syllabus, parent, false));
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        return position >= syllabus.length || position < 0 ? SyllabusCourse.ITEM_TYPE.ITEM_TYPE_TEXT.ordinal() : syllabus[position].getType();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SyllabusCourse item = syllabus[position];
        if(holder instanceof SyllabusTextHolder) {
            if(item != null && item.getContent() != null) {
                ((SyllabusTextHolder) holder).content.setText(item.getContent());
            }
        }else if(holder instanceof SyllabusCardHolder) {
            if(item != null && item.getCourse() != null){
                SyllabusCardHolder cardHolder = (SyllabusCardHolder) holder;
                if(item.getColor() != 0){
                    cardHolder.cardView.setCardBackgroundColor(mContext.getResources().getColor(item.getColor()));
                }
                cardHolder.courseName.setText("数据结构");
                cardHolder.address.setText("8栋301");
            }
        }
    }

    @Override
    public int getItemCount() {
        return syllabus == null ? 0 : syllabus.length;
    }

    static class SyllabusTextHolder extends RecyclerView.ViewHolder{

        TextView content;

        public SyllabusTextHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.normal_item_syllabus_text);
        }
    }

    static class SyllabusCardHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView courseName;
        TextView address;

        public SyllabusCardHolder(View itemView) {
            super(itemView);
            courseName = (TextView) itemView.findViewById(R.id.item_sysabus_course_name);
            address = (TextView) itemView.findViewById(R.id.item_sysabus_address);
            cardView = (CardView) itemView.findViewById(R.id.item_syllabus_card_view);
        }
    }
}
