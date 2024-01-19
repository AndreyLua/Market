package com.example.market.services;

import com.example.market.EmptyPrincipal;
import com.example.market.models.Image;
import com.example.market.models.Product;
import com.example.market.models.User;
import com.example.market.repositories.ProductRepository;
import com.example.market.repositories.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@SpringBootTest
@RunWith(SpringRunner.class)
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private ProductRepository productRepository;

    @Test
    void listProductsNull() {
        productService.listProducts(null);
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    void listProductsTestNotNull() {
        productService.listProducts("testTitle");
        Mockito.verify(productRepository, Mockito.times(1)).findByTitle("testTitle");
    }

    @Test
    void saveProduct() throws IOException {
        User user = new User();
        user.setEmail("test");
        Product product = new Product();
        product.setUser(user);

        Product newProduct = new Product();
        List<Image> images = new ArrayList<>();
        images.add(new Image());
        images.add(new Image());
        images.add(new Image());
        newProduct.setImages(images);

        Mockito.doReturn(newProduct)
                .when(productRepository)
                .save(product);
        MockMultipartFile file1 = new MockMultipartFile("filename", new byte[0]);
        productService.saveProduct(new EmptyPrincipal(), product, file1, file1, file1);

        Mockito.verify(productRepository, Mockito.times(2)).save(product);
    }

    @Test
    void getUserByPrincipalNull() {
        User user = productService.getUserByPrincipal(null);
        Assert.assertTrue(user.getEmail() == null);
    }

    @Test
    void getUserByPrincipalNotNull() {
        EmptyPrincipal principal = new EmptyPrincipal();
        User user = productService.getUserByPrincipal(principal);
        Mockito.verify(userRepository, Mockito.times(1)).findByEmail(principal.getName());
    }

    @Test
    void deleteProductNotNull() {
        Long id = 1L;
        Product product = new Product();
        User user = new User();
        user.setId(id);
        product.setUser(user);
        Optional optionalProduct = Optional.of(product);
        Mockito.doReturn(optionalProduct)
                .when(productRepository)
                .findById(id);
        productRepository.delete(product);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
    @Test
    void getProductById() {
        Long id = 1L;
        productService.getProductById(id);
        Mockito.verify(productRepository, Mockito.times(1)).findById(id);
    }
}