package qzu.com.attendance.http.subscriber;

import android.content.Context;
import android.widget.Toast;

import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.http.progress.ProgressDialogHandler;
import qzu.com.attendance.http.progress.ProgressCancelListener;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;
import rx.Subscriber;

/**
 * 带进度条的Subscriber，统一处理了onCompleted和onError方法
 */
public class ProgressSubscriber extends Subscriber<BaseEntity> implements ProgressCancelListener {

    private Context mContext;

    private SubscriberOnNextListener<BaseEntity> mSubscriberOnNextListener;
    private ProgressDialogHandler mProGressDialogHandler;

    public ProgressSubscriber(Context mContext, SubscriberOnNextListener mSubscriberOnNextListener) {
        this.mContext = mContext;
        this.mSubscriberOnNextListener =  mSubscriberOnNextListener;
        mProGressDialogHandler = new ProgressDialogHandler(mContext, true, this);
    }

    private void showProgressDialog() {
        if(mProGressDialogHandler != null) {
            mProGressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dissProgressDialog(){
        if(mProGressDialogHandler != null) {
            mProGressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProGressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
//        showToast("加载完成");
        dissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        showToast("网络异常");
        L.i("网络异常 : " + e.getMessage() );
        dissProgressDialog();
    }

    @Override
    public void onNext(BaseEntity entity) {
        if(mSubscriberOnNextListener != null) {
            if(entity.getStatus() == Constants.NETWORK_STATUS_SUCCESS){
                mSubscriberOnNextListener.success(entity);
            }else if(entity.getStatus() == Constants.NETWORK_STATUS_ERROR) {
                mSubscriberOnNextListener.error(entity.getErrno());
            }
        }
    }

    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancelProgress() {
        if(!isUnsubscribed()){
            unsubscribe();
        }
    }
}
