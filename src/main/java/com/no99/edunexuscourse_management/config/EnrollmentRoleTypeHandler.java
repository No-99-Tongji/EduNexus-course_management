package com.no99.edunexuscourse_management.config;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.no99.edunexuscourse_management.entity.Enrollment.EnrollmentRole;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * EnrollmentRole枚举类型处理器
 */
public class EnrollmentRoleTypeHandler extends BaseTypeHandler<EnrollmentRole> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, EnrollmentRole parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public EnrollmentRole getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : EnrollmentRole.fromValue(value);
    }

    @Override
    public EnrollmentRole getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : EnrollmentRole.fromValue(value);
    }

    @Override
    public EnrollmentRole getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : EnrollmentRole.fromValue(value);
    }
}
