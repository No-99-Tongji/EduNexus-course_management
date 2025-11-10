package com.no99.edunexuscourse_management.mapper;

import com.no99.edunexuscourse_management.entity.Module;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * 课程模块数据访问层
 */
@Mapper
public interface ModuleMapper {

    /**
     * 插入模块
     */
    @Insert("INSERT INTO modules (course_id, title, description, order_index, is_published) " +
            "VALUES (#{courseId}, #{title}, #{description}, #{orderIndex}, #{isPublished})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Module module);

    /**
     * 根据ID查询模块
     */
    @Select("SELECT m.*, c.title AS course_title, c.code AS course_code " +
            "FROM modules m " +
            "LEFT JOIN courses c ON m.course_id = c.id " +
            "WHERE m.id = #{id}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "courseId", column = "course_id"),
        @Result(property = "title", column = "title"),
        @Result(property = "description", column = "description"),
        @Result(property = "orderIndex", column = "order_index"),
        @Result(property = "isPublished", column = "is_published"),
        @Result(property = "publishedAt", column = "published_at"),
        @Result(property = "createdAt", column = "created_at"),
        @Result(property = "updatedAt", column = "updated_at"),
        @Result(property = "course.id", column = "course_id"),
        @Result(property = "course.title", column = "course_title"),
        @Result(property = "course.code", column = "course_code")
    })
    Module findById(Integer id);

    /**
     * 根据课程ID查询所有模块
     */
    @Select("SELECT * FROM modules " +
            "WHERE course_id = #{courseId} " +
            "ORDER BY order_index ASC, created_at ASC")
    List<Module> findByCourseId(Integer courseId);

    /**
     * 根据课程ID查询已发布的模块
     */
    @Select("SELECT * FROM modules " +
            "WHERE course_id = #{courseId} AND is_published = 1 " +
            "ORDER BY order_index ASC, created_at ASC")
    List<Module> findPublishedByCourseId(Integer courseId);

    /**
     * 获取课程中下一个排序序号
     */
    @Select("SELECT COALESCE(MAX(order_index), 0) + 1 FROM modules WHERE course_id = #{courseId}")
    int getNextOrderIndex(Integer courseId);

    /**
     * 更新模块
     */
    @Update("UPDATE modules SET " +
            "title = #{title}, " +
            "description = #{description}, " +
            "order_index = #{orderIndex}, " +
            "is_published = #{isPublished}, " +
            "published_at = #{publishedAt} " +
            "WHERE id = #{id}")
    int update(Module module);

    /**
     * 更新模块发布状态
     */
    @Update("UPDATE modules SET " +
            "is_published = #{isPublished}, " +
            "published_at = #{publishedAt} " +
            "WHERE id = #{id}")
    int updatePublishStatus(Module module);

    /**
     * 删除模块
     */
    @Delete("DELETE FROM modules WHERE id = #{id}")
    int deleteById(Integer id);

    /**
     * 根据课程ID删除所有模块
     */
    @Delete("DELETE FROM modules WHERE course_id = #{courseId}")
    int deleteByCourseId(Integer courseId);

    /**
     * 批量更新模块排序
     */
    @Update("UPDATE modules SET order_index = #{orderIndex} WHERE id = #{id}")
    int updateOrderIndex(@Param("id") Integer id, @Param("orderIndex") Integer orderIndex);
}
