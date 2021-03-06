package qzu.com.attendance.ui.fragment;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.ui.view.MDialog;
import qzu.com.attendance.utils.L;
import qzu.com.attendance.utils.Utils;
import qzu.com.attendance.utils.ViewClick;
import rx.functions.Action1;

/**
 * 随机抽问
 */
@SuppressLint("ValidFragment")
public class AskQuestionFragment extends BaseFragment{

    private String userType;
    private String uid;
    private String sersionId;
    private String AskType = "4";
    
    private Button askBtn;
    private ImageView mStudentIcon;
    private TextView mStudentName;
    private TextView mStudentSex;
    private TextView mStudentId;
    private TextView mStudentClass;
    private TextView mStudentPhone;
    private TextView noCourse;
    private RelativeLayout contentLayout;
            ;

    public AskQuestionFragment(String userType, String uid, String sersionId) {
        this.userType = userType;
        this.uid = uid;
        this.sersionId = sersionId;
    }

    public AskQuestionFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ask_question;
    }

    @Override
    protected void initView(View view) {
        askBtn = (Button) view.findViewById(R.id.ask_question_btn);
        mStudentIcon = (ImageView) view.findViewById(R.id.ask_icon);
        mStudentName = (TextView) view.findViewById(R.id.ask_student_name);
        mStudentSex = (TextView) view.findViewById(R.id.ask_student_sex);
        mStudentId = (TextView) view.findViewById(R.id.ask_student_id);
        mStudentClass = (TextView) view.findViewById(R.id.ask_student_class);
        mStudentPhone = (TextView) view.findViewById(R.id.ask_student_phone);
        noCourse = (TextView) view.findViewById(R.id.ask_no_course);
        contentLayout = (RelativeLayout) view.findViewById(R.id.ask_content_layout);
    }

    @Override
    protected void initData() {
        contentLayout.setVisibility(View.GONE);
        noCourse.setVisibility(View.GONE);

        ViewClick.preventShake(askBtn, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                getStudent();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        
    }

    @Override
    public boolean checkCanDoRefresh() {
        return false;
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
     * 网络获取学生信息
     */
    private void getStudent(){
        AApplication.mHttpMethod.ask(getActivity(), new SubscriberOnNextListener<Student>() {
            @Override
            public void success(Student student) {
                L.i(student.toString());
                if(TextUtils.isEmpty(student.getUID())) {
                    contentLayout.setVisibility(View.GONE);
                    noCourse.setVisibility(View.VISIBLE);
                    return ;
                }else {
                    contentLayout.setVisibility(View.VISIBLE);
                    noCourse.setVisibility(View.GONE);
//                    Utils.loadImage(AskQuestionFragment.this, student.getPhoto(), mStudentIcon);
                    Utils.loadImageRx(AskQuestionFragment.this, student.getPhoto(), mStudentIcon);
                    mStudentName.setText(student.getName());
                    mStudentSex.setText(student.getSex());
                    mStudentId.setText(student.getUID());
                    mStudentClass.setText(student.getClassX());
                    mStudentPhone.setText(student.getPhone());
                }
            }

            @Override
            public void error(int code) {
                errorTip(code);
            }
        },userType, uid, sersionId, AskType);
    }
}
