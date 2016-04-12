package qzu.com.attendance.http.subscriber;

import android.content.Context;

import in.srain.cube.views.ptr.PtrFrameLayout;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.utils.Constants;
import rx.Subscriber;

/**
 *
 */
public class RefreshSubscriber extends Subscriber<BaseEntity> {

    private Context mContext;
    private SubscriberOnNextListener<BaseEntity> mSubscriberOnNextListener;
    private PtrFrameLayout frame;

    public RefreshSubscriber(Context mContext, SubscriberOnNextListener<BaseEntity> mSubscriberOnNextListener, PtrFrameLayout frame) {
        this.mContext = mContext;
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.frame = frame;
    }

    @Override
    public void onCompleted() {
        frame.refreshComplete();
    }

    @Override
    public void onError(Throwable e) {
        frame.refreshComplete();
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
}
