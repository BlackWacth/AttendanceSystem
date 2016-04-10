package qzu.com.attendance.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 *  考勤，签到 
 */
public class Attend extends BaseEntity{


    /**
     * scheduleId : 21
     * schedule : [{"stuId":"12237008","name":"彭 双","sex":"女","phone":"10100011033","class":"12计本一班","photo":"http://ccide.iego.cn/twins/pic/12237008.png","check":1}]
     * stuCount : 1
     */

    private String scheduleId;
    private int stuCount;
    /**
     * stuId : 12237008
     * name : 彭 双
     * sex : 女
     * phone : 10100011033
     * class : 12计本一班
     * photo : http://ccide.iego.cn/twins/pic/12237008.png
     * check : 1
     */

    private List<ScheduleBean> schedule;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getStuCount() {
        return stuCount;
    }

    public void setStuCount(int stuCount) {
        this.stuCount = stuCount;
    }

    public List<ScheduleBean> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleBean> schedule) {
        this.schedule = schedule;
    }

    public static class ScheduleBean {
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
            return "ScheduleBean{" +
                    "\nstuId='" + stuId + '\'' +
                    ",\n name='" + name + '\'' +
                    ",\n sex='" + sex + '\'' +
                    ",\n phone='" + phone + '\'' +
                    ",\n classX='" + classX + '\'' +
                    ",\n photo='" + photo + '\'' +
                    ",\n check=" + check +
                    "\n}";
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nAttend{" +
                "\nscheduleId='" + scheduleId + '\'' +
                ",\n stuCount=" + stuCount +
                ",\n schedule=" + schedule +
                "\n}";
    }
}
