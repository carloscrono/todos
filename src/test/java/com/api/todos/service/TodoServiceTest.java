package com.api.todos.service;

import com.api.todos.model.Todo;
import com.api.todos.dao.TodoDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {

    @Mock
    private TodoDAO todoDAO;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTodo() {
        Todo todo = new Todo();
        todo.setDescription("Test description");
        todo.setCompleted(false);

        when(todoDAO.save(any(Todo.class))).thenReturn(todo);

        Todo createdTodo = todoService.createTodo(todo);

        assertNotNull(createdTodo);
        assertEquals("Test description", createdTodo.getDescription());
        assertFalse(createdTodo.isCompleted());
        verify(todoDAO, times(1)).save(todo);
    }

    @Test
    void testGetTodoById() {
        Todo mockTodo = new Todo();
        mockTodo.setId(1L);
        mockTodo.setDescription("Test Todo");
        mockTodo.setCompleted(false);

        when(todoDAO.findById(anyLong())).thenReturn(mockTodo);

        Todo result = todoService.getTodoById(1L);

        assertNotNull(result);
    }

    @Test
    void testUpdateTodo() {
        Todo mockTodo = new Todo();
        mockTodo.setId(1L);
        mockTodo.setDescription("Test Todo");
        mockTodo.setCompleted(false);

        when(todoDAO.findById(anyLong())).thenReturn(mockTodo);

        Todo result = todoService.updateTodo(mockTodo);

        assertNotNull(result);
        assertEquals("Test Todo", result.getDescription());
        assertFalse(result.isCompleted());

    }

    @Test
    void testDeleteTodo() {
        Todo mockTodo = new Todo();
        mockTodo.setId(Long.MAX_VALUE);
        mockTodo.setDescription("Test Todo");
        mockTodo.setCompleted(false);

        Long id = mockTodo.getId();

        when(todoDAO.findById(anyLong())).thenReturn(mockTodo);
        Long deteledId = todoService.deleteTodo(id);

        assertNotEquals(id, deteledId);
    }

    @Test
    void testGetAllTodos() {
        int currentSize = todoService.getAllTodos().size();

        Todo mockTodo = new Todo();
        mockTodo.setId(Long.MAX_VALUE);
        mockTodo.setDescription("Test Todo");
        mockTodo.setCompleted(false);

        Todo mockTodo2 = new Todo();
        mockTodo2.setId(Long.MAX_VALUE);
        mockTodo2.setDescription("Test Todo");
        mockTodo2.setCompleted(false);

        Todo mockTodo3 = new Todo();
        mockTodo3.setId(Long.MAX_VALUE);
        mockTodo3.setDescription("Test Todo");
        mockTodo3.setCompleted(false);

        List<Todo> todos = new ArrayList<>();
        todos.add(todoService.createTodo(mockTodo));
        todos.add(todoService.createTodo(mockTodo2));
        todos.add(todoService.createTodo(mockTodo3));



        assertEquals(currentSize + 3, todos.size());
    }
}
