package br.com.kauanamorim.todolist.task;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        taskModel.setIdUser(idUser);

        LocalDateTime currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "A data de inicio / data de término deve ser maior do que a data atual"
            );
        }

        if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                "A data de início deve ser menor do que a data de término"
            );
        }

        TaskModel taskCreated = this.taskRepository.save(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    public ResponseEntity<Object> list(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        List<TaskModel> tasks = this.taskRepository.findByIdUser(idUser);
        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
        @PathVariable UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request
    ) {
        UUID idUser = (UUID) request.getAttribute("idUser");

        Optional<TaskModel> result = this.taskRepository.findByIdAndIdUser(id, idUser);
        if (result.isEmpty()) {
            return ResponseEntity.status(404).body("Task does not exist");
        }

        TaskModel taskStored = result.get();
        taskStored.update(taskModel);
        return ResponseEntity.ok().body(this.taskRepository.save(taskStored));
    }
}
