package br.com.kauanamorim.todolist.infrastructure.repository.user;

import br.com.kauanamorim.todolist.domain.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
    UserModel findByUsername(String username);
}
