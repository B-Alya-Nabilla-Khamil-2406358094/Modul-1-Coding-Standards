package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    private ProductController productController;
    private ProductService productService;
    private Model model;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        model = mock(Model.class);

        productController = new ProductController(productService);
    }

    @Test
    void testCreateProductPage() {
        String viewName = productController.createProductPage(model);
        assertEquals("createProduct", viewName);
        Mockito.verify(model).addAttribute(Mockito.eq("product"), any(Product.class));
    }

    @Test
    void testCreateProductPost() {
        Product product = new Product();
        String viewName = productController.createProductPost(product, model);
        assertEquals("redirect:list", viewName);
        Mockito.verify(productService).create(product);
    }

    @Test
    void testProductListPage() {
        List<Product> products = new ArrayList<>();
        Mockito.when(productService.findAll()).thenReturn(products);

        String viewName = productController.productListPage(model);

        assertEquals("productList", viewName);
        Mockito.verify(model).addAttribute("products", products);
    }

    @Test
    void testEditProductPage() {
        Product product = new Product();
        Mockito.when(productService.findById("123")).thenReturn(product);

        String viewName = productController.editProductPage("123", model);

        assertEquals("editProduct", viewName);
        Mockito.verify(model).addAttribute("product", product);
    }

    @Test
    void testEditProductPost() {
        Product product = new Product();
        String viewName = productController.editProductPost(product);

        assertEquals("redirect:list", viewName);
        Mockito.verify(productService).update(product);
    }

    @Test
    void testDeleteProduct() {
        String viewName = productController.deleteProduct("123");

        assertEquals("redirect:/product/list", viewName);
        Mockito.verify(productService).deleteProductById("123");
    }
}