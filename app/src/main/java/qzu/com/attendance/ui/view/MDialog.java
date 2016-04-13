package qzu.com.attendance.ui.view;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;


/**
 * 对话框
 */
public class MDialog {

    public static void showDialog(Context context, String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(text)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.show();
    }

}
