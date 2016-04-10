package qzu.com.attendance.utils;

import android.support.v4.app.Fragment;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import qzu.com.attendance.R;

/**
 * Created by HUA_ZHONG_WEI on 2016/4/9.
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

}
