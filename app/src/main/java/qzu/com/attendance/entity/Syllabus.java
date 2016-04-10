package qzu.com.attendance.entity;

import java.util.List;

/**
 *  课程表
 */
public class Syllabus extends BaseEntity{


    /**
     * schedule : [{"week":"3","node":"1","courseName":"WEB网络编程","Address":"8栋110","startTime":"08:00:00","endTime":"09:30:00"},{"week":"4","node":"1","courseName":"WEB网络编程","Address":"五号机房(6栋607)","startTime":"08:00:00","endTime":"09:30:00"},{"week":"5","node":"2","courseName":"计算机系统结构","Address":"8栋108","startTime":"10:00:00","endTime":"12:30:00"},{"week":"6","node":"1","courseName":"WEB网络编程","Address":"8栋110","startTime":"08:00:00","endTime":"09:30:00"},{"week":"6","node":"2","courseName":"WEB网络编程","Address":"8栋110","startTime":"10:00:00","endTime":"12:30:00"},{"week":"6","node":"3","courseName":"WEB网络编程","Address":"8栋110","startTime":"14:30:00","endTime":"16:00:00"},{"week":"6","node":"4","courseName":"WEB网络编程","Address":"8栋110","startTime":"16:30:00","endTime":"18:00:00"},{"week":"6","node":"5","courseName":"WEB网络编程","Address":"8栋110","startTime":"20:00:00","endTime":"21:30:00"},{"week":"3","node":"2","courseName":"汇编语言","Address":"468465","startTime":"09:10:00","endTime":"11:00:00"}]
     * scheduleCount : 9
     */

    private int scheduleCount;
    /**
     * week : 3
     * node : 1
     * courseName : WEB网络编程
     * Address : 8栋110
     * startTime : 08:00:00
     * endTime : 09:30:00
     */

    private List<ScheduleBean> schedule;

    public int getScheduleCount() {
        return scheduleCount;
    }

    public void setScheduleCount(int scheduleCount) {
        this.scheduleCount = scheduleCount;
    }

    public List<ScheduleBean> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleBean> schedule) {
        this.schedule = schedule;
    }

    public static class ScheduleBean {
        private String week;
        private String node;
        private String courseName;
        private String Address;
        private String startTime;
        private String endTime;

        public String getWeek() {
            return week;
        }

        public void setWeek(String week) {
            this.week = week;
        }

        public String getNode() {
            return node;
        }

        public void setNode(String node) {
            this.node = node;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getAddress() {
            return Address;
        }

        public void setAddress(String Address) {
            this.Address = Address;
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

        @Override
        public String toString() {
            return "ScheduleBean{" +
                    "week='" + week + '\'' +
                    ", node='" + node + '\'' +
                    ", courseName='" + courseName + '\'' +
                    ", Address='" + Address + '\'' +
                    ", startTime='" + startTime + '\'' +
                    ", endTime='" + endTime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return super.toString() + "Syllabus{" +
                "scheduleCount=" + scheduleCount +
                ", schedule=" + schedule +
                '}';
    }
}
