package br.com.kauanamorim.todolist.app.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.kauanamorim.todolist.app.user.interfaces.IRegisterUser;
import br.com.kauanamorim.todolist.domain.user.UserModel;
import br.com.kauanamorim.todolist.infrastructure.repository.user.IUserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUser implements IRegisterUser {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserModel execute(UserModel userModel) throws Exception {
        UserModel user = this.userRepository.findByUsername(userModel.getUsername());
        if(user != null) {
            throw new BadRequestException("User already exists");
        }

        String passwordHashed = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());
        userModel.setPassword(passwordHashed);
        return this.userRepository.save(userModel);
    }
}
