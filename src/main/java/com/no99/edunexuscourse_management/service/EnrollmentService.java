package com.no99.edunexuscourse_management.service;

import com.no99.edunexuscourse_management.entity.Enrollment;
import com.no99.edunexuscourse_management.mapper.EnrollmentMapper;
import com.no99.edunexuscourse_management.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程注册服务实现类
 */
@Service
@Transactional
public class EnrollmentService {

    @Autowired
    private EnrollmentMapper enrollmentMapper;

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 注册课程
     */
    public Enrollment enrollCourse(Integer userId, Integer courseId) {
        // 检查是否已经注册
        if (enrollmentMapper.isUserEnrolled(userId, courseId) > 0) {
            throw new IllegalArgumentException("用户已经注册了该课程");
        }

        // 检查课程是否存在
        if (courseMapper.findById(courseId) == null) {
            throw new RuntimeException("课程不存在: " + courseId);
        }

        Enrollment enrollment = new Enrollment(userId, courseId);
        enrollmentMapper.insert(enrollment);
        return enrollmentMapper.findById(enrollment.getId());
    }

    /**
     * 退课
     */
    public void dropCourse(Integer userId, Integer courseId) {
        List<Enrollment> enrollments = enrollmentMapper.findByUserId(userId);
        Enrollment enrollment = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId) &&
                           e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到有效的课程注册记录"));

        enrollment.setEnrollmentStatus(Enrollment.EnrollmentStatus.DROPPED);
        enrollmentMapper.updateStatus(enrollment);
    }

    /**
     * 完成课程
     */
    public void completeCourse(Integer userId, Integer courseId) {
        List<Enrollment> enrollments = enrollmentMapper.findByUserId(userId);
        Enrollment enrollment = enrollments.stream()
                .filter(e -> e.getCourseId().equals(courseId) &&
                           e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("未找到有效的课程注册记录"));

        enrollment.setEnrollmentStatus(Enrollment.EnrollmentStatus.COMPLETED);
        enrollment.setCompletedAt(LocalDateTime.now());
        enrollmentMapper.updateStatus(enrollment);
    }

    /**
     * 获取用户的课程注册列表
     */
    @Transactional(readOnly = true)
    public List<Enrollment> getUserEnrollments(Integer userId) {
        return enrollmentMapper.findByUserId(userId);
    }

    /**
     * 获取课程的学生列表
     */
    @Transactional(readOnly = true)
    public List<Enrollment> getCourseEnrollments(Integer courseId) {
        return enrollmentMapper.findByCourseId(courseId);
    }

    /**
     * 检查用户是否已注册课程
     */
    @Transactional(readOnly = true)
    public boolean isUserEnrolled(Integer userId, Integer courseId) {
        return enrollmentMapper.isUserEnrolled(userId, courseId) > 0;
    }

    /**
     * 获取课程注册人数
     */
    @Transactional(readOnly = true)
    public int getCourseEnrollmentCount(Integer courseId) {
        return enrollmentMapper.countByCourseId(courseId);
    }

    /**
     * 根据ID获取注册记录
     */
    @Transactional(readOnly = true)
    public Enrollment getEnrollmentById(Integer id) {
        Enrollment enrollment = enrollmentMapper.findById(id);
        if (enrollment == null) {
            throw new RuntimeException("注册记录不存在: " + id);
        }
        return enrollment;
    }

    /**
     * 删除注册记录
     */
    public void deleteEnrollment(Integer id) {
        getEnrollmentById(id); // 检查是否存在
        enrollmentMapper.deleteById(id);
    }
}
