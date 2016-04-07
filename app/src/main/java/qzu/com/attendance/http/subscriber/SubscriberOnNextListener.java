package qzu.com.attendance.http.subscriber;

/**
 * Created by ZHONG WEI  HUA on 2016/3/23.
 */
public interface SubscriberOnNextListener<T> {

    void  onNext(T t);

}
