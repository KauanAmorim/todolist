package br.com.kauanamorim.todolist.app.user.interfaces;

import br.com.kauanamorim.todolist.domain.user.UserModel;

public interface IRegisterUser {
    UserModel execute(UserModel userModel) throws Exception;
}
