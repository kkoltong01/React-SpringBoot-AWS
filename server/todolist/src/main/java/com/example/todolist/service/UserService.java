package com.example.todolist.service;


import com.example.todolist.model.UserEntity;
import com.example.todolist.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity) {
        if(userEntity == null || userEntity.getUsername() == null) {
            throw new RuntimeException("Invalid arguments");
        }
        final String username = userEntity.getUsername();
        if(userRepository.existsByUsername(username)) {
            log.warn("{}는 이미 존재하는 사용자입니다.", username);
            throw new RuntimeException("이미 존재하는 사용자입니다.");
        }

        return userRepository.save(userEntity);
    }

    public UserEntity getByCredentials(final String username, final String password,
                                       final PasswordEncoder encoder) {
        // bcry
        final UserEntity originalUser = userRepository.findByUsername(username);

        // matches 메서드를 이용해 같은지 확인
        if(originalUser != null &&
            encoder.matches(password,
                    originalUser.getPassword())) {
            return originalUser;
        }
        return null;
    }
}
