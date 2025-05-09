package university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import university.model.Course;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CourseDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Get all courses
    public List<Course> getAllCourses() {
        String sql = "SELECT * FROM courses";
        return jdbcTemplate.query(sql, new CourseRowMapper());
    }

    // Get course by ID
    public Course getCourseById(int courseId) {
        String sql = "SELECT * FROM courses WHERE course_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new CourseRowMapper(), courseId);
        } catch (Exception e) {
            return null;
        }
    }

    // Get courses registered by a student
    public List<Course> getCoursesForStudent(int studentId) {
        String sql = "SELECT c.* FROM courses c " + "JOIN registrations r ON c.course_id = r.course_id " + "WHERE r.student_id = ?";
        return jdbcTemplate.query(sql, new CourseRowMapper(), studentId);
    }

    // Add new course
    public boolean addCourse(Course course) {
        String sql = "INSERT INTO courses (name, instructor, credits) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, course.getName(), course.getInstructor(), course.getCredits());
        return result > 0;
    }

    // Update course
    public boolean updateCourse(Course course) {
        String sql = "UPDATE courses SET name = ?, instructor = ?, credits = ? WHERE course_id = ?";
        int result = jdbcTemplate.update(sql, course.getName(), course.getInstructor(), course.getCredits(), course.getCourseId());
        return result > 0;
    }

    // Delete course
    public boolean deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE course_id = ?";
        int result = jdbcTemplate.update(sql, courseId);
        return result > 0;
    }


    // RowMapper for Course
    static final class CourseRowMapper implements RowMapper<Course> {
        @Override
        public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
            Course course = new Course();
            course.setCourseId(rs.getInt("course_id"));
            course.setName(rs.getString("name"));
            course.setInstructor(rs.getString("instructor"));
            course.setCredits(rs.getInt("credits"));
            return course;
        }
    }
}