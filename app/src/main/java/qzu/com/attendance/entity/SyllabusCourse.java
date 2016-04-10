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
    private Syllabus.ScheduleBean schedule;
    private int type;
    private int color = 0;

    public SyllabusCourse(String content, Syllabus.ScheduleBean schedule, int type, int color) {
        this.content = content;
        this.schedule = schedule;
        this.type = type;
        this.color = color;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Syllabus.ScheduleBean getSchedule() {
        return schedule;
    }

    public void setSchedule(Syllabus.ScheduleBean schedule) {
        this.schedule = schedule;
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
                ", schedule=" + schedule +
                ", type=" + type +
                ", color=" + color +
                '}';
    }
}
