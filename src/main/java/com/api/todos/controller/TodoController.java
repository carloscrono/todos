package com.api.todos.controller;

import com.api.todos.model.Todo;
import com.api.todos.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Log4j2
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<Todo> getAllTodos() {
        return todoService.getAllTodos();
    }

    @GetMapping("/{id}")
    public Todo getTodoById(@PathVariable Long id) {
        return todoService.getTodoById(id);
    }

    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        log.info(todo);
        return todoService.createTodo(todo);
    }

    @PatchMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo) {
        return todoService.updateTodo(todo);
    }

    @DeleteMapping("/{id}")
    public Long deleteTodo(@PathVariable Long id) {
        return todoService.deleteTodo(id);
    }
}

