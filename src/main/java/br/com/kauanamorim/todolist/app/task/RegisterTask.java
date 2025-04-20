package br.com.kauanamorim.todolist.app.task;

import br.com.kauanamorim.todolist.app.task.interfaces.IRegisterTask;
import br.com.kauanamorim.todolist.domain.task.TaskModel;
import br.com.kauanamorim.todolist.infrastructure.repository.task.ITaskRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RegisterTask implements IRegisterTask {

    @Autowired
    private ITaskRepository taskRepository;

    @Override
    public TaskModel execute(TaskModel taskModel) throws Exception {
        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            throw new BadRequestException(
                "A data de inicio / data de término deve ser maior do que a data atual"
            );
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            throw new BadRequestException(
                    "A data de início deve ser menor do que a data de término"
            );
        }

        return this.taskRepository.save(taskModel);
    }
}
