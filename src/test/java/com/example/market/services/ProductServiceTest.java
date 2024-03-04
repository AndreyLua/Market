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

import org.springframework.test.context.junit4.SpringRunner;

import java.io.Console;
import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;

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
        List<Product> productList = productService.listProducts(null);
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
        Assert.assertNotNull(productList);
        Assert.assertEquals(0, productList.size());
    }

    @Test
    void listProductsTestNotNull() {
        List<Product> productList = productService.listProducts("testTitle");
        Mockito.verify(productRepository, Mockito.times(1)).findByTitle("testTitle");
        Assert.assertNotNull(productList);
        Assert.assertEquals(0, productList.size());
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

        class  Employee{
            public int s()
            {
                return  0;
            }
        }

        List<Employee> employees = new ArrayList<>();

        employees.add(new Employee());

        employees.filter(Employee::s).collect(Collectors.toUnmodifiableList());
        employees.stream().map(Employee::s).collect(Collectors.toList());

        User user = productService.getUserByPrincipal(null);
        Assert.assertNull(user.getEmail());
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