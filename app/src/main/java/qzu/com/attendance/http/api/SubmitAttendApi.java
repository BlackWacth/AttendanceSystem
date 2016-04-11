package qzu.com.attendance.http.api;

import qzu.com.attendance.entity.AttendBody;
import qzu.com.attendance.entity.BaseEntity;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by ZHONG WEI  HUA on 2016/4/11.
 */
public interface SubmitAttendApi {

    @POST("SaveCheckClass.php")
    Observable<BaseEntity> submit(@Body AttendBody body);

}
