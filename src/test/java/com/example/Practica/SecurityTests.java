// Define el paquete donde se encuentra la clase de prueba.
package com.example.Practica;

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para ejecutar código antes de cada prueba.
import org.junit.jupiter.api.Test; // Importa la anotación para definir pruebas unitarias.

import static org.junit.jupiter.api.Assertions.*; // Importa los métodos de aserción de JUnit.

class SecurityTests { // Define la clase de prueba para validar reglas de seguridad en ProductService.

    private com.example.Practica.ProductService productService; // Instancia de ProductService para realizar pruebas.

    // Configuración previa a cada prueba: se inicializa una nueva instancia de ProductService.
    @BeforeEach
    void setup() {
        productService = new com.example.Practica.ProductService();
    }

    // Test para verificar que los espacios en el nombre del producto se eliminan correctamente.
    @Test
    void testNameTrimming() {
        // Se intenta agregar un producto con espacios adicionales en el nombre.
        String result = productService.addProduct("  ProductoSeguro  ", "10", "15", "USD");

        // Se espera que el producto se agregue sin errores.
        assertNull(result, "El producto debería agregarse correctamente tras eliminar espacios.");

        // Se espera que el nombre haya sido limpiado de espacios en blanco.
        String expected = "Nombre: ProductoSeguro, Cantidad: 10, Precio: 15 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }

    // Test para verificar que la cantidad no acepta valores decimales.
    @Test
    void testNonIntegerQuantityValidation() {
        // Se intenta agregar un producto con cantidad decimal (inválido).
        String result = productService.addProduct("Producto", "5.5", "20", "USD");

        // Se espera un mensaje de error indicando que la cantidad debe ser un número entero.
        assertEquals("La cantidad debe ser un número entero.", result);
    }

    // Test para verificar que la cantidad no puede ser negativa.
    @Test
    void testNegativeQuantityValidation() {
        // Se intenta agregar un producto con cantidad negativa.
        String result = productService.addProduct("Producto", "-3", "20", "USD");

        // Se espera un mensaje de error indicando que la cantidad no puede ser negativa.
        assertEquals("La cantidad no puede ser negativa.", result);
    }

    // Test para verificar que el precio no puede ser negativo.
    @Test
    void testNegativePriceValidation() {
        // Se intenta agregar un producto con precio negativo.
        String result = productService.addProduct("Producto", "5", "-10", "USD");

        // Se espera un mensaje de error indicando que el precio no puede ser negativo.
        assertEquals("El precio no puede ser negativo.", result);
    }

    // Test para verificar que los precios con coma en lugar de punto se aceptan correctamente.
    @Test
    void testPriceParsingWithComma() {
        // Se intenta agregar un producto con un precio que usa coma como separador decimal.
        String result = productService.addProduct("Producto", "5", "15,75", "USD");

        // Se espera que el producto se agregue sin errores.
        assertNull(result, "El precio con coma debe ser aceptado.");

        // Se verifica que el formato del producto agregado es el esperado.
        String expected = "Nombre: Producto, Cantidad: 5, Precio: 15,75 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }
}
