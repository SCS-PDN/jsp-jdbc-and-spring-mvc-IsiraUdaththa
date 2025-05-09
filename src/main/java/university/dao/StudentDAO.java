package university.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import university.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Get all students
    public List<Student> getAllStudents() {
        String sql = "SELECT * FROM students";
        return jdbcTemplate.query(sql, new StudentRowMapper());
    }

    // Get student by ID
    public Student getStudentById(int studentId) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), studentId);
        } catch (Exception e) {
            return null;
        }
    }

    // Get student by email
    public Student getStudentByEmail(String email) {
        String sql = "SELECT * FROM students WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), email);
        } catch (Exception e) {
            return null;
        }
    }

    // Validate login credentials
    public Student validateStudent(String email, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new StudentRowMapper(), email, password);
        } catch (Exception e) {
            return null;
        }
    }

    // Add new student
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, email, password) VALUES (?, ?, ?)";
        int result = jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getPassword());
        return result > 0;
    }

    // Update student
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name = ?, email = ?, password = ? WHERE student_id = ?";
        int result = jdbcTemplate.update(sql, student.getName(), student.getEmail(), student.getPassword(), student.getStudentId());
        return result > 0;
    }

    // Delete student
    public boolean deleteStudent(int studentId) {
        String sql = "DELETE FROM students WHERE student_id = ?";
        int result = jdbcTemplate.update(sql, studentId);
        return result > 0;
    }

    // RowMapper for Student
    private static final class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setStudentId(rs.getInt("student_id"));
            student.setName(rs.getString("name"));
            student.setEmail(rs.getString("email"));
            student.setPassword(rs.getString("password"));
            return student;
        }
    }
}