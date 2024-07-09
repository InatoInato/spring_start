package com.inato.spring.service;

import com.inato.spring.entity.UserEntity;
import com.inato.spring.exception.UserAlreadyExistException;
import com.inato.spring.exception.UserNotFoundException;
import com.inato.spring.model.User;
import com.inato.spring.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepo;

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException{
        if(userRepo.findByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistException("User is already exist!");
        }
        return userRepo.save(user);

    }

    public User getOneUser(Long id) throws UserNotFoundException{
        UserEntity user = userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        return User.toModel(user);
    }

    public Long deleteUser(Long id){
        userRepo.deleteById(id);
        return id;
    }

    public List<User> getAllUsers(){
        List<UserEntity> userEntities = (List<UserEntity>) userRepo.findAll();
        return userEntities.stream()
                .map(User::toModel)
                .collect(Collectors.toList());
    }

    public User convertToUser(UserEntity userEntity){
        User user = new User();
        user.setId(userEntity.getId());
        user.setUsername(userEntity.getUsername());
        return user;
    }
}
