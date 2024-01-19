package com.example.market.services;

import com.example.market.EmptyPrincipal;
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
import java.util.Map;
import java.util.Optional;

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
        Assert.assertTrue(CoreMatchers.is(user.getRoles())
                .matches(Collections.singleton(Role.ROLE_USER)));

        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void createUserFailTest() {
        User user = new User();

        user.setEmail("Andrey");

        Mockito.doReturn(new User())
                .when(userRepository)
                .findByEmail("Andrey");
        boolean isUserCreated = userService.createUser(user);
        Assert.assertFalse(isUserCreated);
        Mockito.verify(userRepository, Mockito.times(0)).save(user);
    }

    @Test
    public void banUser() {
        User user = new User();
        user.setActive(true);

        Optional optionalUser = Optional.of(user);
        Mockito.doReturn(optionalUser)
                .when(userRepository)
                .findById(user.getId());

        userService.banUser(user.getId());
        Assert.assertFalse(user.isActive());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void unbanUser() {
        User user = new User();
        user.setActive(false);

        Optional optionalUser = Optional.of(user);
        Mockito.doReturn(optionalUser)
                .when(userRepository)
                .findById(user.getId());

        userService.banUser(user.getId());
        Assert.assertTrue(user.isActive());
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }

    @Test
    public void changeUserRoles() {
        User user = new User();
        Mockito.verify(userRepository, Mockito.times(1)).save(user);
    }
    @Test
    public void list() {
        userService.list();
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }
    @Test
    public void getUserByPrincipalNull() {
        User user = userService.getUserByPrincipal(null);
        Assert.assertTrue(user.getEmail() == new User().getEmail());
    }
    @Test
    public void getUserByPrincipalNotNull() {
        EmptyPrincipal emptyPrincipal = new EmptyPrincipal();
        User user = userService.getUserByPrincipal(emptyPrincipal);
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(emptyPrincipal.getName());
    }
}