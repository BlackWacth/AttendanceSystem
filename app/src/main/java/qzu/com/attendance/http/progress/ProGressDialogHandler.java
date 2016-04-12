package qzu.com.attendance.http.progress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.TextView;

import qzu.com.attendance.R;

/**
 * 进度条
 */
public class ProgressDialogHandler extends Handler{

    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private Dialog mProgressDialog;

    private Context mContext;
    private boolean mCancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context mContext, boolean mCancelable, ProgressCancelListener mProgressCancelListener) {
        this.mContext = mContext;
        this.mCancelable = mCancelable;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    public ProgressDialogHandler(Context mContext, boolean mCancelable) {
        this.mContext = mContext;
        this.mCancelable = mCancelable;
    }

    private void initProgressDialog() {
        if(mProgressDialog == null) {
            mProgressDialog = new Dialog(mContext);
            mProgressDialog.setCancelable(mCancelable);
            mProgressDialog.setContentView(R.layout.progress_dialog);
            WindowManager.LayoutParams params = mProgressDialog.getWindow().getAttributes();
            params.width = 500;
//            params.height = 500;
            mProgressDialog.getWindow().setAttributes(params);
            mProgressDialog.getWindow().setBackgroundDrawableResource(R.drawable.progress_dialog_shape);
            TextView msg = (TextView) mProgressDialog.findViewById(R.id.progress_msg);
            msg.setText("卖力加载中……");
            if(mCancelable) {
                mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        if(mProgressCancelListener == null) {
                            return ;
                        }
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }

            if(!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        }
    }

    private void dismissProgressDialog(){
        if(mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;

            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
