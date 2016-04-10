package qzu.com.attendance.http.subscriber;

/**
 * 状态码和错误码统一处理
 */
public interface SubscriberOnNextListener<T> {

    void success(T t);
    
    void error(int code);
}
