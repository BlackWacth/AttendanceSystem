package qzu.com.attendance.utils;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import qzu.com.attendance.application.AApplication;
import rx.functions.Action1;

/**
 * 控件点击
 */
public class ViewClick {

    /**
     * 防抖动，防止按键连续多次点击
     * @param view 被点击View
     * @param time 下次点击有效时间（秒）
     * @param action 点击事件
     */
    public static void preventShake(View view, int time, Action1<Void> action) {
        RxView.clicks(view)
                .throttleFirst(time, TimeUnit.SECONDS)
                .subscribe(action);
    }

    /**
     * 按键防抖的，默认时间3秒
     * @param view
     * @param action
     */
    public static void preventShake(View view, Action1<Void> action) {
        preventShake(view, AApplication.SKIP_TIME, action);
    }

}
