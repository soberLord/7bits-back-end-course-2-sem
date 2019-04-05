package it.sevenbits.workshop.web.service;

import it.sevenbits.workshop.core.model.EnumValues;
import it.sevenbits.workshop.core.model.Task;
import it.sevenbits.workshop.core.repository.TaskRepository;
import it.sevenbits.workshop.web.model.RequestUpdateTaskValues;
import org.springframework.dao.IncorrectResultSizeDataAccessException;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ServiceRepository {
    private final TaskRepository taskRepository;

    public ServiceRepository(TaskRepository tasksRepository){
        this.taskRepository = tasksRepository;
    }

    private String getNextId() {
        return UUID.randomUUID().toString();
    }

    public List<Task> getAllTasks() {
        return taskRepository.getAllTasks();
    }

    public Task getTask(String id) throws IndexOutOfBoundsException {
        return taskRepository.getTask(id);
    }

    public Task createTask(String text) {
        String id = getNextId();
        String status = EnumValues.EnumStatus.inbox.toString();
        String date = ServiceCurrentDate.getCurrentDate();
        Task task = new Task(id, text, status, date);
        return taskRepository.createTask(task);
    }

    public int deleteTask(String id) throws IndexOutOfBoundsException {
        return taskRepository.deleteTask(id);
    }

    public Task updateTask(String id, RequestUpdateTaskValues requestBody) throws IndexOutOfBoundsException {
        Task task = getTask((id));
        if (!requestBody.getText().equals("null")) {
            task.setText(requestBody.getText());
        }
        if (!requestBody.getStatus().equals("null")) {
            task.setStatus(requestBody.getStatus());
        }
        return taskRepository.updateTask(task);
    }

}