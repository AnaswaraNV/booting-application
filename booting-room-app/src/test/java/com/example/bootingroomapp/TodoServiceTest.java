package com.example.bootingroomapp;

import com.example.bootingroomapp.data.TodoRepository;
import com.example.bootingroomapp.models.Todo;
import com.example.bootingroomapp.service.TodoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

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

    //used to capture the arguments that were passed to a mocked method
    @Captor
    private ArgumentCaptor<Todo> todoCaptor;


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

    @Test
    public void shouldArchiveTodoCompleted() {
        Todo todo = new Todo("blah");
        todo.setCompleted(true);
        Optional<Todo> optionalTodo = Optional.of(todo);
        when(todoRepository.findById(1L)).thenReturn(optionalTodo);

        todoService.archiveTodo(1L);
        verify(todoRepository).archive(todo);
    }

    @Test
    public void shouldArchiveTodoNotCompleted() {
        Todo todo = new Todo("blah");
        todo.setCompleted(false);
        Optional<Todo> optionalTodo = Optional.of(todo);
        when(todoRepository.findById(1L)).thenReturn(optionalTodo);

        todoService.archiveTodo(1L);
        verify(todoRepository, never()).archive(any());
    }

    @Test
    public void shouldArchiveTodoException() {
        when(todoRepository.findById(32L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoService.archiveTodo(32L);
        });

        assertEquals("Not found", exception.getMessage());
        verify(todoRepository, never()).archive(any());
    }

    @Test
    public void findCompleted() {
        List<Todo> completedTodo = List.of(new Todo("hi", true), new Todo("hello", false));
        when(todoRepository.findAll()).thenReturn(completedTodo);
        List<Todo> completedExpected = completedTodo.stream().
                filter(Todo::isCompleted).toList();

        List<Todo> completed = todoService.findCompleted();

        assertEquals(completedExpected, completed);
    }

    //todoCapture****
    @Test
    public void shouldUpdateText() {
        Todo todo = new Todo("old");
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        todoService.updateText(1L, "new");
        verify(todoRepository).save(todoCaptor.capture());
        assertEquals("new", todoCaptor.getValue().getText());
    }

    @Test
    public void shouldDeleteByIdMissing() {
        when(todoRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            todoService.deleteIfExists(1L);
        });
        assertEquals("Missing", exception.getMessage());
        verify(todoRepository, never()).deleteById(any());
    }

    @Test
    public void shouldDeleteById_Id_Exists() {
        when(todoRepository.existsById(1L)).thenReturn(true);
        todoService.deleteIfExists(1L);
        verify(todoRepository).deleteById(1L);
    }
}
