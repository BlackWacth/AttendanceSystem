package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.AttendBody;
import qzu.com.attendance.entity.BaseEntity;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 考勤提交接口
 */
public interface SubmitAttendApi {

    @POST("SaveCheckClass.php")
    Observable<BaseEntity> submit(@Body AttendBody body);

}
