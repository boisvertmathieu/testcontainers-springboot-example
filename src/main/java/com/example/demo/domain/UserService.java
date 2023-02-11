package com.example.demo.domain;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.convertirListeUserEntityVersListeUser(userRepository.findAll());
    }

    public User findById(UUID id) throws ResourceNotFoundException {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + id));
        return userMapper.convertirUserEntityVersUser(userEntity);
    }

    public void createUser(User user) {
        UserEntity userEntity = userMapper.convertirUserVersUserEntity(user);
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setCreatedBy("admin");
        userEntity.setUpdatedAt(LocalDateTime.now());
        userEntity.setUpdatedBy("admin");
        userRepository.save(userEntity);
    }
}
