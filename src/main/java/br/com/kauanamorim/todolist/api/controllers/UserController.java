package br.com.kauanamorim.todolist.api.controllers;

import br.com.kauanamorim.todolist.app.user.interfaces.IRegisterUser;
import br.com.kauanamorim.todolist.domain.user.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IRegisterUser registerUser;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody UserModel userModel) throws Exception {
        UserModel userCreated = this.registerUser.execute(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
