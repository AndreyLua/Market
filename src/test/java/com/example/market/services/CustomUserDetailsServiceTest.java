package com.example.market.services;

import com.example.market.repositories.ProductRepository;
import com.example.market.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@RunWith(SpringRunner.class)
class CustomUserDetailsServiceTest {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @MockBean
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("test");
        Mockito.verify(userRepository, Mockito.times(1)).
                findByEmail("test");
    }
}