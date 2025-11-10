package com.no99.edunexuscourse_management.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.no99.edunexuscourse_management.entity.Course;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/**
 * 课程创建/更新请求DTO
 */
public class CourseRequest {

    @NotBlank(message = "课程标题不能为空")
    @Size(max = 200, message = "课程标题长度不能超过200个字符")
    private String title;

    @NotBlank(message = "课程代码不能为空")
    @Size(max = 50, message = "课程代码长度不能超过50个字符")
    private String code;

    private String description;

    @NotNull(message = "教师ID不能为空")
    @Positive(message = "教师ID必须为正整数")
    private Integer instructorId;

    private Integer credits;

    private Integer maxStudents;

    private Boolean isPublic;

    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    // 构造函数
    public CourseRequest() {}

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Integer instructorId) {
        this.instructorId = instructorId;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public Integer getMaxStudents() {
        return maxStudents;
    }

    public void setMaxStudents(Integer maxStudents) {
        this.maxStudents = maxStudents;
    }

    public Boolean getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * 转换为Course实体
     */
    public Course toEntity() {
        Course course = new Course();
        course.setTitle(this.title);
        course.setCode(this.code);
        course.setDescription(this.description);
        course.setInstructorId(this.instructorId);
        course.setCredits(this.credits != null ? this.credits : 0);
        course.setMaxStudents(this.maxStudents != null ? this.maxStudents : 0);
        course.setIsPublic(this.isPublic != null ? this.isPublic : false);

        if (this.status != null) {
            course.setStatus(Course.CourseStatus.fromValue(this.status));
        }

        course.setStartDate(this.startDate);
        course.setEndDate(this.endDate);

        return course;
    }

    /**
     * 更新现有课程实体
     */
    public void updateEntity(Course course) {
        if (this.title != null) {
            course.setTitle(this.title);
        }
        if (this.code != null) {
            course.setCode(this.code);
        }
        if (this.description != null) {
            course.setDescription(this.description);
        }
        if (this.instructorId != null) {
            course.setInstructorId(this.instructorId);
        }
        if (this.credits != null) {
            course.setCredits(this.credits);
        }
        if (this.maxStudents != null) {
            course.setMaxStudents(this.maxStudents);
        }
        if (this.isPublic != null) {
            course.setIsPublic(this.isPublic);
        }
        if (this.status != null) {
            course.setStatus(Course.CourseStatus.fromValue(this.status));
        }
        if (this.startDate != null) {
            course.setStartDate(this.startDate);
        }
        if (this.endDate != null) {
            course.setEndDate(this.endDate);
        }
    }
}
