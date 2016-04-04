package qzu.com.attendance.application;

import android.app.Application;

public class AApplication extends Application {

    public static final int TYPE_TEACHER = 1;   //教师
    public static final int TYPE_STUDENT = 2;   //学生
    
    /**用户类型 */
    public static int USER_TYPE = TYPE_STUDENT;

    @Override
    public void onCreate() {
        super.onCreate();
        
    }
}
