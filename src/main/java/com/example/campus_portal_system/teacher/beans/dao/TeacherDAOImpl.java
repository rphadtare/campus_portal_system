package com.example.campus_portal_system.teacher.beans.dao;

import com.example.campus_portal_system.teacher.beans.Teacher;
import com.example.campus_portal_system.teacher.beans.mapper.TeacherMapper;
import com.example.campus_portal_system.utility.beans.Admin;
import com.example.campus_portal_system.utility.beans.UserTypes;
import com.example.campus_portal_system.utility.beans.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.logging.Logger;

@Component
public class TeacherDAOImpl implements TeacherDAO {

    JdbcTemplate jdbcTemplate;

    private Logger logger;

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

    public final String SQL_CHECK_IF_HOD_EXIST = "select * from teacher where institute_id = ? and department_id = ?" +
            "and teacherTypeId in (" + UserTypes.HEAD_OF_DEPARTMENT.getNumVal() + "," +
            UserTypes.HEAD_OF_DEPARTMENT_AND_CLASS_TEACHER.getNumVal() + ") limit 1";

    public final String SQL_GET_HOD = "select * from teacher where institute_id = ? and department_id = ?" +
            "and teacherTypeId in (" + UserTypes.HEAD_OF_DEPARTMENT.getNumVal() + "," +
            UserTypes.HEAD_OF_DEPARTMENT_AND_CLASS_TEACHER.getNumVal() + ")";

    @Autowired
    public TeacherDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        logger = Logger.getLogger(TeacherDAOImpl.class.getName());
    }

    @Override
    public List<Teacher> getAllTeachers() {
        logger.info("Inside getAllTeachers ...");
        return jdbcTemplate.query(SQL_GET_ALL_TEACHERS, new TeacherMapper());
    }

    @Override
    public List<Teacher> getAllTeachers(int institute_id) {
        logger.info("Inside getAllTeachers of institute : " + institute_id);
        return jdbcTemplate.query(SQL_GET_ALL_TEACHERS_OF_INSTITUTE, new Object[] {institute_id}, new TeacherMapper());
    }

    @Override
    public List<Teacher> getAllHOD(int institute_id) {
        logger.info("Inside getAllHOD of institute : " + institute_id);
        return jdbcTemplate.query(SQL_GET_TEACHER_BY_TYPE_FROM_INSTITUTE, new Object[] {institute_id, "2,4"}, new TeacherMapper());
    }

    @Override
    public List<Teacher> getAllClassTeachers(int institute_id) {
        logger.info("Inside getAllClassTeachers of institute : " + institute_id);
        return jdbcTemplate.query(SQL_GET_TEACHER_BY_TYPE_FROM_INSTITUTE, new Object[] {institute_id, "3,4"}, new TeacherMapper());
    }

    @Override
    public Teacher getTeacherById(int institute_id, int teacher_id) {
        logger.info("Inside getTeacherById ..");
        return jdbcTemplate.queryForObject(SQL_GET_TEACHER_BY_ID, new Object[] {institute_id, teacher_id},
                new TeacherMapper());
    }

    @Override
    public Boolean updateTeacherInfo(Teacher teacher) {
        logger.info("Inside updateTeacherInfo ...");
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
        logger.info("Inside deleteTeacher ...");
        return jdbcTemplate.update(
                SQL_DELETE_TEACHER_BY_ID,
                teacher.getInstituteId(),
                teacher.getTeacherId()
        ) > 0;
    }

    @Override
    public Boolean deleteTeacherById(int institute_id, int teacher_id) {
        logger.info("Inside deleteTeacherById ...");
        return jdbcTemplate.update(
                SQL_DELETE_TEACHER_BY_ID,
                institute_id,
                teacher_id
        ) > 0;
    }

    @Override
    public Boolean createTeacher(Teacher teacher) {
        logger.info("Inside createTeacher ...");
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
        logger.info("Inside activateTeacher ...");
        return jdbcTemplate.update(SQL_ACTIVATE_TEACHER, institute_id, teacher_id) > 0;
    }

    @Override
    public Boolean checkIfHODExist(int institute_id, int department_id) {
        logger.info("Inside checkIfHODExist for institute id : " + institute_id + " and department id " + department_id);
        Teacher teacher = new Teacher(-1);

        try{
            teacher = jdbcTemplate.queryForObject(SQL_CHECK_IF_HOD_EXIST,
                    new Object[]{institute_id, department_id}, new TeacherMapper());

        } catch (Exception e) {
            logger.severe("Inside checkIfHODExist for institute id : " + institute_id + " and department id " + department_id +
                    "exception occurred - " + e.getMessage());
        }

        if(teacher.getTeacherId() == -1){
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Teacher getHOD(int institute_id, int department_id) {
        logger.info("Inside getHOD for institute id : " + institute_id + " and department id " + department_id);
        Teacher teacher = new Teacher(-1);

        try{
            teacher = jdbcTemplate.queryForObject(SQL_GET_HOD,
                    new Object[]{institute_id, department_id}, new TeacherMapper());

        } catch (Exception e) {
            logger.severe("Inside getHOD for institute id : " + institute_id + " and department id " + department_id +
                    "exception occurred - " + e.getMessage());
        }

       return teacher;
    }
}
