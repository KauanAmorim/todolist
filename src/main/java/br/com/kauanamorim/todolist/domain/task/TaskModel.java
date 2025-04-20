package br.com.kauanamorim.todolist.domain.task;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name = "tb_tasks")
public class TaskModel {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(length = 50)
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("O campo title deve conter no máximo 50 caracteres");
        }
        this.title = title;
    }

    public void update(TaskModel source) {
        if (source.title != null) {
            this.title = source.title;
        }

        if (source.description != null) {
            this.description = source.description;
        }

        if (source.startAt != null) {
            this.startAt = source.startAt;
        }

        if (source.endAt != null) {
            this.endAt = source.endAt;
        }

        if (source.priority != null) {
            this.priority = source.priority;
        }
    }
}
