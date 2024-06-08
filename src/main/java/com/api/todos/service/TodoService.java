package com.api.todos.service;

import com.api.todos.dao.TodoDAO;
import com.api.todos.exception.TodoNotFoundException;
import com.api.todos.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {

    @Autowired
    private TodoDAO todoDAO;

    public List<Todo> getAllTodos() {
        return todoDAO.findAll();
    }

    public Todo getTodoById(Long id) {
        Todo todo = todoDAO.findById(id);
        if (todo == null) {
            throw new TodoNotFoundException("Todo with id " + id + " not found");
        }
        return todo;
    }

    public Todo createTodo(Todo todo) {
        todoDAO.save(todo);
        return todo;
    }

    public Todo updateTodo(Todo todo) {
        todoDAO.update(todo);
        return todo;
    }

    public Long deleteTodo(Long id) {
        return todoDAO.deleteById(id);
    }
}

