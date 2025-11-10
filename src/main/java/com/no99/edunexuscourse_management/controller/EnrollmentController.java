package com.no99.edunexuscourse_management.controller;

import com.no99.edunexuscourse_management.dto.ApiResponse;
import com.no99.edunexuscourse_management.entity.Enrollment;
import com.no99.edunexuscourse_management.service.EnrollmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程注册控制器
 */
@RestController
@RequestMapping("/api/enrollments")
@CrossOrigin(origins = "*")
@Tag(name = "课程注册管理", description = "课程注册相关的API接口")
public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    /**
     * 注册课程
     */
    @Operation(summary = "注册课程", description = "用户注册指定课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "注册成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "请求参数错误"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "用户或课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping
    public ApiResponse<Enrollment> enrollCourse(@Parameter(description = "用户ID", required = true) @RequestParam Integer userId,
                                              @Parameter(description = "课程ID", required = true) @RequestParam Integer courseId) {
        try {
            Enrollment enrollment = enrollmentService.enrollCourse(userId, courseId);
            return ApiResponse.success("课程注册成功", enrollment);
        } catch (IllegalArgumentException e) {
            return ApiResponse.badRequest(e.getMessage());
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("注册课程失败: " + e.getMessage());
        }
    }

    /**
     * 退课
     */
    @Operation(summary = "退课", description = "用户退出指定课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "退课成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "注册记录不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/drop")
    public ApiResponse<Void> dropCourse(@Parameter(description = "用户ID", required = true) @RequestParam Integer userId,
                                       @Parameter(description = "课程ID", required = true) @RequestParam Integer courseId) {
        try {
            enrollmentService.dropCourse(userId, courseId);
            return ApiResponse.<Void>success("退课成功", null);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("退课失败: " + e.getMessage());
        }
    }

    /**
     * 完成课程
     */
    @Operation(summary = "完成课程", description = "标记用户完成指定课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "课程完成"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "注册记录不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping("/complete")
    public ApiResponse<Void> completeCourse(@Parameter(description = "用户ID", required = true) @RequestParam Integer userId,
                                          @Parameter(description = "课程ID", required = true) @RequestParam Integer courseId) {
        try {
            enrollmentService.completeCourse(userId, courseId);
            return ApiResponse.<Void>success("课程完成", null);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("完成课程失败: " + e.getMessage());
        }
    }

    /**
     * 获取用户的课程注册列表
     */
    @Operation(summary = "获取用户的课程注册列表", description = "获取指定用户的所有课程注册记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Enrollment>> getUserEnrollments(@Parameter(description = "用户ID", required = true) @PathVariable Integer userId) {
        try {
            List<Enrollment> enrollments = enrollmentService.getUserEnrollments(userId);
            return ApiResponse.success(enrollments);
        } catch (Exception e) {
            return ApiResponse.error("获取用户课程失败: " + e.getMessage());
        }
    }

    /**
     * 获取课程的学生列表
     */
    @Operation(summary = "获取课程的学生列表", description = "获取指定课程的所有注册学生")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/course/{courseId}")
    public ApiResponse<List<Enrollment>> getCourseEnrollments(@Parameter(description = "课程ID", required = true) @PathVariable Integer courseId) {
        try {
            List<Enrollment> enrollments = enrollmentService.getCourseEnrollments(courseId);
            return ApiResponse.success(enrollments);
        } catch (Exception e) {
            return ApiResponse.error("获取课程学生失败: " + e.getMessage());
        }
    }

    /**
     * 检查用户是否已注册课程
     */
    @Operation(summary = "检查用户是否已注册课程", description = "检查指定用户是否已注册指定课程")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "检查成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/check")
    public ApiResponse<Boolean> checkEnrollment(@Parameter(description = "用户ID", required = true) @RequestParam Integer userId,
                                              @Parameter(description = "课程ID", required = true) @RequestParam Integer courseId) {
        try {
            boolean enrolled = enrollmentService.isUserEnrolled(userId, courseId);
            return ApiResponse.success(enrolled);
        } catch (Exception e) {
            return ApiResponse.error("检查注册状态失败: " + e.getMessage());
        }
    }

    /**
     * 获取课程注册人数
     */
    @Operation(summary = "获取课程注册人数", description = "获取指定课程的注册学生数量")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/course/{courseId}/count")
    public ApiResponse<Integer> getCourseEnrollmentCount(@Parameter(description = "课程ID", required = true) @PathVariable Integer courseId) {
        try {
            int count = enrollmentService.getCourseEnrollmentCount(courseId);
            return ApiResponse.success(count);
        } catch (Exception e) {
            return ApiResponse.error("获取课程注册人数失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取注册记录
     */
    @Operation(summary = "根据ID获取注册记录", description = "通过注册记录ID获取详细信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "注册记录不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{id}")
    public ApiResponse<Enrollment> getEnrollment(@Parameter(description = "注册记录ID", required = true) @PathVariable Integer id) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentById(id);
            return ApiResponse.success(enrollment);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("获取注册记录失败: " + e.getMessage());
        }
    }

    /**
     * 删除注册记录
     */
    @Operation(summary = "删除注册记录", description = "删除指定ID的注册记录")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "删除成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "注册记录不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteEnrollment(@Parameter(description = "注册记录ID", required = true) @PathVariable Integer id) {
        try {
            enrollmentService.deleteEnrollment(id);
            return ApiResponse.<Void>success("注册记录删除成功", null);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("删除注册记录失败: " + e.getMessage());
        }
    }
}
