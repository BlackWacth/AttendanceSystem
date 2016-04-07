package qzu.com.attendance.entity;

import java.util.List;

/**
 * 课程实体类
 */
public class Course {

    /**
     * schedule : [{"week":1,"node":2,"coursename":"数据结构","address":"8栋301","starttime":"10:30:00","endtime":"12:00:00"},{"week":2,"node":2,"coursename":"web网络编程","address":"3栋301","starttime":"10:30:00","endtime":"12:00:00"}]
     * scheduleCount : 2
     * errno : 1
     * status : 1
     */

    private int scheduleCount;
    private int errno;
    private int status;
    /**
     * week : 1
     * node : 2
     * coursename : 数据结构
     * address : 8栋301
     * starttime : 10:30:00
     * endtime : 12:00:00
     */

    private List<ScheduleBean> schedule;

    public int getScheduleCount() {
        return scheduleCount;
    }

    public void setScheduleCount(int scheduleCount) {
        this.scheduleCount = scheduleCount;
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

    public List<ScheduleBean> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleBean> schedule) {
        this.schedule = schedule;
    }

    public static class ScheduleBean {
        private int week;
        private int node;
        private String coursename;
        private String address;
        private String starttime;
        private String endtime;

        public int getWeek() {
            return week;
        }

        public void setWeek(int week) {
            this.week = week;
        }

        public int getNode() {
            return node;
        }

        public void setNode(int node) {
            this.node = node;
        }

        public String getCoursename() {
            return coursename;
        }

        public void setCoursename(String coursename) {
            this.coursename = coursename;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStarttime() {
            return starttime;
        }

        public void setStarttime(String starttime) {
            this.starttime = starttime;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        @Override
        public String toString() {
            return "ScheduleBean{" +
                    "week=" + week +
                    ", node=" + node +
                    ", coursename='" + coursename + '\'' +
                    ", address='" + address + '\'' +
                    ", starttime='" + starttime + '\'' +
                    ", endtime='" + endtime + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Course{" +
                "scheduleCount=" + scheduleCount +
                ", errno=" + errno +
                ", status=" + status +
                ", schedule=" + schedule +
                '}';
    }
}
