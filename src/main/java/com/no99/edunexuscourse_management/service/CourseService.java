package com.no99.edunexuscourse_management.service;

import com.no99.edunexuscourse_management.entity.Course;
import com.no99.edunexuscourse_management.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 课程服务实现类
 */
@Service
@Transactional
public class CourseService {

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 创建课程
     */
    public Course createCourse(Course course) {
        // 检查课程代码是否重复
        Course existingCourse = courseMapper.findByCode(course.getCode());
        if (existingCourse != null) {
            throw new IllegalArgumentException("课程代码已存在: " + course.getCode());
        }

        courseMapper.insert(course);
        return courseMapper.findById(course.getId());
    }

    /**
     * 根据ID获取课程
     */
    @Transactional(readOnly = true)
    public Course getCourseById(Integer id) {
        Course course = courseMapper.findById(id);
        if (course == null) {
            throw new RuntimeException("课程不存在: " + id);
        }
        return course;
    }

    /**
     * 获取所有课程
     */
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() {
        return courseMapper.findAll();
    }

    /**
     * 根据教师ID获取课程
     */
    @Transactional(readOnly = true)
    public List<Course> getCoursesByInstructor(Integer instructorId) {
        return courseMapper.findByInstructorId(instructorId);
    }

    /**
     * 获取公开课程
     */
    @Transactional(readOnly = true)
    public List<Course> getPublicCourses() {
        return courseMapper.findPublicCourses();
    }

    /**
     * 搜索课程
     */
    @Transactional(readOnly = true)
    public List<Course> searchCourses(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllCourses();
        }
        return courseMapper.searchCourses(keyword.trim());
    }

    /**
     * 更新课程
     */
    public Course updateCourse(Integer id, Course course) {
        // 检查课程是否存在
        Course existingCourse = getCourseById(id);

        // 检查课程代码是否与其他课程冲突
        if (!existingCourse.getCode().equals(course.getCode())) {
            Course duplicateCourse = courseMapper.findByCode(course.getCode());
            if (duplicateCourse != null && !duplicateCourse.getId().equals(id)) {
                throw new IllegalArgumentException("课程代码已存在: " + course.getCode());
            }
        }

        course.setId(id);
        courseMapper.update(course);
        return courseMapper.findById(id);
    }

    /**
     * 删除课程
     */
    public void deleteCourse(Integer id) {
        Course course = getCourseById(id);
        courseMapper.deleteById(id);
    }

    /**
     * 发布课程
     */
    public Course publishCourse(Integer id) {
        Course course = getCourseById(id);
        course.setStatus(Course.CourseStatus.PUBLISHED);
        courseMapper.update(course);
        return courseMapper.findById(id);
    }

    /**
     * 归档课程
     */
    public Course archiveCourse(Integer id) {
        Course course = getCourseById(id);
        course.setStatus(Course.CourseStatus.ARCHIVED);
        courseMapper.update(course);
        return courseMapper.findById(id);
    }

    /**
     * 检查课程代码是否可用
     */
    @Transactional(readOnly = true)
    public boolean isCourseCodeAvailable(String code, Integer excludeId) {
        Course course = courseMapper.findByCode(code);
        return course == null || (excludeId != null && course.getId().equals(excludeId));
    }
}
