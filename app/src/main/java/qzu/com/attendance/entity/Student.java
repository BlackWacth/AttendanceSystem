package qzu.com.attendance.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZHONG WEI  HUA on 2016/4/6.
 */
public class Student {

    /**
     * userType : 1
     * UID : 110
     * sersionId : 1604040939
     * name : twins
     * sex : 男
     * phone : 11101011001
     * class : 12计本一班
     * college : 电子信息通信学院
     * errno : 1
     * status : 1
     */

    private String userType;
    private String UID;
    private int sersionId;
    private String name;
    private String sex;
    private String phone;
    @SerializedName("class")
    private String classX;
    private String college;
    private int errno;
    private int status;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public int getSersionId() {
        return sersionId;
    }

    public void setSersionId(int sersionId) {
        this.sersionId = sersionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getClassX() {
        return classX;
    }

    public void setClassX(String classX) {
        this.classX = classX;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public int getErrno() {
        return errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Student{" +
                "userType='" + userType + '\'' +
                ", UID='" + UID + '\'' +
                ", sersionId=" + sersionId +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", classX='" + classX + '\'' +
                ", college='" + college + '\'' +
                ", errno=" + errno +
                ", status=" + status +
                '}';
    }
}
