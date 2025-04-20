package br.com.kauanamorim.todolist.app.task;

import br.com.kauanamorim.todolist.app.task.interfaces.IUpdateTask;
import br.com.kauanamorim.todolist.domain.task.TaskModel;
import br.com.kauanamorim.todolist.infrastructure.repository.task.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateTask implements IUpdateTask {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public TaskModel execute(TaskModel taskModel) throws Exception {
        UUID idTask = taskModel.getId();
        UUID idUser = taskModel.getIdUser();

        Optional<TaskModel> result = this.taskRepository.findByIdAndIdUser(idTask, idUser);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
        }

        TaskModel taskStored = result.get();
        taskStored.update(taskModel);
        return this.taskRepository.save(taskStored);
    }
}
