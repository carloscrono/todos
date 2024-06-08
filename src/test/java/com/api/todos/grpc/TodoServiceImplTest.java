package com.api.todos.grpc;

import com.api.todos.model.Todo;
import com.api.todos.service.TodoService;
import io.grpc.internal.testing.StreamRecorder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TodoServiceImplTest {

    @Mock
    private TodoService todoService;

    @InjectMocks
    private TodoServiceImpl todoServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("Test Todo");
        todo.setCompleted(false);

        when(todoService.createTodo(any(Todo.class))).thenReturn(todo);

        com.api.todos.grpc.TodoRequest request = com.api.todos.grpc.TodoRequest.newBuilder()
                .setDescription("Test Todo")
                .setCompleted(false)
                .build();

        StreamRecorder<com.api.todos.grpc.TodoResponse> responseObserver = StreamRecorder.create();
        todoServiceImpl.createTodo(request, responseObserver);

        com.api.todos.grpc.TodoResponse response = responseObserver.firstValue().get();

        assertEquals(1L, response.getTodo().getId());
        assertEquals("Test Todo", response.getTodo().getDescription());
        assertFalse(response.getTodo().getCompleted());
    }

    @Test
    public void testGetTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("Test Todo");
        todo.setCompleted(false);

        when(todoService.getTodoById(1L)).thenReturn(todo);

        com.api.todos.grpc.TodoId request = com.api.todos.grpc.TodoId.newBuilder()
                .setId(1L)
                .build();

        StreamRecorder<com.api.todos.grpc.TodoResponse> responseObserver = StreamRecorder.create();
        todoServiceImpl.getTodo(request, responseObserver);

        com.api.todos.grpc.TodoResponse response = responseObserver.firstValue().get();

        assertEquals(1L, response.getTodo().getId());
        assertEquals("Test Todo", response.getTodo().getDescription());
        assertFalse(response.getTodo().getCompleted());
    }

    @Test
    public void testGetAllTodos() throws Exception {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setDescription("Test Todo 1");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setDescription("Test Todo 2");
        todo2.setCompleted(true);

        List<Todo> todos = Arrays.asList(todo1, todo2);

        when(todoService.getAllTodos()).thenReturn(todos);

        com.api.todos.grpc.Empty request = com.api.todos.grpc.Empty.newBuilder().build();

        StreamRecorder<com.api.todos.grpc.TodoList> responseObserver = StreamRecorder.create();
        todoServiceImpl.getAllTodos(request, responseObserver);

        com.api.todos.grpc.TodoList response = responseObserver.firstValue().get();

        assertEquals(2, response.getTodosCount());
        assertEquals(1L, response.getTodos(0).getId());
        assertEquals("Test Todo 1", response.getTodos(0).getDescription());
        assertFalse(response.getTodos(0).getCompleted());
        assertEquals(2L, response.getTodos(1).getId());
        assertEquals("Test Todo 2", response.getTodos(1).getDescription());
        assertTrue(response.getTodos(1).getCompleted());
    }

    @Test
    public void testUpdateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setDescription("Updated Todo");
        todo.setCompleted(true);

        when(todoService.updateTodo(any(Todo.class))).thenReturn(todo);

        com.api.todos.grpc.Todo request = com.api.todos.grpc.Todo.newBuilder()
                .setId(1L)
                .setDescription("Updated Todo")
                .setCompleted(true)
                .build();

        StreamRecorder<com.api.todos.grpc.TodoResponse> responseObserver = StreamRecorder.create();
        todoServiceImpl.updateTodo(request, responseObserver);

        com.api.todos.grpc.TodoResponse response = responseObserver.firstValue().get();

        assertEquals(1L, response.getTodo().getId());
        assertEquals("Updated Todo", response.getTodo().getDescription());
        assertTrue(response.getTodo().getCompleted());
    }

    @Test
    public void testDeleteTodo() throws Exception {
        com.api.todos.grpc.TodoId request = com.api.todos.grpc.TodoId.newBuilder()
                .setId(1L)
                .build();

        StreamRecorder<com.api.todos.grpc.Empty> responseObserver = StreamRecorder.create();
        todoServiceImpl.deleteTodo(request, responseObserver);

        com.api.todos.grpc.Empty response = responseObserver.firstValue().get();

        assertEquals(1, responseObserver.getValues().size());
    }
}
