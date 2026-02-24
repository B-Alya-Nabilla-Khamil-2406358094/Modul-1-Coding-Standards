package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("123");
        product.setProductName("Kecap");
        product.setProductQuantity(5);
    }

    @Test
    void testCreate() {
        Mockito.when(productRepository.create(product)).thenReturn(product);
        Product result = productService.create(product);
        assertEquals(product, result);
        Mockito.verify(productRepository, Mockito.times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        Iterator<Product> iterator = productList.iterator();

        Mockito.when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    void testFindById() {
        Mockito.when(productRepository.findById("123")).thenReturn(product);
        Product result = productService.findById("123");
        assertEquals(product, result);
    }

    @Test
    void testUpdate() {
        Mockito.when(productRepository.edit(product)).thenReturn(product);
        productService.update(product);
        Mockito.verify(productRepository, Mockito.times(1)).edit(product);
    }

    @Test
    void testDeleteProductById() {
        Mockito.doNothing().when(productRepository).delete("123");
        productService.deleteProductById("123");
        Mockito.verify(productRepository, Mockito.times(1)).delete("123");
    }
}