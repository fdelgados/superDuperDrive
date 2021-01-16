package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public void signup(User user) throws UserIsNotAvailableException, UnableToCreateUserException {
        ensureUserDoesNotExist(user.getUsername());

        String salt = createSalt();
        String hashedPassword = createHashedPassword(user.getPassword(), salt);

        User newUser = new User(null, user.getUsername(), salt, hashedPassword, user.getFirstName(), user.getLastName());
        if (userMapper.insert(newUser) != 1) {
            throw new UnableToCreateUserException("New user cannot be created");
        }
    }

    private String createSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);

        return Base64.getEncoder().encodeToString(salt);
    }

    private String createHashedPassword(String password, String salt) {
        return hashService.getHashedValue(password, salt);
    }

    private void ensureUserDoesNotExist(String username) throws UserIsNotAvailableException {
        if (userMapper.getUser(username) != null) {
            throw new UserIsNotAvailableException("The username already exists");
        }
    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userMapper.getUser(authentication.getName());
    }
}
