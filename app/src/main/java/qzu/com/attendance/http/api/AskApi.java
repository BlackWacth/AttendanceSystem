package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.Student;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by HUA_ZHONG_WEI on 2016/4/10.
 */
public interface AskApi {
    
    @GET("spotCheckStudent.php")
    Observable<Student> ask(@Query("userType")String userType, @Query("UID")String uid, @Query("sersionId")String sersionId, @Query("AskType")String AskType);
}
