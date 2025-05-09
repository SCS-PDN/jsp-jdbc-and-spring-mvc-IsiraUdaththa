package university.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import university.model.Student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.logging.Logger;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger LOGGER = Logger.getLogger(LoggingInterceptor.class.getName());


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        // Log all requests
        LOGGER.info("Request URI: " + request.getRequestURI());

        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession(false);

        // Log login attempts
        if (requestURI.equals("/login") && request.getMethod().equals("POST")) {
            Student student = session != null ? (Student) session.getAttribute("currentStudent") : null;

            if (student != null) {
                // Login successful
                LOGGER.info("Login successful for: " + student.getEmail());
            } else {
                // Login failed
                String email = request.getParameter("email");
                LOGGER.warning("Login failed for: " + email);
            }
        }

        // Log course registrations
        if (requestURI.startsWith("/register/") && request.getMethod().equals("POST")) {
            Student student = session != null ? (Student) session.getAttribute("currentStudent") : null;

            if (student != null) {
                // Extract course ID from URI
                String courseId = requestURI.substring(requestURI.lastIndexOf("/") + 1);
                LOGGER.info("Course registration: Student ID=" + student.getStudentId() +
                        ", Name=" + student.getName() +
                        ", Course ID=" + courseId);
            }
        }
    }


    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // No specific logging needed after completion
    }
}