package qzu.com.attendance.http.api;

import okhttp3.ResponseBody;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.entity.Teacher;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 登录接口
 */
public interface LoginApi{
    
    @GET("login.php")
    Observable<Teacher> loginTeacher(@Query("loginType") int type, @Query("UID")int uid, @Query("password")String password);
    
    @GET("login.php")
    Observable<Student> loginStudent(@Query("loginType") int type, @Query("UID")int uid, @Query("password")String password);
}
