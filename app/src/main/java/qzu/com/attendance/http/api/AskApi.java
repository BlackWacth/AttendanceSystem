package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.Student;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 提问接口
 */
public interface AskApi {
    
    @GET("spotCheckStudent.php")
    Observable<Student> ask(@Query("userType")String userType, @Query("UID")String uid, @Query("sersionId")String sersionId, @Query("AskType")String AskType);
}
