/*
 * This project is licensed under the MIT license.
 *
 * The MIT License
 * Copyright © 2014-2025 Enhanced Student Management System
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */
package com.iluwatar.datamapper;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.Builder;

/**
 * Enhanced Student class with additional attributes and methods.
 * Implements Serializable for persistence and Comparable for sorting.
 */
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public final class Student implements Serializable, Comparable<Student> {
  
  @Serial 
  private static final long serialVersionUID = 1L;
  
  @EqualsAndHashCode.Include 
  private int studentId;
  
  private String name;
  private char grade;
  
  // New fields
  private String email;
  private String phoneNumber;
  private LocalDate dateOfBirth;
  private String address;
  private String major;
  private double gpa;
  private LocalDate enrollmentDate;
  private StudentStatus status;
  private String guardian;
  private String guardianPhone;
  
  @Builder.Default
  private List<Course> enrolledCourses = new ArrayList<>();
  
  @Builder.Default
  private List<Grade> grades = new ArrayList<>();
  
  /**
   * Calculate student's age based on date of birth.
   * @return age in years
   */
  public int getAge() {
    if (dateOfBirth == null) {
      return 0;
    }
    return Period.between(dateOfBirth, LocalDate.now()).getYears();
  }
  
  /**
   * Check if student is eligible for graduation.
   * @return true if GPA >= 2.0 and status is ACTIVE
   */
  public boolean isEligibleForGraduation() {
    return gpa >= 2.0 && status == StudentStatus.ACTIVE;
  }
  
  /**
   * Add a course to the student's enrollment.
   * @param course the course to enroll in
   */
  public void enrollInCourse(Course course) {
    if (course != null && !enrolledCourses.contains(course)) {
      enrolledCourses.add(course);
    }
  }
  
  /**
   * Remove a course from student's enrollment.
   * @param course the course to drop
   */
  public void dropCourse(Course course) {
    enrolledCourses.remove(course);
  }
  
  /**
   * Add a grade record.
   * @param grade the grade to add
   */
  public void addGrade(Grade grade) {
    if (grade != null) {
      grades.add(grade);
    }
  }
  
  /**
   * Calculate average GPA from all grades.
   * @return calculated GPA
   */
  public double calculateGPA() {
    if (grades.isEmpty()) {
      return 0.0;
    }
    return grades.stream()
        .mapToDouble(Grade::getGradePoint)
        .average()
        .orElse(0.0);
  }
  
  /**
   * Get total credits from enrolled courses.
   * @return total credits
   */
  public int getTotalCredits() {
    return enrolledCourses.stream()
        .mapToInt(Course::getCredits)
        .sum();
  }
  
  /**
   * Check if student is a full-time student (12+ credits).
   * @return true if full-time
   */
  public boolean isFullTime() {
    return getTotalCredits() >= 12;
  }
  
  @Override
  public int compareTo(Student other) {
    return Integer.compare(this.studentId, other.studentId);
  }
}

/**
 * Enum representing student status.
 */
enum StudentStatus {
  ACTIVE,
  INACTIVE,
  GRADUATED,
  SUSPENDED,
  ON_LEAVE
}

/**
 * Course class representing a course.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
class Course implements Serializable {
  
  @Serial 
  private static final long serialVersionUID = 1L;
  
  @EqualsAndHashCode.Include
  private String courseId;
  
  private String courseName;
  private String instructor;
  private int credits;
  private String department;
  private String description;
  private int maxCapacity;
  
  @Builder.Default
  private List<Student> enrolledStudents = new ArrayList<>();
  
  /**
   * Check if course is full.
   * @return true if at capacity
   */
  public boolean isFull() {
    return enrolledStudents.size() >= maxCapacity;
  }
  
  /**
   * Get current enrollment count.
   * @return number of enrolled students
   */
  public int getCurrentEnrollment() {
    return enrolledStudents.size();
  }
}

/**
 * Grade class representing a student's grade in a course.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
class Grade implements Serializable {
  
  @Serial 
  private static final long serialVersionUID = 1L;
  
  private String courseId;
  private String courseName;
  private char letterGrade;
  private double numericGrade;
  private int semester;
  private int year;
  
  /**
   * Convert letter grade to grade point.
   * @return grade point (4.0 scale)
   */
  public double getGradePoint() {
    return switch (letterGrade) {
      case 'A' -> 4.0;
      case 'B' -> 3.0;
      case 'C' -> 2.0;
      case 'D' -> 1.0;
      case 'F' -> 0.0;
      default -> 0.0;
    };
  }
  
  /**
   * Check if grade is passing.
   * @return true if grade is D or better
   */
  public boolean isPassing() {
    return letterGrade != 'F';
  }
}

/**
 * StudentMapper interface for data access operations.
 */
interface StudentMapper {
  
  /**
   * Insert a new student.
   * @param student the student to insert
   * @return the inserted student with generated ID
   */
  Student insert(Student student);
  
  /**
   * Update an existing student.
   * @param student the student to update
   * @return the updated student
   */
  Student update(Student student);
  
  /**
   * Delete a student by ID.
   * @param studentId the student ID
   * @return true if deleted successfully
   */
  boolean delete(int studentId);
  
  /**
   * Find student by ID.
   * @param studentId the student ID
   * @return the student or null if not found
   */
  Student findById(int studentId);
  
  /**
   * Find all students.
   * @return list of all students
   */
  List<Student> findAll();
  
  /**
   * Find students by name.
   * @param name the name to search for
   * @return list of matching students
   */
  List<Student> findByName(String name);
  
  /**
   * Find students by grade.
   * @param grade the grade to search for
   * @return list of students with that grade
   */
  List<Student> findByGrade(char grade);
  
  /**
   * Find students by status.
   * @param status the status to search for
   * @return list of students with that status
   */
  List<Student> findByStatus(StudentStatus status);
  
  /**
   * Find students by major.
   * @param major the major to search for
   * @return list of students with that major
   */
  List<Student> findByMajor(String major);
}

/**
 * In-memory implementation of StudentMapper.
 */
class InMemoryStudentMapper implements StudentMapper {
  
  private final List<Student> students = new ArrayList<>();
  private int nextId = 1;
  
  @Override
  public Student insert(Student student) {
    student.setStudentId(nextId++);
    students.add(student);
    return student;
  }
  
  @Override
  public Student update(Student student) {
    for (int i = 0; i < students.size(); i++) {
      if (students.get(i).getStudentId() == student.getStudentId()) {
        students.set(i, student);
        return student;
      }
    }
    return null;
  }
  
  @Override
  public boolean delete(int studentId) {
    return students.removeIf(s -> s.getStudentId() == studentId);
  }
  
  @Override
  public Student findById(int studentId) {
    return students.stream()
        .filter(s -> s.getStudentId() == studentId)
        .findFirst()
        .orElse(null);
  }
  
  @Override
  public List<Student> findAll() {
    return new ArrayList<>(students);
  }
  
  @Override
  public List<Student> findByName(String name) {
    return students.stream()
        .filter(s -> s.getName().toLowerCase().contains(name.toLowerCase()))
        .toList();
  }
  
  @Override
  public List<Student> findByGrade(char grade) {
    return students.stream()
        .filter(s -> s.getGrade() == grade)
        .toList();
  }
  
  @Override
  public List<Student> findByStatus(StudentStatus status) {
    return students.stream()
        .filter(s -> s.getStatus() == status)
        .toList();
  }
  
  @Override
  public List<Student> findByMajor(String major) {
    return students.stream()
        .filter(s -> major.equalsIgnoreCase(s.getMajor()))
        .toList();
  }
}

/**
 * Student service class for business logic.
 */
class StudentService {
  
  private final StudentMapper studentMapper;
  
  public StudentService(StudentMapper studentMapper) {
    this.studentMapper = Objects.requireNonNull(studentMapper);
  }
  
  /**
   * Register a new student.
   */
  public Student registerStudent(Student student) {
    if (student.getEnrollmentDate() == null) {
      student.setEnrollmentDate(LocalDate.now());
    }
    if (student.getStatus() == null) {
      student.setStatus(StudentStatus.ACTIVE);
    }
    return studentMapper.insert(student);
  }
  
  /**
   * Update student information.
   */
  public Student updateStudentInfo(Student student) {
    Student existing = studentMapper.findById(student.getStudentId());
    if (existing == null) {
      throw new IllegalArgumentException("Student not found");
    }
    return studentMapper.update(student);
  }
  
  /**
   * Enroll student in a course.
   */
  public void enrollStudentInCourse(int studentId, Course course) {
    Student student = studentMapper.findById(studentId);
    if (student == null) {
      throw new IllegalArgumentException("Student not found");
    }
    if (course.isFull()) {
      throw new IllegalStateException("Course is full");
    }
    student.enrollInCourse(course);
    course.getEnrolledStudents().add(student);
    studentMapper.update(student);
  }
  
  /**
   * Calculate and update student's GPA.
   */
  public void updateStudentGPA(int studentId) {
    Student student = studentMapper.findById(studentId);
    if (student != null) {
      double gpa = student.calculateGPA();
      student.setGpa(gpa);
      studentMapper.update(student);
    }
  }
  
  /**
   * Get students eligible for graduation.
   */
  public List<Student> getEligibleGraduates() {
    return studentMapper.findAll().stream()
        .filter(Student::isEligibleForGraduation)
        .toList();
  }
  
  /**
   * Get full-time students.
   */
  public List<Student> getFullTimeStudents() {
    return studentMapper.findAll().stream()
        .filter(Student::isFullTime)
        .toList();
  }
}

/**
 * Example usage and testing.
 */
class StudentManagementExample {
  
  public static void main(String[] args) {
    // Create mapper and service
    StudentMapper mapper = new InMemoryStudentMapper();
    StudentService service = new StudentService(mapper);
    
    // Create sample students
    Student student1 = Student.builder()
        .name("John Doe")
        .grade('A')
        .email("john.doe@university.edu")
        .phoneNumber("123-456-7890")
        .dateOfBirth(LocalDate.of(2002, 5, 15))
        .address("123 Main St, City, State 12345")
        .major("Computer Science")
        .gpa(3.8)
        .status(StudentStatus.ACTIVE)
        .guardian("Jane Doe")
        .guardianPhone("098-765-4321")
        .build();
    
    Student student2 = Student.builder()
        .name("Alice Smith")
        .grade('B')
        .email("alice.smith@university.edu")
        .phoneNumber("234-567-8901")
        .dateOfBirth(LocalDate.of(2001, 8, 20))
        .address("456 Oak Ave, City, State 12345")
        .major("Mathematics")
        .gpa(3.5)
        .status(StudentStatus.ACTIVE)
        .guardian("Bob Smith")
        .guardianPhone("876-543-2109")
        .build();
    
    // Register students
    student1 = service.registerStudent(student1);
    student2 = service.registerStudent(student2);
    
    // Create courses
    Course course1 = Course.builder()
        .courseId("CS101")
        .courseName("Introduction to Programming")
        .instructor("Dr. Johnson")
        .credits(3)
        .department("Computer Science")
        .description("Basic programming concepts")
        .maxCapacity(30)
        .build();
    
    Course course2 = Course.builder()
        .courseId("MATH201")
        .courseName("Calculus II")
        .instructor("Prof. Williams")
        .credits(4)
        .department("Mathematics")
        .description("Advanced calculus")
        .maxCapacity(25)
        .build();
    
    // Enroll students in courses
    service.enrollStudentInCourse(student1.getStudentId(), course1);
    service.enrollStudentInCourse(student2.getStudentId(), course2);
    
    // Add grades
    Grade grade1 = Grade.builder()
        .courseId("CS101")
        .courseName("Introduction to Programming")
        .letterGrade('A')
        .numericGrade(95.0)
        .semester(1)
        .year(2024)
        .build();
    
    student1.addGrade(grade1);
    service.updateStudentGPA(student1.getStudentId());
    
    // Display information
    System.out.println("Student Information:");
    System.out.println("Name: " + student1.getName());
    System.out.println("Age: " + student1.getAge());
    System.out.println("GPA: " + student1.getGpa());
    System.out.println("Full-time: " + student1.isFullTime());
    System.out.println("Eligible for graduation: " + student1.isEligibleForGraduation());
    System.out.println("Total Credits: " + student1.getTotalCredits());
  }
}
