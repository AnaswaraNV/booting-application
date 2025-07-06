package com.example.bootingroomapp;

import com.example.bootingroomapp.data.TodoRepository;
import com.example.bootingroomapp.models.Todo;
import com.example.bootingroomapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TodoServiceTest {
    @Mock
    TodoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    public void shouldAddTodo() {
        Todo todo = new Todo("hello");
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo result = todoService.add(todo.getText());

        assertEquals(result.getText(), "hello");
        verify(todoRepository).save(any(Todo.class));
    }

    @Test
    public void deleteTodo() {
        when(todoRepository.existsById(1L)).thenReturn(true);
        todoService.delete(1L);
        //assert delete by id called
        verify(todoRepository).deleteById(1L);
    }

    @Test
    public void deleteWithoutIdTodo() {
        when(todoRepository.existsById(42L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class,  () -> {
                todoService.delete(42L);
        });

        assertEquals(exception.getMessage(), "Todo not found");
        verify(todoRepository, never()).deleteById(any()); //verify  delete by id was never called
    }

    @Test
    public void findAll() {
        List<Todo> lists = List.of(new Todo("1"), new Todo("2"));
        when(todoRepository.findAll()).thenReturn(lists);

        List<Todo> results  = todoService.findAll();

        assertEquals(2, results.size());
        verify(todoRepository).findAll();
    }
}
