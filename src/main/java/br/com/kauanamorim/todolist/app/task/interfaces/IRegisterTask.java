package br.com.kauanamorim.todolist.app.task.interfaces;

import br.com.kauanamorim.todolist.domain.task.TaskModel;

public interface IRegisterTask {
    TaskModel execute(TaskModel taskModel) throws Exception;
}
