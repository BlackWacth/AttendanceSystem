package qzu.com.attendance.entity;

/**
 * 课程表实体类
 */
public class SyllabusCourse {

    public static enum ITEM_TYPE {
        ITEM_TYPE_TEXT,
        ITEM_TYPE_CARD
    }

    private String content;
    private Course course;
    private int type;
    private int color = 0;

    public SyllabusCourse(String content, Course course, int type) {
        this.content = content;
        this.course = course;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "SyllabusCourse{" +
                "content='" + content + '\'' +
                ", course=" + course +
                ", type=" + type +
                ", color=" + color +
                '}';
    }
}
