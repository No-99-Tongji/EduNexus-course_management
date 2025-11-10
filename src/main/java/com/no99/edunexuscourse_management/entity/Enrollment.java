package com.no99.edunexuscourse_management.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

/**
 * 课程注册实体类
 */
public class Enrollment {

    private Integer id;
    private Integer userId;
    private Integer courseId;
    private EnrollmentRole role;
    private EnrollmentStatus enrollmentStatus;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enrolledAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime completedAt;

    // 关联的用户信息
    private User user;

    // 关联的课程信息
    private Course course;

    // 注册角色枚举
    public enum EnrollmentRole {
        STUDENT("student"),
        TA("ta");

        private final String value;

        EnrollmentRole(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static EnrollmentRole fromValue(String value) {
            for (EnrollmentRole role : EnrollmentRole.values()) {
                if (role.value.equals(value)) {
                    return role;
                }
            }
            throw new IllegalArgumentException("Unknown enrollment role: " + value);
        }
    }

    // 注册状态枚举
    public enum EnrollmentStatus {
        ACTIVE("active"),
        DROPPED("dropped"),
        COMPLETED("completed");

        private final String value;

        EnrollmentStatus(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static EnrollmentStatus fromValue(String value) {
            for (EnrollmentStatus status : EnrollmentStatus.values()) {
                if (status.value.equals(value)) {
                    return status;
                }
            }
            throw new IllegalArgumentException("Unknown enrollment status: " + value);
        }
    }

    // 构造函数
    public Enrollment() {}

    public Enrollment(Integer userId, Integer courseId) {
        this.userId = userId;
        this.courseId = courseId;
        this.role = EnrollmentRole.STUDENT;
        this.enrollmentStatus = EnrollmentStatus.ACTIVE;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public EnrollmentRole getRole() {
        return role;
    }

    public void setRole(EnrollmentRole role) {
        this.role = role;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    public void setEnrolledAt(LocalDateTime enrolledAt) {
        this.enrolledAt = enrolledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "id=" + id +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", role=" + role +
                ", enrollmentStatus=" + enrollmentStatus +
                ", enrolledAt=" + enrolledAt +
                '}';
    }
}
