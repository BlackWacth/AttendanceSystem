package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.Attend;
import qzu.com.attendance.entity.Course;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 考勤接口
 */
public interface AttendApi {

    @GET("lookOverStudent.php")
    Observable<Attend> attend(@Query("userType")String userType, @Query("UID")String uid, @Query("sersionId")String sersionId, @Query("AskType")String AskType);
    
}
