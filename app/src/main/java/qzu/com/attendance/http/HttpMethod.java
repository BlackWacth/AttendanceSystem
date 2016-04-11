package qzu.com.attendance.http;

import android.content.Context;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import qzu.com.attendance.application.AApplication;
import qzu.com.attendance.entity.Attend;
import qzu.com.attendance.entity.AttendBody;
import qzu.com.attendance.entity.BaseEntity;
import qzu.com.attendance.entity.Teacher;
import qzu.com.attendance.http.api.AskApi;
import qzu.com.attendance.http.api.AttendApi;
import qzu.com.attendance.http.api.CourseApi;
import qzu.com.attendance.http.api.LoginApi;
import qzu.com.attendance.http.api.PersionalApi;
import qzu.com.attendance.http.api.SubmitAttendApi;
import qzu.com.attendance.http.api.SyllabusApi;
import qzu.com.attendance.http.subscriber.ProgressSubscriber;
import qzu.com.attendance.http.subscriber.SubscriberOnNextListener;
import qzu.com.attendance.utils.Constants;
import qzu.com.attendance.utils.L;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethod {
    public static final String BASE_URL = "http://ccide.iego.cn/twins/phone/";
    public static final int DEFUALT_CONNECT_TIMEOUT = 30;
    public static final int DEFUALT_READ_TIMEOUT = 30;

    private Retrofit mRetrofit;
    
    private LoginApi mLoginApi;
    private SyllabusApi mSyllabusApi;
    private CourseApi mCourseApi;
    private AttendApi mAttendApi;
    private PersionalApi mPersionalApi;
    private AskApi mAskApi;
    private SubmitAttendApi mSubmitAttendApi;
    
    private HttpMethod() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFUALT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Response response = chain.proceed(chain.request());
//                        L.i("url : " + response.request().url().toString());
//                        L.i("response === " + response.body().string());
//                        return response;
//                    }
//                })
                .build();
        
        
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        
        mLoginApi = mRetrofit.create(LoginApi.class);
        mSyllabusApi = mRetrofit.create(SyllabusApi.class);
        mCourseApi = mRetrofit.create(CourseApi.class);
        mAttendApi = mRetrofit.create(AttendApi.class);
        mPersionalApi = mRetrofit.create(PersionalApi.class);
        mAskApi = mRetrofit.create(AskApi.class);
        mSubmitAttendApi = mRetrofit.create(SubmitAttendApi.class);
    }

    private static class HttpMethodHolder {
        public static final HttpMethod HTTP_METHOD = new HttpMethod();
    }

    public static HttpMethod getInstance() {
        return HttpMethodHolder.HTTP_METHOD;
    }

    private Observable<? extends BaseEntity> processObservalbe(Observable<? extends  BaseEntity> observable){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 登录接口
     * @param subscriber
     * @param type
     * @param uuid
     * @param password
     */
    public void login(Subscriber<BaseEntity> subscriber, int type, int uuid, String password){
        Observable<? extends  BaseEntity> observable = null;
        if(type == AApplication.TYPE_TEACHER){
            observable = mLoginApi.loginTeacher(type, uuid, password);    
        }else if(type == AApplication.TYPE_STUDENT) {
            observable = mLoginApi.loginStudent(type, uuid, password);
        }
        processObservalbe(observable).subscribe(subscriber);
    }

    /**
     * 获取课程表接口
     * @param context
     * @param listener
     * @param userType
     * @param uid
     * @param sersionId
     * @param askType
     */
    public void getSyllabus(Context context, SubscriberOnNextListener listener, String userType, String uid, String sersionId, String askType) {
        processObservalbe(mSyllabusApi.getSyllabus(userType, uid, sersionId, askType)).subscribe(new ProgressSubscriber(context, listener));        
    }

    /**
     * 获取当前课程
     * @param context
     * @param listener
     * @param userType
     * @param uid
     * @param sersionId
     * @param askType
     */
    public void getCourse(Context context, SubscriberOnNextListener listener, String userType, String uid, String sersionId, String askType) {
        processObservalbe(mCourseApi.getCourse(userType, uid, sersionId, askType)).subscribe(new ProgressSubscriber(context, listener));
    }

    /**
     * 考勤、签到信息
     * @param context
     * @param listener
     * @param userType
     * @param uid
     * @param sersionId
     * @param askType
     */
    public void attend(Context context, SubscriberOnNextListener listener, String userType, String uid, String sersionId, String askType) {
        processObservalbe(mAttendApi.attend(userType, uid, sersionId, askType)).subscribe(new ProgressSubscriber(context, listener));
    }

    /**
     * 修改个人信息
     * @param context
     * @param listener
     * @param userType
     * @param uid
     * @param sersionId
     * @param phone
     * @param newPassword
     */
    public void infoChange(Context context, SubscriberOnNextListener listener, String userType, String uid, String sersionId, String phone, String newPassword) {
        processObservalbe(mPersionalApi.infoChange(userType, uid, sersionId, phone, newPassword)).subscribe(new ProgressSubscriber(context, listener));
    }

    /**
     * 随机抽问
     * @param context
     * @param listener
     * @param userType
     * @param uid
     * @param sersionId
     * @param askType
     */
    public void ask(Context context, SubscriberOnNextListener listener, String userType, String uid, String sersionId, String askType) {
        processObservalbe(mAskApi.ask(userType, uid, sersionId, askType)).subscribe(new ProgressSubscriber(context, listener));
    }

    /**
     * 考勤数据提交
     * @param context
     * @param listener
     * @param body
     */
    public void sublime(Context context, SubscriberOnNextListener listener, AttendBody body) {
        processObservalbe(mSubmitAttendApi.submit(body)).subscribe(new ProgressSubscriber(context, listener));
    }
}
