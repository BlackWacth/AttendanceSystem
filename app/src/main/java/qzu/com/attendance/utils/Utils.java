package qzu.com.attendance.utils;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.concurrent.ExecutionException;

import qzu.com.attendance.R;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
public class Utils {
    
    public static int string2Int(String str){
        return Integer.parseInt(str);
    }
    
    public static String getTime(String startTime, String endTime){
        startTime = startTime.substring(0, startTime.lastIndexOf(":"));
        endTime = endTime.substring(0, endTime.lastIndexOf(":"));
        return startTime + " - " + endTime;
    }
    
    public static void loadImage(Fragment fragment, String url, ImageView imageView) {
        Glide.with(fragment).load(url)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error)
                .fitCenter()
                .into(imageView);
    }

    public static void loadImageRx(final Fragment fragment, final String url, final ImageView imageView) {
        Observable<Bitmap> observable = Observable.create(new Observable.OnSubscribe<Bitmap>() {
            @Override
            public void call(Subscriber<? super Bitmap> subscriber) {
                try {
                    Bitmap bitmap = Glide.with(fragment)
                            .load(url)
                            .asBitmap()
                            .into(100, 100)
                            .get();
                    subscriber.onNext(bitmap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        });
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        imageView.setImageBitmap(bitmap);
                    }
                });

    }
}
