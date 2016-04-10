package qzu.com.attendance.entity;

import com.google.gson.annotations.SerializedName;

/**
 * 学生实体
 */
public class  Student extends BaseEntity{


    /**
     * userType : 2
     * UID : 12237001
     * sersionId : 8c60843085285424f3bf16bd890da733
     * name : 孙一鸣
     * sex : 男
     * phone : 10100011011
     * class : 12计本一班
     * college : 电子通信工程学院 
     */

    private String userType;
    private String UID;
    private String sersionId;
    private String name;
    private String sex;
    private String phone;
    @SerializedName("class")
    private String classX;
    private String college;
    private String photo;

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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return super.toString() + "\nStudent{" +
                "\nuserType='" + userType + '\'' +
                ",\n UID='" + UID + '\'' +
                ",\n sersionId='" + sersionId + '\'' +
                ",\n name='" + name + '\'' +
                ",\n sex='" + sex + '\'' +
                ",\n phone='" + phone + '\'' +
                ",\n classX='" + classX + '\'' +
                ",\n college='" + college + '\'' +
                ",\n photo = '" + photo + '\'' +
                "}";
    }
}
