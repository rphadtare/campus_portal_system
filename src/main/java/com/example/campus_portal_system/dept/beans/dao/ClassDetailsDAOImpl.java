package com.example.campus_portal_system.dept.beans.dao;

import com.example.campus_portal_system.dept.beans.ClassDetails;
import com.example.campus_portal_system.dept.beans.mapper.ClassDetailsMapper;
import com.example.campus_portal_system.dept.beans.mapper.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.support.SqlLobValue;
import org.springframework.jdbc.support.lob.DefaultLobHandler;

import javax.sql.DataSource;
import java.io.ByteArrayInputStream;
import java.sql.Types;
import java.util.List;

public class ClassDetailsDAOImpl implements ClassDetailsDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassDetailsDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private final String SQL_GET_ALL_CLASSES_BY_INSTITUTE_AND_DEPT_ID = "select * from class_details_mapping" +
            "where institute_id = ? and department_id = ?";

    private final String SQL_UPDATE_TIMETABLE = "update class_details_mapping" +
            "set timetable = :timetable" +
            "where id = :id and institute_id = :institute_id and department_id = :department_id";

    private final String SQL_UPDATE_CLASS_TEACHER = "update class_details_mapping" +
            "set class_teacher_id = ?" +
            "where id = ? and institute_id = ? and department_id = ?";

    @Override
    public List<ClassDetails> getClassDetailsByInstituteAndDepartmentId(int instituteId, int departmentId) {
        System.out.println("[ClassDetailsDAOImpl]: Inside getClassDetailsByInstituteAndDepartmentId");
        return jdbcTemplate.query(SQL_GET_ALL_CLASSES_BY_INSTITUTE_AND_DEPT_ID,
                new Object[] {instituteId, departmentId},
                new ClassDetailsMapper());
    }

    @Override
    public Boolean setTimetable(int id, int instituteId, int departmentId, byte[] newTimeTable) {
        System.out.println("[ClassDetailsDAOImpl]: Inside setTimetable");
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("timetable",  new SqlLobValue(new ByteArrayInputStream(newTimeTable),
                newTimeTable.length, new DefaultLobHandler()), Types.BLOB);
        in.addValue("id",id);
        in.addValue("institute_id",instituteId);
        in.addValue("department_id",departmentId);

        return jdbcTemplate.update(SQL_UPDATE_TIMETABLE, in) > 0;

    }

    @Override
    public Boolean setClassTeacher(int instituteId, int departmentId, int classTeacherId) {
        System.out.println("[ClassDetailsDAOImpl]: Inside setClassTeacher");
        return jdbcTemplate.update(SQL_UPDATE_CLASS_TEACHER,
                new Object[]{classTeacherId, instituteId, departmentId}) > 0;

    }
}
