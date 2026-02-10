package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testEditProductPositive() {
        // Setup
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        // Action: Edit data
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId()); // Ambil ID dari objek setup
        updatedProduct.setProductName("Sampo Cap Usep");
        updatedProduct.setProductQuantity(50);
        productRepository.edit(updatedProduct);

        // Verify
        Product result = productRepository.findById(product.getProductId());
        assertEquals("Sampo Cap Usep", result.getProductName());
        assertEquals(50, result.getProductQuantity());
    }

    @Test
    void testEditProductNegative() {

        Product product = new Product();
        product.setProductId("id-asli");
        product.setProductName("Produk Asli");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product fakeProduct = new Product();
        fakeProduct.setProductId("id-palsu");
        fakeProduct.setProductName("Produk Palsu");
        fakeProduct.setProductQuantity(50);
        Product result = productRepository.edit(fakeProduct);

        assertNull(result);
    }

    @Test
    void testDeleteProductPositive() {
        // Setup
        Product product = new Product();
        product.setProductId("id-hapus");
        product.setProductName("Mau Dihapus");
        product.setProductQuantity(10);
        productRepository.create(product);

        // Action: Hapus
        productRepository.delete("id-hapus");

        assertNull(productRepository.findById("id-hapus"));

        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testDeleteProductNegative() {

        Product product = new Product();
        product.setProductId("id-tetap");
        product.setProductName("Jangan Dihapus");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete("id-salah");

        assertNotNull(productRepository.findById("id-tetap"));
    }

    @Test
    void testCreateProductWithNoId_ShouldGenerateUuid() {
        // Setup: Produk tanpa ID
        Product product = new Product();
        product.setProductName("Produk Tanpa ID");
        product.setProductQuantity(5);

        // Action
        productRepository.create(product);

        // Verify
        assertNotNull(product.getProductId()); // Pastikan ID ter-generate (tidak null)
        assertFalse(product.getProductId().isEmpty()); // Pastikan ID tidak kosong
    }
}