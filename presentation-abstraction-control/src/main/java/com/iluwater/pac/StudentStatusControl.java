package com.iluwater.pac;

public class StudentStatusControl {
    private StudentStatusAbstraction studentStatusAbstraction;
    private StudentStatusPresentation studentStatusPresentation;
    private StudentCourseControl studentCourseControl;
    private StudentAverageGradeControl studentAverageGradeControl;

    /**
     * a getter.
     *
     * @return the StudentCourseControl object that this class object has.
     */
    public StudentCourseControl getStudentCourseControl() {
        return studentCourseControl;
    }

    /**
     * a getter.
     *
     * @return the StudentAverageGradeControl object this class object holds.
     */

    public StudentAverageGradeControl getStudentAverageGradeControl() {
        return studentAverageGradeControl;
    }

    /**
     * constructor.
     */
    public StudentStatusControl() {
        this.studentStatusAbstraction = new StudentStatusAbstraction();
        this.studentStatusPresentation = new StudentStatusPresentation();
        this.studentCourseControl = new StudentCourseControl(this);
        this.studentAverageGradeControl = new StudentAverageGradeControl(this);
    }

    /**
     * update the average grade.
     *
     * @param studentCourseAbstraction is an StudentCourseAbstract object that
     *                                 holds all the course grades.
     */
    public void update(StudentCourseAbstraction studentCourseAbstraction) {
        studentAverageGradeControl.updatePreAndAbstraction(studentCourseAbstraction);
    }

    /**
     * update the status of the student according to the average grade.
     *
     * @param averageGrade is the average grade.
     */

    public void updateStatus(float averageGrade) {
        studentStatusAbstraction.setPass(averageGrade);
        studentStatusPresentation.present(studentStatusAbstraction.isPass());
    }

    /**
     *a getter.
     *
     * @return the status of the student.
     */
    public boolean getStatus() {
        return studentStatusAbstraction.isPass();
    }

    /**
     * present status information to user.
     */
    public void present() {
        studentStatusPresentation.present(getStatus());
    }
}
