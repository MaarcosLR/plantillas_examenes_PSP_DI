// src/testGettersAndSetters/java/com/example/stock/SecurityTests.java
package com.example.Practica;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTests {

    private com.example.Practica.ProductService productService;

    @BeforeEach
    void setup() {
        productService = new com.example.Practica.ProductService();
    }

    @Test
    void testNameTrimming() {
        // Se deben eliminar espacios al principio y al final del nombre.
        String result = productService.addProduct("  ProductoSeguro  ", "10", "15", "USD");
        assertNull(result, "El producto debería agregarse correctamente tras eliminar espacios.");
        String expected = "Nombre: ProductoSeguro, Cantidad: 10, Precio: 15 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }

    @Test
    void testNonIntegerQuantityValidation() {
        // La cantidad con formato decimal debe fallar.
        String result = productService.addProduct("Producto", "5.5", "20", "USD");
        assertEquals("La cantidad debe ser un número entero.", result);
    }

    @Test
    void testNegativeQuantityValidation() {
        // La cantidad negativa debe fallar.
        String result = productService.addProduct("Producto", "-3", "20", "USD");
        assertEquals("La cantidad no puede ser negativa.", result);
    }

    @Test
    void testNegativePriceValidation() {
        // El precio negativo debe fallar.
        String result = productService.addProduct("Producto", "5", "-10", "USD");
        assertEquals("El precio no puede ser negativo.", result);
    }

    @Test
    void testPriceParsingWithComma() {
        // El precio escrito con coma debe ser aceptado.
        String result = productService.addProduct("Producto", "5", "15,75", "USD");
        assertNull(result, "El precio con coma debe ser aceptado.");
        String expected = "Nombre: Producto, Cantidad: 5, Precio: 15,75 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }
}
