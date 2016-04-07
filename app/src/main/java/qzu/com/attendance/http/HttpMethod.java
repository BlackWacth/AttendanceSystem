package qzu.com.attendance.http;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import qzu.com.attendance.http.api.LoginApi;
import qzu.com.attendance.utils.L;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HttpMethod {
    public static final String BASE_URL = "http://ccide.iego.cn/twins/phone/";
    public static final int DEFUALT_CONNECT_TIMEOUT = 30;
    public static final int DEFUALT_READ_TIMEOUT = 30;

    private Retrofit mRetrofit;

    private HttpMethod() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFUALT_CONNECT_TIMEOUT, TimeUnit.SECONDS)
//                .addInterceptor(new Interceptor() {
//                    @Override
//                    public Response intercept(Chain chain) throws IOException {
//                        Response response = chain.proceed(chain.request());
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
    }

    public Retrofit getmRetrofit() {
        return mRetrofit;
    }

    private static class HttpMethodHolder {
        public static final HttpMethod HTTP_METHOD = new HttpMethod();
    }

    public static HttpMethod getInstance() {
        return HttpMethodHolder.HTTP_METHOD;
    }

    public void Login(Subscriber subscriber, int type, int uuid, String password){
        LoginApi mLoginApi = mRetrofit.create(LoginApi.class);
        mLoginApi.login(type, uuid, password)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
