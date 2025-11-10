# EduNexus 课程管理微服务

这是一个类似Canvas系统的课程管理微服务，提供完整的课程增删改查功能。

## 技术栈

- **Java 17**
- **Spring Boot 3.5.7**
- **MyBatis 3.0.5**
- **MySQL 8.0**
- **Maven**

## 功能特性

### 课程管理 (Course Management)
- ✅ 创建课程
- ✅ 查询课程（按ID、教师、公开课程等）
- ✅ 更新课程
- ✅ 删除课程
- ✅ 发布/归档课程
- ✅ 搜索课程
- ✅ 课程代码唯一性验证

### 课程注册 (Course Enrollment)
- ✅ 学生注册课程
- ✅ 退课
- ✅ 完成课程
- ✅ 查询用户课程列表
- ✅ 查询课程学生列表
- ✅ 注册状态检查

### 课程模块 (Course Module)
- ✅ 创建模块
- ✅ 查询模块
- ✅ 更新模块
- ✅ 删除模块
- ✅ 发布/取消发布模块
- ✅ 模块排序

## 数据库表结构

### courses 表
- `id` - 主键
- `title` - 课程标题
- `code` - 课程代码（唯一）
- `description` - 课程描述
- `instructor_id` - 教师ID
- `credits` - 学分
- `max_students` - 最大学生数
- `is_public` - 是否公开
- `status` - 状态（draft/published/archived）
- `start_date` - 开始日期
- `end_date` - 结束日期

### enrollments 表
- `id` - 主键
- `user_id` - 用户ID
- `course_id` - 课程ID
- `role` - 角色（student/ta）
- `enrollment_status` - 注册状态（active/dropped/completed）
- `enrolled_at` - 注册时间
- `completed_at` - 完成时间

### modules 表
- `id` - 主键
- `course_id` - 课程ID
- `title` - 模块标题
- `description` - 模块描述
- `order_index` - 排序序号
- `is_published` - 是否发布
- `published_at` - 发布时间

## API 接口

### 课程接口

#### 创建课程
```http
POST /api/courses
Content-Type: application/json

{
  "title": "Java编程基础",
  "code": "CS101",
  "description": "Java编程入门课程",
  "instructorId": 1,
  "credits": 3,
  "maxStudents": 50,
  "isPublic": true,
  "status": "draft",
  "startDate": "2024-01-15",
  "endDate": "2024-05-15"
}
```

#### 获取所有课程
```http
GET /api/courses
```

#### 根据ID获取课程
```http
GET /api/courses/{id}
```

#### 获取教师课程
```http
GET /api/courses/instructor/{instructorId}
```

#### 获取公开课程
```http
GET /api/courses/public
```

#### 搜索课程
```http
GET /api/courses/search?keyword=Java
```

#### 更新课程
```http
PUT /api/courses/{id}
Content-Type: application/json

{
  "title": "Java编程进阶",
  "code": "CS102",
  "description": "Java编程进阶课程",
  "instructorId": 1,
  "credits": 4,
  "maxStudents": 30,
  "isPublic": true,
  "status": "published",
  "startDate": "2024-02-15",
  "endDate": "2024-06-15"
}
```

#### 部分更新课程
```http
PATCH /api/courses/{id}
Content-Type: application/json

{
  "title": "Java编程高级"
}
```

#### 删除课程
```http
DELETE /api/courses/{id}
```

#### 发布课程
```http
POST /api/courses/{id}/publish
```

#### 归档课程
```http
POST /api/courses/{id}/archive
```

#### 检查课程代码
```http
GET /api/courses/check-code?code=CS101&excludeId=1
```

### 课程注册接口

#### 注册课程
```http
POST /api/enrollments?userId=1&courseId=1
```

#### 退课
```http
POST /api/enrollments/drop?userId=1&courseId=1
```

#### 完成课程
```http
POST /api/enrollments/complete?userId=1&courseId=1
```

#### 获取用户课程
```http
GET /api/enrollments/user/{userId}
```

#### 获取课程学生
```http
GET /api/enrollments/course/{courseId}
```

#### 检查注册状态
```http
GET /api/enrollments/check?userId=1&courseId=1
```

#### 获取课程注册人数
```http
GET /api/enrollments/course/{courseId}/count
```

### 课程模块接口

#### 创建模块
```http
POST /api/modules
Content-Type: application/json

{
  "courseId": 1,
  "title": "第一章：Java基础语法",
  "description": "介绍Java基础语法和概念",
  "orderIndex": 1,
  "isPublished": false
}
```

#### 获取课程模块
```http
GET /api/modules/course/{courseId}
```

#### 获取已发布模块
```http
GET /api/modules/course/{courseId}/published
```

#### 更新模块
```http
PUT /api/modules/{id}
Content-Type: application/json

{
  "courseId": 1,
  "title": "第一章：Java基础语法（修订版）",
  "description": "介绍Java基础语法和概念",
  "orderIndex": 1,
  "isPublished": true
}
```

#### 发布模块
```http
POST /api/modules/{id}/publish
```

#### 取消发布模块
```http
POST /api/modules/{id}/unpublish
```

#### 重新排序模块
```http
POST /api/modules/reorder
Content-Type: application/json

[1, 3, 2, 4]
```

## 运行项目

### 1. 配置数据库
确保MySQL数据库运行，并且存在`edunexus`数据库。

### 2. 修改配置
编辑 `src/main/resources/application.properties`：
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/edunexus?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
spring.datasource.username=root
spring.datasource.password=your_password
```

### 3. 运行项目
```bash
mvn spring-boot:run
```

### 4. 健康检查
访问：http://localhost:8080/api/health

## 响应格式

所有API响应都采用统一格式：

### 成功响应
```json
{
  "success": true,
  "message": "操作成功",
  "data": {...},
  "code": 200
}
```

### 错误响应
```json
{
  "success": false,
  "message": "错误信息",
  "data": null,
  "code": 400
}
```

## 错误码说明

- `200` - 成功
- `400` - 请求参数错误
- `403` - 权限不足
- `404` - 资源不存在
- `500` - 服务器内部错误

## 注意事项

1. 课程代码必须唯一
2. 删除课程会级联删除相关的注册记录和模块
3. 只有已发布的课程才能被学生注册
4. 模块排序从1开始
5. 所有时间字段使用ISO 8601格式

## 扩展功能

可以进一步扩展的功能：
- 用户认证和授权
- 文件上传和管理
- 作业和考试管理
- 成绩管理
- 通知系统
- 讨论区功能
