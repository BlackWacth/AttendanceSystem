package qzu.com.attendance.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 提交考勤body
 */
public class AttendBody implements Serializable{

    private String userType;
    private String UID;
    private String sersionId;
    private List<CheckBean> checks;

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

    public String getSersionId() {
        return sersionId;
    }

    public void setSersionId(String sersionId) {
        this.sersionId = sersionId;
    }

    public List<CheckBean> getChecks() {
        return checks;
    }

    public void setChecks(List<CheckBean> checks) {
        this.checks = checks;
    }

    public static class CheckBean {
        private String stuId;
        private String scheduled;
        private int check;

        public CheckBean(String stuId, String scheduled, int check) {
            this.stuId = stuId;
            this.scheduled = scheduled;
            this.check = check;
        }

        public String getStuId() {
            return stuId;
        }

        public void setStuId(String stuId) {
            this.stuId = stuId;
        }

        public String getScheduled() {
            return scheduled;
        }

        public void setScheduled(String scheduled) {
            this.scheduled = scheduled;
        }

        public int getCheck() {
            return check;
        }

        public void setCheck(int check) {
            this.check = check;
        }

        @Override
        public String toString() {
            return "CheckBean{" +
                    "stuId='" + stuId + '\'' +
                    ", scheduled='" + scheduled + '\'' +
                    ", check=" + check +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AttendBody{" +
                "userType='" + userType + '\'' +
                ", UID='" + UID + '\'' +
                ", sersionId='" + sersionId + '\'' +
                ", checks=" + checks +
                '}';
    }
}
