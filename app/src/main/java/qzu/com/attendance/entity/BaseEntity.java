package qzu.com.attendance.entity;

import java.io.Serializable;

/**
 * 实体基类，用于统一处理状态码和错误码
 */
public class BaseEntity implements Serializable{
    
    protected int status;
    protected int errno;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "status=" + status +
                ", errno=" + errno +
                '}';
    }
}
