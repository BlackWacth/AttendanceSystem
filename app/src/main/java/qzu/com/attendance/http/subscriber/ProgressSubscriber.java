package qzu.com.attendance.http.subscriber;

import android.content.Context;
import android.widget.Toast;

import qzu.com.attendance.http.progress.ProGressDialogHandler;
import qzu.com.attendance.http.progress.ProgressCancelListener;
import rx.Subscriber;

/**
 * Created by ZHONG WEI  HUA on 2016/3/23.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private Context mContext;

    private SubscriberOnNextListener mSubscriberOnNextListener;
    private ProGressDialogHandler mProGressDialogHandler;

    public ProgressSubscriber(Context mContext, SubscriberOnNextListener mSubscriberOnNextListener) {
        this.mContext = mContext;
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;

        mProGressDialogHandler = new ProGressDialogHandler(mContext, true, this);
    }

    private void showProgressDialog() {
        if(mProGressDialogHandler != null) {
            mProGressDialogHandler.obtainMessage(ProGressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dissProgressDialog(){
        if(mProGressDialogHandler != null) {
            mProGressDialogHandler.obtainMessage(ProGressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProGressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        showToast("加载完成");
        dissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        showToast("网络异常");
        dissProgressDialog();
    }

    @Override
    public void onNext(T t) {
        if(mSubscriberOnNextListener != null) {
            mSubscriberOnNextListener.onNext(t);
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
