package com.no99.edunexuscourse_management.mapper;

import com.no99.edunexuscourse_management.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 课程数据访问层
 */
@Mapper
public interface CourseMapper {

    /**
     * 插入课程
     */
    @Insert("INSERT INTO courses (title, code, description, instructor_id, credits, max_students, is_public, status, start_date, end_date) " +
            "VALUES (#{title}, #{code}, #{description}, #{instructorId}, #{credits}, #{maxStudents}, #{isPublic}, #{status}, #{startDate}, #{endDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Course course);

    /**
     * 根据ID查询课程
     */
    @Select("SELECT c.*, u.username, u.first_name, u.last_name, u.email " +
            "FROM courses c " +
            "LEFT JOIN users u ON c.instructor_id = u.id " +
            "WHERE c.id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "title", column = "title"),
        @Result(property = "code", column = "code"),
        @Result(property = "description", column = "description"),
        @Result(property = "instructorId", column = "instructor_id"),
        @Result(property = "credits", column = "credits"),
        @Result(property = "maxStudents", column = "max_students"),
        @Result(property = "isPublic", column = "is_public"),
        @Result(property = "status", column = "status"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "instructor.id", column = "instructor_id"),
        @Result(property = "instructor.username", column = "username"),
        @Result(property = "instructor.firstName", column = "first_name"),
        @Result(property = "instructor.lastName", column = "last_name"),
        @Result(property = "instructor.email", column = "email")
    })
    Course findById(Integer id);

    /**
     * 查询所有课程
     */
    @Select("SELECT c.*, u.username, u.first_name, u.last_name, u.email " +
            "FROM courses c " +
            "LEFT JOIN users u ON c.instructor_id = u.id " +
            "ORDER BY c.created_at DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "title", column = "title"),
        @Result(property = "code", column = "code"),
        @Result(property = "description", column = "description"),
        @Result(property = "instructorId", column = "instructor_id"),
        @Result(property = "credits", column = "credits"),
        @Result(property = "maxStudents", column = "max_students"),
        @Result(property = "isPublic", column = "is_public"),
        @Result(property = "status", column = "status"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "instructor.id", column = "instructor_id"),
        @Result(property = "instructor.username", column = "username"),
        @Result(property = "instructor.firstName", column = "first_name"),
        @Result(property = "instructor.lastName", column = "last_name"),
        @Result(property = "instructor.email", column = "email")
    })
    List<Course> findAll();

    /**
     * 根据教师ID查询课程
     */
    @Select("SELECT c.*, u.username, u.first_name, u.last_name, u.email " +
            "FROM courses c " +
            "LEFT JOIN users u ON c.instructor_id = u.id " +
            "WHERE c.instructor_id = #{instructorId} " +
            "ORDER BY c.created_at DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "title", column = "title"),
        @Result(property = "code", column = "code"),
        @Result(property = "description", column = "description"),
        @Result(property = "instructorId", column = "instructor_id"),
        @Result(property = "credits", column = "credits"),
        @Result(property = "maxStudents", column = "max_students"),
        @Result(property = "isPublic", column = "is_public"),
        @Result(property = "status", column = "status"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "instructor.id", column = "instructor_id"),
        @Result(property = "instructor.username", column = "username"),
        @Result(property = "instructor.firstName", column = "first_name"),
        @Result(property = "instructor.lastName", column = "last_name"),
        @Result(property = "instructor.email", column = "email")
    })
    List<Course> findByInstructorId(Integer instructorId);

    /**
     * 查询公开课程
     */
    @Select("SELECT c.*, u.username, u.first_name, u.last_name, u.email " +
            "FROM courses c " +
            "LEFT JOIN users u ON c.instructor_id = u.id " +
            "WHERE c.is_public = 1 AND c.status = 'published' " +
            "ORDER BY c.created_at DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "title", column = "title"),
        @Result(property = "code", column = "code"),
        @Result(property = "description", column = "description"),
        @Result(property = "instructorId", column = "instructor_id"),
        @Result(property = "credits", column = "credits"),
        @Result(property = "maxStudents", column = "max_students"),
        @Result(property = "isPublic", column = "is_public"),
        @Result(property = "status", column = "status"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "instructor.id", column = "instructor_id"),
        @Result(property = "instructor.username", column = "username"),
        @Result(property = "instructor.firstName", column = "first_name"),
        @Result(property = "instructor.lastName", column = "last_name"),
        @Result(property = "instructor.email", column = "email")
    })
    List<Course> findPublicCourses();

    /**
     * 根据课程代码查询课程
     */
    @Select("SELECT * FROM courses WHERE code = #{code}")
    Course findByCode(String code);

    /**
     * 更新课程
     */
    @Update("UPDATE courses SET " +
            "title = #{title}, " +
            "code = #{code}, " +
            "description = #{description}, " +
            "instructor_id = #{instructorId}, " +
            "credits = #{credits}, " +
            "max_students = #{maxStudents}, " +
            "is_public = #{isPublic}, " +
            "status = #{status}, " +
            "start_date = #{startDate}, " +
            "end_date = #{endDate} " +
            "WHERE id = #{id}")
    int update(Course course);

    /**
     * 删除课程
     */
    @Delete("DELETE FROM courses WHERE id = #{id}")
    int deleteById(Integer id);

    /**
     * 搜索课程
     */
    @Select("SELECT c.*, u.username, u.first_name, u.last_name, u.email " +
            "FROM courses c " +
            "LEFT JOIN users u ON c.instructor_id = u.id " +
            "WHERE c.title LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR c.code LIKE CONCAT('%', #{keyword}, '%') " +
            "   OR c.description LIKE CONCAT('%', #{keyword}, '%') " +
            "ORDER BY c.created_at DESC")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "title", column = "title"),
        @Result(property = "code", column = "code"),
        @Result(property = "description", column = "description"),
        @Result(property = "instructorId", column = "instructor_id"),
        @Result(property = "credits", column = "credits"),
        @Result(property = "maxStudents", column = "max_students"),
        @Result(property = "isPublic", column = "is_public"),
        @Result(property = "status", column = "status"),
        @Result(property = "startDate", column = "start_date"),
        @Result(property = "endDate", column = "end_date"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "instructor.id", column = "instructor_id"),
        @Result(property = "instructor.username", column = "username"),
        @Result(property = "instructor.firstName", column = "first_name"),
        @Result(property = "instructor.lastName", column = "last_name"),
        @Result(property = "instructor.email", column = "email")
    })
    List<Course> searchCourses(String keyword);
}
