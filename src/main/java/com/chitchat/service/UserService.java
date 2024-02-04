package com.chitchat.service;

import com.chitchat.dto.UserDto;
import com.chitchat.entity.User;
import com.chitchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    private final ModelMapper modelMapper;

    public UserDto saveUser(User user) {
        user = this.repository.save(user);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }

    public void disconnect(User user) {


    }

    public Optional<UserDto> findByUserNameAndPassword(User user) {
        Optional<User> result = this.repository.findByUserNameAndPassword(user.getUserName(), user.getPassword());
        return result.map(value -> modelMapper.map(value, UserDto.class));
    }

    public User getUserById(Long userId) {
        Optional<User> repoResult = this.repository.findById(userId);
        if(repoResult.isPresent()) {
            repoResult.get().setPassword(null);
            return repoResult.get();
        }
        return null;
    }

    public List<User> getAllUsers() {
        List<User> users = this.repository.findAll();
        users.forEach(user -> {user.setPassword(null);});
        return users;
    }
}
