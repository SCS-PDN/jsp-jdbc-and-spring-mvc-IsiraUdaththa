package university.model;

import java.sql.Date;

public class Registration {
    private int regId;
    private int studentId;
    private int courseId;
    private Date date;

    public Registration() {
    }

    public Registration(int regId, int studentId, int courseId, Date date) {
        this.regId = regId;
        this.studentId = studentId;
        this.courseId = courseId;
        this.date = date;
    }

    public int getRegId() {
        return regId;
    }

    public void setRegId(int regId) {
        this.regId = regId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Registration{" + "regId=" + regId + ", studentId=" + studentId + ", courseId=" + courseId + ", date=" + date + '}';
    }
}