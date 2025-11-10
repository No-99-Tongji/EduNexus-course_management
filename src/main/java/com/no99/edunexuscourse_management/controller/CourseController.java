package com.no99.edunexuscourse_management.controller;

import com.no99.edunexuscourse_management.dto.ApiResponse;
import com.no99.edunexuscourse_management.dto.CourseRequest;
import com.no99.edunexuscourse_management.entity.Course;
import com.no99.edunexuscourse_management.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理控制器
 */
@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "*")
@Tag(name = "课程管理", description = "课程相关的API接口")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 创建课程
     */
    @Operation(summary = "创建课程", description = "创建一个新的课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "课程创建成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "请求参数错误"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping
    public ApiResponse<Course> createCourse(@Valid @RequestBody CourseRequest request) {
        try {
            Course course = request.toEntity();
            Course createdCourse = courseService.createCourse(course);
            return ApiResponse.success("课程创建成功", createdCourse);
        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("创建课程失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取课程
     */
    @Operation(summary = "根据ID获取课程", description = "通过课程ID获取课程详细信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{id}")
    public ApiResponse<Course> getCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer id) {
        try {
            Course course = courseService.getCourseById(id);
            return ApiResponse.success(course);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("获取课程失败: " + e.getMessage());
        }
    }

    /**
     * 获取所有课程
     */
    @Operation(summary = "获取所有课程", description = "获取系统中所有课程的列表")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping
    public ApiResponse<List<Course>> getAllCourses() {
        try {
            List<Course> courses = courseService.getAllCourses();
            return ApiResponse.success(courses);
        } catch (Exception e) {
            return ApiResponse.error("获取课程列表失败: " + e.getMessage());
        }
    }

    /**
     * 根据教师ID获取课程
     */
    @Operation(summary = "根据教师ID获取课程", description = "获取指定教师的所有课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/instructor/{instructorId}")
    public ApiResponse<List<Course>> getCoursesByInstructor(@Parameter(description = "教师ID", required = true) @PathVariable Integer instructorId) {
        try {
            List<Course> courses = courseService.getCoursesByInstructor(instructorId);
            return ApiResponse.success(courses);
        } catch (Exception e) {
            return ApiResponse.error("获取教师课程失败: " + e.getMessage());
        }
    }

    /**
     * 获取公开课程
     */
    @Operation(summary = "获取公开课程", description = "获取所有公开状态的课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/public")
    public ApiResponse<List<Course>> getPublicCourses() {
        try {
            List<Course> courses = courseService.getPublicCourses();
            return ApiResponse.success(courses);
        } catch (Exception e) {
            return ApiResponse.error("获取公开课程失败: " + e.getMessage());
        }
    }

    /**
     * 搜索课程
     */
    @Operation(summary = "搜索课程", description = "根据关键词搜索课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "搜索成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/search")
    public ApiResponse<List<Course>> searchCourses(@Parameter(description = "搜索关键词", required = false) @RequestParam(required = false) String keyword) {
        try {
            List<Course> courses = courseService.searchCourses(keyword);
            return ApiResponse.success(courses);
        } catch (Exception e) {
            return ApiResponse.error("搜索课程失败: " + e.getMessage());
        }
    }

    /**
     * 更新课程
     */
    @Operation(summary = "更新课程", description = "完整更新指定ID的课程信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "更新成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "请求参数错误"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}")
    public ApiResponse<Course> updateCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer id,
                                          @Valid @RequestBody CourseRequest request) {
        try {
            Course course = request.toEntity();
            Course updatedCourse = courseService.updateCourse(id, course);
            return ApiResponse.success("课程更新成功", updatedCourse);
        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest(e.getMessage());
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("更新课程失败: " + e.getMessage());
        }
    }

    /**
     * 部分更新课程
     */
    @Operation(summary = "部分更新课程", description = "部分更新指定ID的课程信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "更新成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "请求参数错误"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PatchMapping("/{id}")
    public ApiResponse<Course> patchCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer id,
                                         @RequestBody CourseRequest request) {
        try {
            Course existingCourse = courseService.getCourseById(id);
            request.updateEntity(existingCourse);
            Course updatedCourse = courseService.updateCourse(id, existingCourse);
            return ApiResponse.success("课程更新成功", updatedCourse);
        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest(e.getMessage());
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("更新课程失败: " + e.getMessage());
        }
    }

    /**
     * 删除课程
     */
    @Operation(summary = "删除课程", description = "删除指定ID的课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "删除成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer id) {
        try {
            courseService.deleteCourse(id);
            return ApiResponse.<Void>success("课程删除成功", null);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("删除课程失败: " + e.getMessage());
        }
    }

    /**
     * 发布课程
     */
    @Operation(summary = "发布课程", description = "发布指定ID的课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "发布成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/{id}/publish")
    public ApiResponse<Course> publishCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer id) {
        try {
            Course course = courseService.publishCourse(id);
            return ApiResponse.success("课程发布成功", course);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("发布课程失败: " + e.getMessage());
        }
    }

    /**
     * 归档课程
     */
    @Operation(summary = "归档课程", description = "归档指定ID的课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "归档成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/{id}/archive")
    public ApiResponse<Course> archiveCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer id) {
        try {
            Course course = courseService.archiveCourse(id);
            return ApiResponse.success("课程归档成功", course);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("归档课程失败: " + e.getMessage());
        }
    }

    /**
     * 检查课程代码是否可用
     */
    @Operation(summary = "检查课程代码是否可用", description = "检查指定课程代码是否可用")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "检查成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/check-code")
    public ApiResponse<Boolean> checkCourseCode(@Parameter(description = "课程代码", required = true) @RequestParam String code,
                                              @Parameter(description = "排除的课程ID", required = false) @RequestParam(required = false) Integer excludeId) {
        try {
            boolean available = courseService.isCourseCodeAvailable(code, excludeId);
            return ApiResponse.success(available);
        } catch (Exception e) {
            return ApiResponse.error("检查课程代码失败: " + e.getMessage());
        }
    }
}
