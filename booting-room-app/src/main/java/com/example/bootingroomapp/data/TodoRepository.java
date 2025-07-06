package com.example.bootingroomapp.data;

import com.example.bootingroomapp.models.Todo;

import java.util.*;

public interface TodoRepository {
    Todo save(Todo todo);
    void deleteById(Long id);
    boolean existsById(Long id);

    List<Todo> findAll();
    Optional<Todo> findById(Long id);
    void archive(Todo todo); // added for archiving logic
}

