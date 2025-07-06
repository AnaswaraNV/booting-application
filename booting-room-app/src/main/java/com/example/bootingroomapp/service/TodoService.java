package com.example.bootingroomapp.service;

import com.example.bootingroomapp.data.TodoRepository;
import com.example.bootingroomapp.models.Todo;

import java.util.List;
import java.util.stream.Collectors;

public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public Todo add(String text) {
        Todo todo = new Todo(text);
        return repository.save(todo);
    }

    public void delete(long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Todo not found");
        }
        repository.deleteById(id);
    }

    public List<Todo> findAll() {
        return repository.findAll();
    }

    public void archiveTodo(Long id) {
        Todo todo = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found"));
        if (todo.isCompleted()) {
            repository.archive(todo);
        }
    }

    public void updateText(Long id, String newText) {
        Todo todo = repository.findById(id).orElseThrow();
        todo.setText(newText);
        repository.save(todo);
    }

    public List<Todo> findCompleted() {
        return repository.findAll().stream()
                .filter(Todo::isCompleted)
                .collect(Collectors.toList());
    }

    public void deleteIfExists(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Missing");
        }
        repository.deleteById(id);
    }

}