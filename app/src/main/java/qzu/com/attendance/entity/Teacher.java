package qzu.com.attendance.entity;

public class Teacher extends BaseEntity{
    
    private String userType;
    private String UID;
    private String sersionId;
    private String name;

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

    @Override
    public String toString() {
        return  super.toString() + "Teacher{" +
                "userType='" + userType + '\'' +
                ", UID='" + UID + '\'' +
                ", sersionId='" + sersionId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
