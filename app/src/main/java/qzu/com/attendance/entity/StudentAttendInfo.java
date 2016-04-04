package qzu.com.attendance.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by HUA_ZHONG_WEI on 2016/4/4.
 */
public class StudentAttendInfo {

    /**
     * scheduleId : 1
     * student : [{"stuId":"12237008","name":"twins","sex":"女","phone":"10100011011","class":"12计本一班","photo":"abc","check":0},{"stuId":"12237006","name":"张春鹏","sex":"女","phone":"10100011012","class":"12计本一班","photo":"abc","check":1}]
     * stuCount : 2
     * errno : 0
     * status : 1
     */

    private int scheduleId;
    private int stuCount;
    private int errno;
    private int status;
    /**
     * stuId : 12237008
     * name : twins
     * sex : 女
     * phone : 10100011011
     * class : 12计本一班
     * photo : abc
     * check : 0
     */

    private List<StudentBean> student;

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getStuCount() {
        return stuCount;
    }

    public void setStuCount(int stuCount) {
        this.stuCount = stuCount;
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

    public List<StudentBean> getStudent() {
        return student;
    }

    public void setStudent(List<StudentBean> student) {
        this.student = student;
    }

    public static class StudentBean {
        private String stuId;
        private String name;
        private String sex;
        private String phone;
        @SerializedName("class")
        private String classX;
        private String photo;
        private int check;

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
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

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        @Override
        public String toString() {
            return "StudentBean{" +
                    "stuId='" + stuId + '\'' +
                    ", name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", phone='" + phone + '\'' +
                    ", classX='" + classX + '\'' +
                    ", photo='" + photo + '\'' +
                    ", check=" + check +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "StudentAttendInfo{" +
                "scheduleId=" + scheduleId +
                ", stuCount=" + stuCount +
                ", errno=" + errno +
                ", status=" + status +
                ", student=" + student +
                '}';
    }
}
