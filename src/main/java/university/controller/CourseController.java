package university.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import university.dao.CourseDAO;
import university.dao.RegistrationDAO;
import university.model.Course;
import university.model.Student;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CourseController {

    @Autowired
    private CourseDAO courseDAO;

    @Autowired
    private RegistrationDAO registrationDAO;

    // Show all courses
    @GetMapping("/courses")
    public String showCourses(HttpSession session, Model model) {
        // Check if user is logged in
        Student student = (Student) session.getAttribute("currentStudent");
        if (student == null) {
            return "redirect:/login";
        }

        // Get all courses
        List<Course> courses = courseDAO.getAllCourses();
        model.addAttribute("courses", courses);

        // Get courses the student is already registered for
        List<Course> registeredCourses = courseDAO.getCoursesForStudent(student.getStudentId());
        model.addAttribute("registeredCourses", registeredCourses);

        return "courses";
    }

    // Register for a course
    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable("courseId") int courseId,
                                 HttpSession session,
                                 Model model) {
        // Check if user is logged in
        Student student = (Student) session.getAttribute("currentStudent");
        if (student == null) {
            return "redirect:/login";
        }

        // Check if already registered
        if (registrationDAO.isAlreadyRegistered(student.getStudentId(), courseId)) {
            model.addAttribute("error", "You are already registered for this course");
            return "redirect:/courses";
        }

        // Register for the course
        boolean success = registrationDAO.addRegistration(student.getStudentId(), courseId);

        if (success) {
            Course course = courseDAO.getCourseById(courseId);
            model.addAttribute("course", course);
            return "success";
        } else {
            model.addAttribute("error", "Registration failed. Please try again.");
            return "redirect:/courses";
        }
    }
}