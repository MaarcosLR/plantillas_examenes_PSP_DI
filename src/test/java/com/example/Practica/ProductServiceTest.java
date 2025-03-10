// src/test/java/com/example/stock/ProductServiceTest.java
package com.example.Practica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceTest {

    private com.example.Practica.ProductService productService;

    @BeforeEach
    void setup() {
        productService = new com.example.Practica.ProductService();
    }

    @Test
    void testAddProductValid() {
        // Caso válido: se debe agregar el producto sin errores.
        String result = productService.addProduct("Producto1", "10", "15.2", "USD");
        assertNull(result, "El producto debería agregarse sin errores.");
        assertEquals(1, productService.getProducts().size());
        String expected = "Nombre: Producto1, Cantidad: 10, Precio: 15.2 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }

    @Test
    void testAddProductInvalidQuantityDouble() {
        // Caso error: la cantidad en formato decimal no es válida.
        String result = productService.addProduct("Producto2", "15.2", "20", "EUR");
        assertEquals("La cantidad debe ser un número entero.", result);
        assertTrue(productService.getProducts().isEmpty());
    }

    @Test
    void testAddProductEmptyName() {
        // Caso error: nombre vacío (incluso tras hacer trim)
        String result = productService.addProduct("   ", "10", "15", "USD");
        assertEquals("Todos los campos deben estar completos.", result);
        assertTrue(productService.getProducts().isEmpty());
    }

    @Test
    void testUpdateProductValid() {
        // Agregar un producto y luego actualizarlo correctamente.
        String result = productService.addProduct("Producto1", "5", "10", "USD");
        assertNull(result);
        String updateResult = productService.updateProduct(0, "ProductoActualizado", "7", "12.5", "USD");
        assertNull(updateResult);
        String expected = "Nombre: ProductoActualizado, Cantidad: 7, Precio: 12.5 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }

    @Test
    void testDeleteProduct() {
        // Agregar dos productos y luego eliminar uno.
        productService.addProduct("Producto1", "5", "10", "USD");
        productService.addProduct("Producto2", "3", "20", "EUR");
        assertEquals(2, productService.getProducts().size());
        productService.deleteProduct(0);
        assertEquals(1, productService.getProducts().size());
        String expected = "Nombre: Producto2, Cantidad: 3, Precio: 20 EUR";
        assertEquals(expected, productService.getProducts().get(0));
    }
}
