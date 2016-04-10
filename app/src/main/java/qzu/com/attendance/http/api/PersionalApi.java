package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.entity.Course;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 *  个人信息接口
 */
public interface PersionalApi {

    @GET("alterStudent.php")
    Observable<BaseEntity> infoChange(@Query("userType")String userType, @Query("UID")String uid, @Query("sersionId")String sersionId, @Query("phone")String phone, @Query("newPassword")String newPassword);
}
