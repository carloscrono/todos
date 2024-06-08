package com.api.todos.grpc;

import com.api.todos.service.TodoService;
import com.api.todos.model.Todo;
import io.grpc.stub.StreamObserver;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

public class TodoServiceImpl extends TodoServiceGrpc.TodoServiceImplBase {

    @Autowired
    TodoService todoService;

    @Override
    public void createTodo(TodoRequest request, StreamObserver<TodoResponse> responseObserver) {
        Todo todo = new Todo();
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.getCompleted());

        Todo createdTodo = todoService.createTodo(todo);

        com.api.todos.grpc.Todo grpcTodo = com.api.todos.grpc.Todo.newBuilder()
                .setId(createdTodo.getId())
                .setCompleted(createdTodo.isCompleted())
                .setDescription(createdTodo.getDescription())
                .build();

        com.api.todos.grpc.TodoResponse response = com.api.todos.grpc.TodoResponse.newBuilder()
                .setTodo(grpcTodo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getTodo(TodoId request, StreamObserver<TodoResponse> responseObserver) {
        Todo todo = todoService.getTodoById(request.getId());

        com.api.todos.grpc.Todo grpcTodo = com.api.todos.grpc.Todo.newBuilder()
                .setId(todo.getId())
                .setCompleted(todo.isCompleted())
                .setDescription(todo.getDescription())
                .build();

        TodoResponse response = TodoResponse.newBuilder()
                .setTodo(grpcTodo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllTodos(Empty request, StreamObserver<TodoList> responseObserver) {
        List<Todo> todos = todoService.getAllTodos();

        List<com.api.todos.grpc.Todo> grpcTodos = todos.stream()
                .map(todo -> com.api.todos.grpc.Todo.newBuilder()
                        .setId(todo.getId())
                        .setCompleted(todo.isCompleted())
                        .setDescription(todo.getDescription())
                        .build())
                .collect(Collectors.toList());

        TodoList response = TodoList.newBuilder()
                .addAllTodos(grpcTodos)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void updateTodo(com.api.todos.grpc.Todo request, StreamObserver<TodoResponse> responseObserver) {
        Todo todo = new Todo();
        todo.setId(request.getId());
        todo.setDescription(request.getDescription());
        todo.setCompleted(request.getCompleted());

        Todo updatedTodo = todoService.updateTodo(todo);

        com.api.todos.grpc.Todo grpcTodo = com.api.todos.grpc.Todo.newBuilder()
                .setId(updatedTodo.getId())
                .setCompleted(updatedTodo.isCompleted())
                .setDescription(updatedTodo.getDescription())
                .build();

        TodoResponse response = TodoResponse.newBuilder()
                .setTodo(grpcTodo)
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTodo(TodoId request, StreamObserver<Empty> responseObserver) {
        todoService.deleteTodo(request.getId());

        Empty response = Empty.newBuilder().build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
