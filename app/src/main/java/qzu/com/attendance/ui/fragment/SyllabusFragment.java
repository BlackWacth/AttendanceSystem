package qzu.com.attendance.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;


import qzu.com.attendance.R;
import qzu.com.attendance.entity.Course;
import qzu.com.attendance.entity.SyllabusCourse;
import qzu.com.attendance.ui.adapter.SyllabusAdapter;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.DividerGridItemDecoration;

/**
 * 课程表
 */
public class SyllabusFragment extends BaseFragment {

    /**课程表这里只显示到周五，共6列 */
    public static final int COLUMN_NUM = 6;
    public static final String DEFUALT_CONTENT = "";
    /**
     * <color name="cyan500">#00bcd4</color>
     <color name="light_blue_a200">#40c4ff</color>
     <color name="green500">#259b24</color>
     <color name="indigo_a200">#536dfe</color>
     <color name="amber500">#ffc107</color>
     <color name="blue500">#5677fc</color>
     <color name="deep_purple_a200">#7c4dff</color>
     */
    public static final int[] BACKGROUND_COLOR = {R.color.amber500,
            R.color.cyan500,
            R.color.light_blue_a200,
            R.color.green500,
            R.color.indigo_a200 ,
            R.color.blue500 ,
            R.color.deep_purple_a200
    };

    private RecyclerView mRecyclerView;
    private SyllabusAdapter mAdapter;
    private SyllabusCourse[] mData = new SyllabusCourse[36];
    private Random mRandom;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_syllabus;
    }

    @Override
    protected void initView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.syllabus_recycler_view);
    }

    @Override
    protected void initData() {
        mRandom = new Random();
        initGrid();
        addCourse();
    }

    private void initRecyclerView() {
        mAdapter = new SyllabusAdapter(getActivity(), mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN_NUM));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }

    /**
     * 计算课程在表格中的位置
     * @param week 周几（1，2,3,4,5）
     * @param node 第几节（1,2,3,4,5）
     * @return
     */
    private int calculateIndexInRecycler(int week, int node) {
        return node * COLUMN_NUM + week;
    }

    /**
     * 初始化课程表，只有排头，星期几和第几节，没有任何课程。
     */
    private void initGrid() {

        for(int i = 0; i < mData.length; i++) {
            mData[i] = new SyllabusCourse(DEFUALT_CONTENT, null, SyllabusCourse.ITEM_TYPE.ITEM_TYPE_TEXT.ordinal());
        }

        mData[1].setContent("周一");
        mData[2].setContent("周二");
        mData[3].setContent("周三");
        mData[4].setContent("周四");
        mData[5].setContent("周五");

        mData[6].setContent("1, 2节");
        mData[12].setContent("3, 4节");
        mData[18].setContent("5, 6节");
        mData[24].setContent("7, 8节");
        mData[30].setContent("9, 10节");

    }

    private void addCourse() {
        mData[13] = new SyllabusCourse(DEFUALT_CONTENT, new Course(), SyllabusCourse.ITEM_TYPE.ITEM_TYPE_CARD.ordinal());
        mData[13].setColor(getRandomColor());

        mData[20] = new SyllabusCourse(DEFUALT_CONTENT, new Course(), SyllabusCourse.ITEM_TYPE.ITEM_TYPE_CARD.ordinal());
        mData[20].setColor(getRandomColor());

        mData[28] = new SyllabusCourse(DEFUALT_CONTENT, new Course(), SyllabusCourse.ITEM_TYPE.ITEM_TYPE_CARD.ordinal());
        mData[28].setColor(getRandomColor());

        mData[16] = new SyllabusCourse(DEFUALT_CONTENT, new Course(), SyllabusCourse.ITEM_TYPE.ITEM_TYPE_CARD.ordinal());
        mData[16].setColor(getRandomColor());
    }

    private int getRandomColor() {
        int index = mRandom.nextInt(BACKGROUND_COLOR.length);
        return BACKGROUND_COLOR[index];
    }
}
