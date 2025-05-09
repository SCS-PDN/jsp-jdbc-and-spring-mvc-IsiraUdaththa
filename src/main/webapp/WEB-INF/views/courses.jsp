<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registration Success - University Course Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 600px;
            margin: 100px auto;
            padding: 20px;
            background-color: white;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        h2 {
            color: #333;
        }
        .success-message {
            color: #4CAF50;
            font-size: 18px;
            margin: 20px 0;
        }
        .course-details {
            background-color: #f9f9f9;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            text-align: left;
        }
        .details-row {
            margin: 10px 0;
        }
        .details-label {
            font-weight: bold;
            display: inline-block;
            width: 100px;
        }
        .btn {
            padding: 10px 15px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 3px;
            display: inline-block;
            margin-top: 20px;
        }
        .btn:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>Registration Successful!</h2>

    <div class="success-message">
        You have successfully registered for the course.
    </div>

    <div class="course-details">
        <div class="details-row">
            <span class="details-label">Course:</span>
            <span>${course.name}</span>
        </div>
        <div class="details-row">
            <span class="details-label">Instructor:</span>
            <span>${course.instructor}</span>
        </div>
        <div class="details-row">
            <span class="details-label">Credits:</span>
            <span>${course.credits}</span>
        </div>
    </div>

    <a href="<c:url value='/courses'/>" class="btn">View All Courses</a>
</div>
</body>
</html>