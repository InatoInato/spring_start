package com.inato.spring.controller;

import com.inato.spring.entity.UserEntity;
import com.inato.spring.exception.UserAlreadyExistException;
import com.inato.spring.exception.UserNotFoundException;
import com.inato.spring.model.User;
import com.inato.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity registration(@RequestBody UserEntity user){
        try{
            userService.registration(user);
            return ResponseEntity.ok("User saved!");
        }
        catch (UserAlreadyExistException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Error!");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity getOneUser(@PathVariable Long id){
        try{
            return ResponseEntity.ok(userService.getOneUser(id));
        }
        catch (UserNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Response Error!");
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try{
            return ResponseEntity.ok("User has been deleted with id: " + userService.deleteUser(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Response Error!");
        }
    }
}
