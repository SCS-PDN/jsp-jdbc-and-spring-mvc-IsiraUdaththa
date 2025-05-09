package university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import university.model.Course;
import university.model.Registration;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class RegistrationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseDAO courseDAO;

    // Get all registrations
    public List<Registration> getAllRegistrations() {
        String sql = "SELECT * FROM registrations";
        return jdbcTemplate.query(sql, new RegistrationRowMapper());
    }

    // Get registrations by student ID
    public List<Registration> getRegistrationsByStudentId(int studentId) {
        String sql = "SELECT * FROM registrations WHERE student_id = ?";
        return jdbcTemplate.query(sql, new RegistrationRowMapper(), studentId);
    }

    // Get courses registered by student
    public List<Course> getCoursesByStudentId(int studentId) {
        String sql = "SELECT c.* FROM courses c INNER JOIN registrations r ON c.course_id = r.course_id WHERE r.student_id = ?";
        return jdbcTemplate.query(sql, new CourseDAO.CourseRowMapper(), studentId);
    }

    // Check if a student is already registered for a course
    public boolean isAlreadyRegistered(int studentId, int courseId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, studentId, courseId);
        return count > 0;
    }

    // Register a student for a course
    public boolean addRegistration(int studentId, int courseId) {
        // Check if already registered
        if (isAlreadyRegistered(studentId, courseId)) {
            return false;
        }

        String sql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, CURDATE())";
        int result = jdbcTemplate.update(sql, studentId, courseId);
        return result > 0;
    }

    // Unregister a student from a course
    public boolean deleteRegistration(int studentId, int courseId) {
        String sql = "DELETE FROM registrations WHERE student_id = ? AND course_id = ?";
        int result = jdbcTemplate.update(sql, studentId, courseId);
        return result > 0;
    }

    // RowMapper for Registration
    private static final class RegistrationRowMapper implements RowMapper<Registration> {
        @Override
        public Registration mapRow(ResultSet rs, int rowNum) throws SQLException {
            Registration registration = new Registration();
            registration.setRegId(rs.getInt("reg_id"));
            registration.setStudentId(rs.getInt("student_id"));
            registration.setCourseId(rs.getInt("course_id"));
            registration.setDate(rs.getDate("date"));
            return registration;
        }
    }
}