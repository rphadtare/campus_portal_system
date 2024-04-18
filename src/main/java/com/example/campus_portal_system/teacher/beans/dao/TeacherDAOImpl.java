package com.example.campus_portal_system.teacher.beans.dao;

import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

public class TeacherDAOImpl implements TeacherDAO {

    JdbcTemplate jdbcTemplate;

    private final String SQL_GET_ALL_TEACHERS = "select * from teacher";

    private final String SQL_GET_ALL_TEACHERS_OF_INSTITUTE = "select * from teacher where institute_id = ?";

    private final String SQL_GET_TEACHER_BY_TYPE_FROM_INSTITUTE = "select * from teacher where institute_id = ? and teacher_type_id in (?)";

    private final String SQL_GET_TEACHER_BY_ID = "select * from teacher where institute_id = ? and teacher_id = ?";

    private final String SQL_UPDATE_TEACHER_BASIC_INFO = "update teacher" +
            "set" +
            "salutations = ?," +
            "first_name = ?, middle_name = ?, last_name=? " +
            "qualifications = ?, email_id = ?, contact_no = ?" +
            "where institute_id = ? and teacher_id = ?";

    private final String SQL_DELETE_TEACHER_BY_ID = "update teacher" +
            "set is_deleted = 1" +
            "where institute_id = ? and teacher_id = ?";

    private final String SQL_INSERT_TEACHER_INFO = "insert into teacher(institute_id, department_id, " +
            "teacher_type_id, salutations, " +
            "first_name, middle_name, last_name," +
            "qualifications, email_id, contact_no," +
            "is_deleted)" +
            "values(?,?,?,?,?,?,?,?,?,?,1)";

    public final String SQL_ACTIVATE_TEACHER = "update teacher set is_deleted = 0 where institute_id = ? and teacher_id = ?";

    @Autowired
    public TeacherDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<Teacher> getAllTeachers() {
        System.out.println("[TeacherDAOImpl]: Inside getAllTeachers ...");
        return jdbcTemplate.query(SQL_GET_ALL_TEACHERS, new TeacherMapper());
    }

    @Override
    public List<Teacher> getAllTeachers(int institute_id) {
        System.out.println("[TeacherDAOImpl]: Inside getAllTeachers of institute : " + institute_id);
        return jdbcTemplate.query(SQL_GET_ALL_TEACHERS_OF_INSTITUTE, new Object[] {institute_id}, new TeacherMapper());
    }

    @Override
    public List<Teacher> getAllHOD(int institute_id) {
        System.out.println("[TeacherDAOImpl]: Inside getAllHOD of institute : " + institute_id);
        return jdbcTemplate.query(SQL_GET_TEACHER_BY_TYPE_FROM_INSTITUTE, new Object[] {institute_id, "2,4"}, new TeacherMapper());
    }

    @Override
    public List<Teacher> getAllClassTeachers(int institute_id) {
        System.out.println("[TeacherDAOImpl]: Inside getAllClassTeachers of institute : " + institute_id);
        return jdbcTemplate.query(SQL_GET_TEACHER_BY_TYPE_FROM_INSTITUTE, new Object[] {institute_id, "3,4"}, new TeacherMapper());
    }

    @Override
    public Teacher getTeacherById(int institute_id, int teacher_id) {
        System.out.println("[TeacherDAOImpl]: Inside getTeacherById ..");
        return jdbcTemplate.queryForObject(SQL_GET_TEACHER_BY_ID, new Object[] {institute_id, teacher_id},
                new TeacherMapper());
    }

    @Override
    public Boolean updateTeacherInfo(Teacher teacher) {
        System.out.println("[TeacherDAOImpl]: Inside updateTeacherInfo ...");
        return jdbcTemplate.update(
                SQL_UPDATE_TEACHER_BASIC_INFO,
                teacher.getSalutations(),
                teacher.getFirstName(),
                teacher.getMiddleName(),
                teacher.getLastName(),
                teacher.getQualifications(),
                teacher.getEmailId(),
                teacher.getContactNo(),
                teacher.getInstituteId(),
                teacher.getTeacherId()
        ) > 0;
    }

    @Override
    public Boolean deleteTeacher(Teacher teacher) {
        System.out.println("[TeacherDAOImpl]: Inside deleteTeacher ...");
        return jdbcTemplate.update(
                SQL_DELETE_TEACHER_BY_ID,
                teacher.getInstituteId(),
                teacher.getTeacherId()
        ) > 0;
    }

    @Override
    public Boolean deleteTeacherById(int institute_id, int teacher_id) {
        System.out.println("[TeacherDAOImpl]: Inside deleteTeacherById ...");
        return jdbcTemplate.update(
                SQL_DELETE_TEACHER_BY_ID,
                institute_id,
                teacher_id
        ) > 0;
    }

    @Override
    public Boolean createTeacher(Teacher teacher) {
        System.out.println("[TeacherDAOImpl]: Inside createTeacher ...");
        return jdbcTemplate.update(
                SQL_INSERT_TEACHER_INFO,
                teacher.getInstituteId(),
                teacher.getDepartmentId(),
                teacher.getTeacherTypeId(),
                teacher.getSalutations(),
                teacher.getFirstName(),
                teacher.getMiddleName(),
                teacher.getLastName(),
                teacher.getQualifications(),
                teacher.getEmailId(),
                teacher.getContactNo()
        ) > 0;
    }

    @Override
    public Boolean activateTeacher(Teacher teacher) {
        return activateTeacher(teacher.getInstituteId(), teacher.getTeacherId());
    }

    @Override
    public Boolean activateTeacher(int institute_id, int teacher_id) {
        System.out.println("[TeacherDAOImpl]: Inside activateTeacher ...");
        return jdbcTemplate.update(SQL_ACTIVATE_TEACHER, institute_id, teacher_id) > 0;
    }
}
