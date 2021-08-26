package com.example.springJPAexample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllUsers()
    {
        return this.userRepository.findAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable(value = "id") int userId)
    {
        return this.userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with Id " + userId));
    }

    @PostMapping
    public  User createUser(@RequestBody User user)
    {
        return  this.userRepository.save(user);
    }

    @PutMapping("/{id}")
    public User updateUser( @RequestBody User user, @PathVariable("id") int userId)
    {
        User existing = this.userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with Id " + userId));
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());

        return this.userRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") int userId)
    {
        User existing = this.userRepository.findById(userId).
                orElseThrow(() -> new ResourceNotFoundException("User not found with Id " + userId));
        this.userRepository.delete(existing);
        return ResponseEntity.ok().build();
    }
}
