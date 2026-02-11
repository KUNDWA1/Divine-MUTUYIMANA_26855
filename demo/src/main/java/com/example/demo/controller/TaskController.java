package com.example.demo.controller;

import com.example.demo.model.Task;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();

    public TaskController() {
        tasks.add(new Task(1L, "Finish Assignment", "Complete Spring Boot API", false, "HIGH", "2026-02-20"));
        tasks.add(new Task(2L, "Buy Groceries", "Milk and Bread", true, "LOW", "2026-02-12"));
        tasks.add(new Task(3L, "Study Java", "OOP concepts", false, "MEDIUM", "2026-02-15"));
    }

    // GET all
    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {
        return ResponseEntity.ok(tasks);
    }

    // GET by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                return ResponseEntity.ok(t);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // GET by completion status
    @GetMapping("/status")
    public ResponseEntity<List<Task>> getByStatus(@RequestParam boolean completed) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.isCompleted() == completed) {
                result.add(t);
            }
        }
        return ResponseEntity.ok(result);
    }

    // GET by priority
    @GetMapping("/priority/{priority}")
    public ResponseEntity<List<Task>> getByPriority(@PathVariable String priority) {
        List<Task> result = new ArrayList<>();
        for (Task t : tasks) {
            if (t.getPriority().equalsIgnoreCase(priority)) {
                result.add(t);
            }
        }
        return ResponseEntity.ok(result);
    }

    // POST create
    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        tasks.add(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(task);
    }

    // PUT update
    @PutMapping("/{taskId}")
    public ResponseEntity<Task> updateTask(@PathVariable Long taskId,
                                           @RequestBody Task updated) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                t.setTitle(updated.getTitle());
                t.setDescription(updated.getDescription());
                t.setPriority(updated.getPriority());
                t.setDueDate(updated.getDueDate());
                t.setCompleted(updated.isCompleted());
                return ResponseEntity.ok(t);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // PATCH mark complete
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<Task> markComplete(@PathVariable Long taskId) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                t.setCompleted(true);
                return ResponseEntity.ok(t);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // DELETE
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long taskId) {
        for (Task t : tasks) {
            if (t.getTaskId().equals(taskId)) {
                tasks.remove(t);
                return ResponseEntity.noContent().build();
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}

