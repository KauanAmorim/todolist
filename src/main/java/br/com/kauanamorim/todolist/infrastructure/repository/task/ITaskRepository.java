package br.com.kauanamorim.todolist.infrastructure.repository.task;

import br.com.kauanamorim.todolist.domain.task.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByIdUser(UUID userId);
    Optional<TaskModel> findByIdAndIdUser(UUID userId, UUID taskId);
}
