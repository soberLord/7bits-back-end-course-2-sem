package it.sevenbits.workshop.web.controllers;

import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.web.model.RequestCreateTask;
import it.sevenbits.workshop.web.model.RequestGetAllTasks;
import it.sevenbits.workshop.web.model.RequestUpdateTaskValues;
import it.sevenbits.workshop.web.service.ServiceRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;


public class TestTaskController {
    private ServiceRepository mockServiceRepository;
    private TaskController taskController;

    @Before
    public void setup() {
        mockServiceRepository = mock(ServiceRepository.class);
        taskController = new TaskController(mockServiceRepository);
    }

    @Test
    public void testGetAllItems() {
        Map mockTasks = mock(Map.class);
        RequestGetAllTasks requestGetAllTasks = mock(RequestGetAllTasks.class);
        when(mockServiceRepository.getAllTasks(requestGetAllTasks)).thenReturn(mockTasks);

        ResponseEntity<Map> answer =  taskController.getAllTasks(requestGetAllTasks);
        verify(mockServiceRepository, times(1)).getAllTasks(requestGetAllTasks);
        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTasks, answer.getBody());
    }

    @Test
    public void testGetTask() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        Task mockTask = mock(Task.class);
        when(mockServiceRepository.getTask(anyString())).thenReturn(mockTask);

        ResponseEntity answer = taskController.getTask(taskId);
        verify(mockServiceRepository, times(1)).getTask(taskId);
        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
    }

    @Test
    public void testGetTaskException() {
        String taskId = "deea44c7-a180-4898-9527-58db0ed34683";
        when(mockServiceRepository.getTask(taskId)).thenThrow(new IndexOutOfBoundsException());
        ResponseEntity answer = taskController.getTask(taskId);
        Assert.assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test
    public void testCreateTask() throws URISyntaxException {
        String mockId = "deea44c7-a180-4898-9527-58db0ed34683";
        String mockText="mockText";
        URI location = new URI(String.format("/tasks/%s", mockId));
        RequestCreateTask requestBody = mock(RequestCreateTask.class);
        when(requestBody.getText()).thenReturn(mockText);

        Task mockTask = mock(Task.class);
        when(mockTask.getId()).thenReturn(mockId);
        when(mockServiceRepository.createTask(mockText)).thenReturn(mockTask);
        ResponseEntity answer = taskController.create(requestBody);

        Assert.assertEquals(HttpStatus.CREATED, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
        Assert.assertEquals(location, answer.getHeaders().getLocation());
    }

    @Test
    public void testUpdateTask() {
        String mockId = "deea44c7-a180-4898-9527-58db0ed34683";
        String mockText="initText";
        String mockStatus="inbox";
        RequestUpdateTaskValues requestBody = mock(RequestUpdateTaskValues.class);
        when(requestBody.getText()).thenReturn(mockText);
        when(requestBody.getStatus()).thenReturn(mockStatus);

        Task mockTask = mock(Task.class);

        when(mockServiceRepository.getTask(mockId)).thenReturn(mockTask);
        when(mockServiceRepository.updateTask(mockId, requestBody)).thenReturn(mockTask);
        ResponseEntity answer = taskController.updateTask(mockId, requestBody);

        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(mockTask, answer.getBody());
    }

    @Test
    public void testUpdateTaskException() {
        String mockId = "deea44c7-a180-4898-9527-58db0ed34683";
        RequestUpdateTaskValues requestBody = mock(RequestUpdateTaskValues.class);
        when(mockServiceRepository.updateTask(mockId, requestBody))
                .thenThrow(new IndexOutOfBoundsException());
        ResponseEntity answer = taskController.updateTask(mockId, requestBody);
        Assert.assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }

    @Test
    public void testDeleteTask() {
        String mockId = "deea44c7-a180-4898-9527-58db0ed34683";
        int expectedDeletedTasks = 1;
        when(mockServiceRepository.deleteTask(mockId)).thenReturn(1);
        ResponseEntity answer = taskController.deleteTask(mockId);

        Assert.assertEquals(HttpStatus.OK, answer.getStatusCode());
        Assert.assertSame(expectedDeletedTasks, answer.getBody());
    }

    @Test
    public void testDeleteTaskException() {
        String mockId = "deea44c7-a180-4898-9527-58db0ed34683";
        when(mockServiceRepository.deleteTask(mockId)).thenThrow(new IndexOutOfBoundsException());
        when(mockServiceRepository.getTask(mockId)).thenThrow(new IndexOutOfBoundsException());
        ResponseEntity answer = taskController.deleteTask(mockId);
        Assert.assertEquals(HttpStatus.NOT_FOUND, answer.getStatusCode());
    }
}
