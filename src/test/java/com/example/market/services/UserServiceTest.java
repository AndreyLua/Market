package com.example.market.services;

import com.example.market.models.User;
import com.example.market.models.enums.Role;
import com.example.market.repositories.UserRepository;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private PasswordEncoder passwordEncoder;

    @Test
    public void createUserTest() {
        User user = new User();
        boolean isUserCreated = userService.createUser(user);
        Assert.assertTrue(isUserCreated);
        Assert.assertTrue(user.isActive());
        Assert. assertTrue(CoreMatchers.is(user.getRoles())
                .matches(Collections.singleton(Role.ROLE_USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
    @Test
    public void addUserFailTest() {

    }
}