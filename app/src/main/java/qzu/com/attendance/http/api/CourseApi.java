package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.Course;
import qzu.com.attendance.entity.Syllabus;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 上课情况接口
 */
public interface CourseApi {

    @GET("lookOverCourse.php")
    Observable<Course> getCourse(@Query("userType")String userType, @Query("UID")String uid, @Query("sersionId")String sersionId, @Query("AskType")String AskType);
}
