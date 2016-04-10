package qzu.com.attendance.application;

import android.app.Application;

import qzu.com.attendance.http.HttpMethod;

public class AApplication extends Application {

    public static final int TYPE_TEACHER = 1;   //教师
    public static final int TYPE_STUDENT = 2;   //学生
    public static HttpMethod mHttpMethod;
    /**用户类型 */
    public static int USER_TYPE = TYPE_STUDENT;

    @Override
    public void onCreate() {
        super.onCreate();
        mHttpMethod = HttpMethod.getInstance();
    }
}
