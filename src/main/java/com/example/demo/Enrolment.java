package com.example.demo;

import javax.persistence.*;

@Entity(name = "Enrolment")
@Table(name = "enrolment")
public class Enrolment { //through table of student <-> enrolment <-> course

    @EmbeddedId
    private EnrolmentId enrolmentId;

    //Composite Key!!!
    @ManyToOne
    @MapsId("studentId") // reference to EnrolmentId (through table)'s studentId
    @JoinColumn(name = "student_id") //actual attribute in db
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "course_id")
    private Course course;

    public Enrolment() {
    }

    public Enrolment(EnrolmentId enrolmentId, Student student, Course course) {
        this.enrolmentId = enrolmentId;
        this.student = student;
        this.course = course;
    }

    public Enrolment(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    public EnrolmentId getEnrolmentId() {
        return enrolmentId;
    }

    public void setEnrolmentId(EnrolmentId enrolmentId) {
        this.enrolmentId = enrolmentId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
