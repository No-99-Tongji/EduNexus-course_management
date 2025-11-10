package com.no99.edunexuscourse_management.controller;

import com.no99.edunexuscourse_management.dto.ApiResponse;
import com.no99.edunexuscourse_management.entity.Module;
import com.no99.edunexuscourse_management.service.ModuleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程模块控制器
 */
@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "*")
@Tag(name = "课程模块管理", description = "课程模块相关的API接口")
public class ModuleController {

    @Autowired
    private ModuleService moduleService;

    /**
     * 创建模块
     */
    @Operation(summary = "创建模块", description = "为课程创建一个新的模块")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "模块创建成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "课程不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PostMapping
    public ApiResponse<Module> createModule(@Valid @RequestBody Module module) {
        try {
            Module createdModule = moduleService.createModule(module);
            return ApiResponse.success("模块创建成功", createdModule);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("创建模块失败: " + e.getMessage());
        }
    }

    /**
     * 根据ID获取模块
     */
    @Operation(summary = "根据ID获取模块", description = "通过模块ID获取模块详细信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "模块不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/{id}")
    public ApiResponse<Module> getModule(@Parameter(description = "模块ID", required = true) @PathVariable Integer id) {
        try {
            Module module = moduleService.getModuleById(id);
            return ApiResponse.success(module);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("获取模块失败: " + e.getMessage());
        }
    }

    /**
     * 获取课程的所有模块
     */
    @Operation(summary = "获取课程的所有模块", description = "获取指定课程的所有模块列表")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/course/{courseId}")
    public ApiResponse<List<Module>> getModulesByCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer courseId) {
        try {
            List<Module> modules = moduleService.getModulesByCourse(courseId);
            return ApiResponse.success(modules);
        } catch (Exception e) {
            return ApiResponse.error("获取课程模块失败: " + e.getMessage());
        }
    }

    /**
     * 获取课程的已发布模块
     */
    @Operation(summary = "获取课程的已发布模块", description = "获取指定课程的所有已发布模块")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "获取成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @GetMapping("/course/{courseId}/published")
    public ApiResponse<List<Module>> getPublishedModulesByCourse(@Parameter(description = "课程ID", required = true) @PathVariable Integer courseId) {
        try {
            List<Module> modules = moduleService.getPublishedModulesByCourse(courseId);
            return ApiResponse.success(modules);
        } catch (Exception e) {
            return ApiResponse.error("获取已发布模块失败: " + e.getMessage());
        }
    }

    /**
     * 更新模块
     */
    @Operation(summary = "更新模块", description = "更新指定ID的模块信息")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "更新成功"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "模块不存在"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "服务器内部错误")
    })
    @PutMapping("/{id}")
    public ApiResponse<Module> updateModule(@Parameter(description = "模块ID", required = true) @PathVariable Integer id,
                                          @Valid @RequestBody Module module) {
        try {
            Module updatedModule = moduleService.updateModule(id, module);
            return ApiResponse.success("模块更新成功", updatedModule);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("更新模块失败: " + e.getMessage());
        }
    }

    /**
     * 删除模块
     */
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteModule(@PathVariable Integer id) {
        try {
            moduleService.deleteModule(id);
            return ApiResponse.<Void>success("模块删除成功", null);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("删除模块失败: " + e.getMessage());
        }
    }

    /**
     * 发布模块
     */
    @PostMapping("/{id}/publish")
    public ApiResponse<Module> publishModule(@PathVariable Integer id) {
        try {
            Module module = moduleService.publishModule(id);
            return ApiResponse.success("模块发布成功", module);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("发布模块失败: " + e.getMessage());
        }
    }

    /**
     * 取消发布模块
     */
    @PostMapping("/{id}/unpublish")
    public ApiResponse<Module> unpublishModule(@PathVariable Integer id) {
        try {
            Module module = moduleService.unpublishModule(id);
            return ApiResponse.success("模块取消发布成功", module);
        } catch (RuntimeException e) {
            return ApiResponse.notFound(e.getMessage());
        } catch (Exception e) {
            return ApiResponse.error("取消发布模块失败: " + e.getMessage());
        }
    }

    /**
     * 批量更新模块排序
     */
    @PostMapping("/reorder")
    public ApiResponse<Void> reorderModules(@RequestBody List<Integer> moduleIds) {
        try {
            moduleService.updateModuleOrder(moduleIds);
            return ApiResponse.<Void>success("模块排序更新成功", null);
        } catch (Exception e) {
            return ApiResponse.error("更新模块排序失败: " + e.getMessage());
        }
    }
}
