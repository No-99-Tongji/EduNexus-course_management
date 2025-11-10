package com.no99.edunexuscourse_management.mapper;

import com.no99.edunexuscourse_management.entity.Enrollment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 课程注册数据访问层
 */
@Mapper
public interface EnrollmentMapper {

    /**
     * 插入注册记录
     */
    @Insert("INSERT INTO enrollments (user_id, course_id, role, enrollment_status) " +
            "VALUES (#{userId}, #{courseId}, #{role}, #{enrollmentStatus})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Enrollment enrollment);

    /**
     * 根据ID查询注册记录
     */
    @Select("SELECT e.*, " +
            "u.username, u.first_name, u.last_name, u.email, " +
            "c.title AS course_title, c.code AS course_code " +
            "FROM enrollments e " +
            "LEFT JOIN users u ON e.user_id = u.id " +
            "LEFT JOIN courses c ON e.course_id = c.id " +
            "WHERE e.id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "role", column = "role"),
        @Result(property = "enrollmentStatus", column = "enrollment_status"),
        @Result(property = "enrolledAt", column = "enrolled_at"),
        @Result(property = "completedAt", column = "completed_at"),
        @Result(property = "user.id", column = "user_id"),
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.firstName", column = "first_name"),
        @Result(property = "user.lastName", column = "last_name"),
        @Result(property = "user.email", column = "email"),
        @Result(property = "course.id", column = "course_id"),
        @Result(property = "course.title", column = "course_title"),
        @Result(property = "course.code", column = "course_code")
    })
    Enrollment findById(Integer id);

    /**
     * 根据用户ID查询注册记录
     */
    @Select("SELECT e.*, " +
            "c.title AS course_title, c.code AS course_code, c.description AS course_description " +
            "FROM enrollments e " +
            "LEFT JOIN courses c ON e.course_id = c.id " +
            "WHERE e.user_id = #{userId} AND e.enrollment_status = 'active' " +
            "ORDER BY e.enrolled_at DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "role", column = "role"),
        @Result(property = "enrollmentStatus", column = "enrollment_status"),
        @Result(property = "enrolledAt", column = "enrolled_at"),
        @Result(property = "completedAt", column = "completed_at"),
        @Result(property = "course.id", column = "course_id"),
        @Result(property = "course.title", column = "course_title"),
        @Result(property = "course.code", column = "course_code"),
        @Result(property = "course.description", column = "course_description")
    })
    List<Enrollment> findByUserId(Integer userId);

    /**
     * 根据课程ID查询注册记录
     */
    @Select("SELECT e.*, " +
            "u.username, u.first_name, u.last_name, u.email " +
            "FROM enrollments e " +
            "LEFT JOIN users u ON e.user_id = u.id " +
            "WHERE e.course_id = #{courseId} AND e.enrollment_status = 'active' " +
            "ORDER BY e.enrolled_at DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "role", column = "role"),
        @Result(property = "enrollmentStatus", column = "enrollment_status"),
        @Result(property = "enrolledAt", column = "enrolled_at"),
        @Result(property = "completedAt", column = "completed_at"),
        @Result(property = "user.id", column = "user_id"),
        @Result(property = "user.username", column = "username"),
        @Result(property = "user.firstName", column = "first_name"),
        @Result(property = "user.lastName", column = "last_name"),
        @Result(property = "user.email", column = "email")
    })
    List<Enrollment> findByCourseId(Integer courseId);

    /**
     * 检查用户是否已注册课程
     */
    @Select("SELECT COUNT(*) FROM enrollments " +
            "WHERE user_id = #{userId} AND course_id = #{courseId} AND enrollment_status = 'active'")
    int isUserEnrolled(Integer userId, Integer courseId);

    /**
     * 更新注册状态
     */
    @Update("UPDATE enrollments SET " +
            "enrollment_status = #{enrollmentStatus}, " +
            "completed_at = #{completedAt} " +
            "WHERE id = #{id}")
    int updateStatus(Enrollment enrollment);

    /**
     * 删除注册记录
     */
    @Delete("DELETE FROM enrollments WHERE id = #{id}")
    int deleteById(Integer id);

    /**
     * 统计课程注册人数
     */
    @Select("SELECT COUNT(*) FROM enrollments " +
            "WHERE course_id = #{courseId} AND enrollment_status = 'active'")
    int countByCourseId(Integer courseId);
}
