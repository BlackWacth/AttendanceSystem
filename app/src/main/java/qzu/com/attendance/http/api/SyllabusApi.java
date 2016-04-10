package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.Syllabus;
import qzu.com.attendance.entity.Teacher;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 课程表查询
 */
public interface SyllabusApi {
    
    @GET("lookOverSchedule.php")
    Observable<Syllabus> getSyllabus(@Query("userType")String userType, @Query("UID")String uid, @Query("sersionId")String sersionId, @Query("AskType")String AskType);
}
