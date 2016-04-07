package qzu.com.attendance.entity;

public class Teacher {

    /**
     * userType : 1
     * UID : 1010001
     * sersionId : c9e8a761d38f9135de71b1f38355da7f
     * name : 尹成国
     * status : 1
     */

    private String userType;
    private String UID;
    private String sersionId;
    private String name;
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

    public String getSersionId() {
        return sersionId;
    }

    public void setSersionId(String sersionId) {
        this.sersionId = sersionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "userType='" + userType + '\'' +
                ", UID='" + UID + '\'' +
                ", sersionId='" + sersionId + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                '}';
    }
}
