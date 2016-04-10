package qzu.com.attendance.ui.fragment;


import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import qzu.com.attendance.R;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.ui.activity.MainActivity;
import qzu.com.attendance.ui.base.BaseFragment;
import qzu.com.attendance.utils.Utils;

/**
 * 提问或个人信息
 */
public class PersionalInfoFragment extends BaseFragment implements View.OnClickListener{
    
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
        mChange.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        
    }

    @Override
    protected void lazyLoad() {
        if(mStudent != null) {
            mStudentName.setText(mStudent.getName());
            mStudentSex.setText(mStudent.getSex());
            mStudentId.setText(mStudent.getUID());
            mStudentClass.setText(mStudent.getClassX());
            mStudentPhone.setText(mStudent.getPhone());
            Utils.loadImage(this, mStudent.getPhoto(), mStudentIcon);
            isHasLoadedOnce = true;
        }
    }

    private void changeInfo(){
        String newPhone = mStudentPhone.getText().toString();
        if(TextUtils.isEmpty(newPhone)) {
            newPhone = mStudent.getPhone();
        }
        String newPwd = mNewPassword.getText().toString();
        if(TextUtils.isEmpty(newPwd)) {
            newPwd = "";
        }else {
            isChangePwd = true;
        }
        AApplication.mHttpMethod.infoChange(getActivity(), new SubscriberOnNextListener<BaseEntity>() {
            @Override
            public void success(BaseEntity entity) {
                showToast("修改成功");
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
    
    @Override
    public void onClick(View v) {
        changeInfo();
    }
}
