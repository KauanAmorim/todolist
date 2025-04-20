package br.com.kauanamorim.todolist.app.task.interfaces;

import br.com.kauanamorim.todolist.domain.task.TaskModel;

import java.util.List;
import java.util.UUID;

public interface IListTask {
    List<TaskModel> execute(UUID idUser);
}
