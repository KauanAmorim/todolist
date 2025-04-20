package br.com.kauanamorim.todolist.app.task;

import br.com.kauanamorim.todolist.app.task.interfaces.IListTask;
import br.com.kauanamorim.todolist.domain.task.TaskModel;
import br.com.kauanamorim.todolist.infrastructure.repository.task.ITaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ListTask implements IListTask {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public List<TaskModel> execute(UUID idUser) {
        return this.taskRepository.findByIdUser(idUser);
    }
}
