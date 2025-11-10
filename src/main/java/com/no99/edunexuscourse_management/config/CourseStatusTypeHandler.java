package com.no99.edunexuscourse_management.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.no99.edunexuscourse_management.entity.Course.CourseStatus;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * CourseStatus枚举类型处理器
 */
public class CourseStatusTypeHandler extends BaseTypeHandler<CourseStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, CourseStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public CourseStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : CourseStatus.fromValue(value);
    }

    @Override
    public CourseStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : CourseStatus.fromValue(value);
    }

    @Override
    public CourseStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : CourseStatus.fromValue(value);
    }
}
