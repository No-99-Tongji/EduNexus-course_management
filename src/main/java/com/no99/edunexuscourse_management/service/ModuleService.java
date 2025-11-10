package com.no99.edunexuscourse_management.service;

import com.no99.edunexuscourse_management.entity.Module;
import com.no99.edunexuscourse_management.mapper.ModuleMapper;
import com.no99.edunexuscourse_management.mapper.CourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 课程模块服务实现类
 */
@Service
@Transactional
public class ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private CourseMapper courseMapper;

    /**
     * 创建模块
     */
    public Module createModule(Module module) {
        // 检查课程是否存在
        if (courseMapper.findById(module.getCourseId()) == null) {
            throw new RuntimeException("课程不存在: " + module.getCourseId());
        }

        // 设置排序序号
        if (module.getOrderIndex() == null || module.getOrderIndex() <= 0) {
            module.setOrderIndex(moduleMapper.getNextOrderIndex(module.getCourseId()));
        }

        moduleMapper.insert(module);
        return moduleMapper.findById(module.getId());
    }

    /**
     * 根据ID获取模块
     */
    @Transactional(readOnly = true)
    public Module getModuleById(Integer id) {
        Module module = moduleMapper.findById(id);
        if (module == null) {
            throw new RuntimeException("模块不存在: " + id);
        }
        return module;
    }

    /**
     * 获取课程的所有模块
     */
    @Transactional(readOnly = true)
    public List<Module> getModulesByCourse(Integer courseId) {
        return moduleMapper.findByCourseId(courseId);
    }

    /**
     * 获取课程的已发布模块
     */
    @Transactional(readOnly = true)
    public List<Module> getPublishedModulesByCourse(Integer courseId) {
        return moduleMapper.findPublishedByCourseId(courseId);
    }

    /**
     * 更新模块
     */
    public Module updateModule(Integer id, Module module) {
        // 检查模块是否存在
        Module existingModule = getModuleById(id);

        module.setId(id);
        module.setCourseId(existingModule.getCourseId()); // 不允许修改课程ID

        moduleMapper.update(module);
        return moduleMapper.findById(id);
    }

    /**
     * 删除模块
     */
    public void deleteModule(Integer id) {
        getModuleById(id); // 检查是否存在
        moduleMapper.deleteById(id);
    }

    /**
     * 发布模块
     */
    public Module publishModule(Integer id) {
        Module module = getModuleById(id);
        module.setIsPublished(true);
        module.setPublishedAt(LocalDateTime.now());
        moduleMapper.updatePublishStatus(module);
        return moduleMapper.findById(id);
    }

    /**
     * 取消发布模块
     */
    public Module unpublishModule(Integer id) {
        Module module = getModuleById(id);
        module.setIsPublished(false);
        module.setPublishedAt(null);
        moduleMapper.updatePublishStatus(module);
        return moduleMapper.findById(id);
    }

    /**
     * 批量更新模块排序
     */
    public void updateModuleOrder(List<Integer> moduleIds) {
        for (int i = 0; i < moduleIds.size(); i++) {
            moduleMapper.updateOrderIndex(moduleIds.get(i), i + 1);
        }
    }

    /**
     * 根据课程ID删除所有模块
     */
    public void deleteModulesByCourse(Integer courseId) {
        moduleMapper.deleteByCourseId(courseId);
    }
}
