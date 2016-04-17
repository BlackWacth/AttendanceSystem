package qzu.com.attendance.ui.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Until;

import java.util.concurrent.ExecutionException;

import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.ui.activity.MainActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.utils.Utils;
import qzu.com.attendance.utils.ViewClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 个人信息
 */
@SuppressLint("ValidFragment")
public class PersionalInfoFragment extends BaseFragment{
    
    private Student mStudent;

    private ImageView mStudentIcon;
    private TextView mStudentName;
    private TextView mStudentSex;
    private TextView mStudentId;
    private TextView mStudentClass;
    private TextInputEditText mStudentPhone;
    private TextInputEditText mNewPassword;
    private Button mChange;
    
    private boolean isChangePwd = false;

    public PersionalInfoFragment(Student mStudent) {
        this.mStudent = mStudent;
    }

    public PersionalInfoFragment() {
        
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_persional_info;
    }

    @Override
    protected void initView(View view) {
        mStudentIcon = (ImageView) view.findViewById(R.id.persional_icon);
        mStudentName = (TextView) view.findViewById(R.id.persional_name);
        mStudentSex = (TextView) view.findViewById(R.id.persional_sex);
        mStudentId = (TextView) view.findViewById(R.id.persional_id);
        mStudentClass = (TextView) view.findViewById(R.id.persional_class);
        mStudentPhone = (TextInputEditText) view.findViewById(R.id.persional_new_phone);
        mNewPassword = (TextInputEditText) view.findViewById(R.id.persional_new_password);
        mChange = (Button) view.findViewById(R.id.persional_info_change);
    }

    @Override
    protected void initData() {
        ViewClick.preventShake(mChange, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                changeInfo();
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if(mStudent != null) {
            mStudentName.setText(mStudent.getName());
            mStudentSex.setText(mStudent.getSex());
            mStudentId.setText(mStudent.getUID());
            mStudentClass.setText(mStudent.getClassX());
            mStudentPhone.setText(mStudent.getPhone());
            Utils.loadImageRx(this, mStudent.getPhoto(), mStudentIcon);
            isHasLoadedOnce = true;
        }
    }

    @Override
    public boolean checkCanDoRefresh() {
        return false;
    }

    /**
     * 提交修改信息，如果修改了密码，就退出，用户重新登录
     */
    private void changeInfo(){
        final String newPhone = mStudentPhone.getText().toString();
        String newPwd = mNewPassword.getText().toString();
        if((TextUtils.isEmpty(newPhone) || newPhone.equals(mStudent.getPhone())) && TextUtils.isEmpty(newPwd)) {
            showToast("信息未修改");
            mStudentPhone.setText(mStudent.getPhone());
            return ;
        }

        if(!TextUtils.isEmpty(newPwd)) {
            isChangePwd = true;
        }

        AApplication.mHttpMethod.infoChange(getActivity(), new SubscriberOnNextListener<BaseEntity>() {
            @Override
            public void success(BaseEntity entity) {
                showToast("修改成功");
                mStudent.setPhone(newPhone);
                if(!isChangePwd) {
                    return;
                }
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        startActivity(new Intent(getActivity(), MainActivity.class));
                        getActivity().finish();
                    }
                }.start();
            }

            @Override
            public void error(int code) {
                showToast("修改失败");
            }
        }, mStudent.getUserType(), mStudent.getUID(), mStudent.getSersionId(), newPhone, newPwd);
    }

}
