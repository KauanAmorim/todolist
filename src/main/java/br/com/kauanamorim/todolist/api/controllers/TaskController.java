package br.com.kauanamorim.todolist.api.controllers;

import br.com.kauanamorim.todolist.app.task.interfaces.IListTask;
import br.com.kauanamorim.todolist.app.task.interfaces.IRegisterTask;
import br.com.kauanamorim.todolist.app.task.interfaces.IUpdateTask;
import br.com.kauanamorim.todolist.domain.task.TaskModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private IRegisterTask registerTask;

    @Autowired
    private IListTask listTask;

    @Autowired
    private IUpdateTask updateTask;

    @PostMapping
    public ResponseEntity<Object> create(
            @RequestBody TaskModel taskModel,
            HttpServletRequest request
    ) throws Exception {
        UUID idUser = (UUID) request.getAttribute("idUser");
        taskModel.setIdUser(idUser);
        TaskModel taskCreated = this.registerTask.execute(taskModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    public ResponseEntity<Object> list(HttpServletRequest request) {
        UUID idUser = (UUID) request.getAttribute("idUser");
        List<TaskModel> tasks = this.listTask.execute(idUser);
        return ResponseEntity.ok().body(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
        @PathVariable UUID id, @RequestBody TaskModel taskModel, HttpServletRequest request
    ) throws Exception {
        UUID idUser = (UUID) request.getAttribute("idUser");
        taskModel.setId(id);
        taskModel.setIdUser(idUser);
        TaskModel taskUpdated = this.updateTask.execute(taskModel);
        return ResponseEntity.ok().body(taskUpdated);
    }
}
