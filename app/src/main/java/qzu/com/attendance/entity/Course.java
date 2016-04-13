package qzu.com.attendance.entity;

/**
 * 课程
 */
public class Course extends BaseEntity{

    /**
     * name : WEB网络编程
     * address : 8栋110
     * startTime : 20:00:00
     * endTime : 21:30:00
     * className : 12计本一班
     * TeacherName : 尹成国
     * phone : 11101011001
     * isExist : true
     */
    private String name;
    private String address;
    private String startTime;
    private String endTime;
    private String className;
    private String TeacherName;
    private String phone;
    private boolean isExist;

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

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean isExist) {
        this.isExist = isExist;
    }

    @Override
    public String toString() {
        return super.toString() + "Course{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", className='" + className + '\'' +
                ", TeacherName='" + TeacherName + '\'' +
                ", phone='" + phone + '\'' +
                ", isExist=" + isExist +
                '}';
    }
}
