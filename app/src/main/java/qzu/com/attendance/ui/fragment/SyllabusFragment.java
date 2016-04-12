package qzu.com.attendance.ui.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import java.util.Random;


import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Syllabus;
import qzu.com.attendance.entity.SyllabusCourse;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.ui.adapter.SyllabusAdapter;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.DividerGridItemDecoration;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.L;
import qzu.com.attendance.utils.Utils;

/**
 * 课程表
 */
public class SyllabusFragment extends BaseFragment {

    /**课程表这里只显示到周五，共6列 */
    public static final int COLUMN_NUM = 6;
    public static final String DEFUALT_CONTENT = "";
 
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
    
    private String userType;
    private String uid;
    private String sersionId;
    private String AskType = "1";
    
    public SyllabusFragment(String userType, String uid, String sersionId) {
        this.userType = userType;
        this.uid = uid;
        this.sersionId = sersionId;
    }

    public SyllabusFragment(){
        
    }

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
        initRecyclerView();
    }

    @Override
    protected void lazyLoad() {
        getSysllabusData();
    }

    @Override
    public boolean checkCanDoRefresh() {
        return false;
    }

    @Override
    public void update(PtrFrameLayout frame) {

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
    
    private void getSysllabusData() {
        AApplication.mHttpMethod.getSyllabus(getActivity(), new SubscriberOnNextListener<Syllabus>() {
            @Override
            public void success(Syllabus syllabus) {
                isHasLoadedOnce = true;
                L.i("syllabus --> " + syllabus.toString());
                if(syllabus.getScheduleCount() <= 0) {
                    return ;
                }
                for(Syllabus.ScheduleBean bean : syllabus.getSchedule()) {
                    int week = Utils.string2Int(bean.getWeek());
                    int node = Utils.string2Int(bean.getNode());
                    if((week > 0 && week < 6) && (node > 0 && node < 6)) {
                        L.i("week = " +  week + "  node = " + node);
                        int index = calculateIndexInRecycler(week, node);
                        mData[index] = new SyllabusCourse(DEFUALT_CONTENT, bean, SyllabusCourse.ITEM_TYPE.ITEM_TYPE_CARD.ordinal(), getRandomColor());
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void error(int code) {
                errorTip(code);
            }
        },userType, uid, sersionId, AskType);
    }

    private void initRecyclerView() {
        mAdapter = new SyllabusAdapter(getActivity(), mData);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN_NUM));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
    }

    private void errorTip(int code){
        String errorText = "";
        switch (code) {
            case 1:
                errorText = "请求错误";
                break;

            case 2:
                errorText = "登陆验证失效";
                break;
            default:
                errorText = "未知异常";
                break;
        }
        MDialog.showDialog(getContext(), errorText);
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
            mData[i] = new SyllabusCourse(DEFUALT_CONTENT, null, SyllabusCourse.ITEM_TYPE.ITEM_TYPE_TEXT.ordinal(), 0);
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

    private int getRandomColor() {
        int index = mRandom.nextInt(BACKGROUND_COLOR.length);
        return BACKGROUND_COLOR[index];
    }
}
