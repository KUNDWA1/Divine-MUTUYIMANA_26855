package com.example.demo.controller;
import com.example.demo.model.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // Constructor: add 5 sample students
    public StudentController() {
        students.add(new Student(1L, "Alice", "Johnson", "alice@mail.com", "Computer Science", 3.8));
        students.add(new Student(2L, "Bob", "Smith", "bob@mail.com", "Mathematics", 3.2));
        students.add(new Student(3L, "Charlie", "Brown", "charlie@mail.com", "Computer Science", 3.4));
        students.add(new Student(4L, "Diana", "Evans", "diana@mail.com", "Physics", 3.6));
        students.add(new Student(5L, "Ethan", "Clark", "ethan@mail.com", "Chemistry", 3.9));
    }

    // GET /api/students
    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(students); // 200
    }

    // GET /api/students/{studentId}
    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long studentId) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                return ResponseEntity.ok(s); // 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }

    // GET /api/students/major/{major}
    @GetMapping("/major/{major}")
    public ResponseEntity<List<Student>> getStudentsByMajor(@PathVariable String major) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getMajor().equalsIgnoreCase(major)) {
                result.add(s);
            }
        }
        return ResponseEntity.ok(result); // 200
    }

    // GET /api/students/filter?gpa={minGpa}
    @GetMapping("/filter")
    public ResponseEntity<List<Student>> filterByGpa(@RequestParam Double gpa) {
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getGpa() >= gpa) {
                result.add(s);
            }
        }
        return ResponseEntity.ok(result); // 200
    }

    // POST /api/students
    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        students.add(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(student); // 201
    }

    // PUT /api/students/{studentId}
    @PutMapping("/{studentId}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long studentId, @RequestBody Student updatedStudent) {
        for (Student s : students) {
            if (s.getStudentId().equals(studentId)) {
                s.setFirstName(updatedStudent.getFirstName());
                s.setLastName(updatedStudent.getLastName());
                s.setEmail(updatedStudent.getEmail());
                s.setMajor(updatedStudent.getMajor());
                s.setGpa(updatedStudent.getGpa());
                return ResponseEntity.ok(s); // 200
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404
    }
}
