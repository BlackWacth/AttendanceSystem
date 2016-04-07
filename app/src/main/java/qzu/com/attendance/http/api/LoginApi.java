package qzu.com.attendance.http.api;

import okhttp3.ResponseBody;
import qzu.com.attendance.entity.Student;
import qzu.com.attendance.entity.Teacher;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface LoginApi {

    @GET("login.php")
    Observable<Teacher> login(@Query("loginType") int type, @Query("UID")int uid, @Query("password")String password);

    @GET("login.php")
    Observable<ResponseBody> loginCall(@Query("loginType") int type, @Query("UID")int uid, @Query("password")String password);

    @GET("login.php")
    Call<ResponseBody> loginBody(@Query("loginType") int type, @Query("UID")int uid, @Query("password")String password);
}
