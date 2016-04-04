package qzu.com.attendance.entity;

/**
 * 上课信息
 */
public class AttendClassInfo {

    /**
     * name : 汇编语言
     * address : 6栋101
     * startTime : 09:00:00
     * endTime : 12:30:00
     * className : 12计本一班
     * TeacherName : 张运波
     * phone : 11101011001
     * status : 1
     */

    private String name;
    private String address;
    private String startTime;
    private String endTime;
    private String className;
    private String TeacherName;
    private String phone;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTeacherName() {
        return TeacherName;
    }

    public void setTeacherName(String TeacherName) {
        this.TeacherName = TeacherName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AttendClassInfo{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", className='" + className + '\'' +
                ", TeacherName='" + TeacherName + '\'' +
                ", phone='" + phone + '\'' +
                ", status=" + status +
                '}';
    }
}
