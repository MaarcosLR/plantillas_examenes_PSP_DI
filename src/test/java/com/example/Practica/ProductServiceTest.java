// Define el paquete donde se encuentra la clase de prueba.
package com.example.Practica;

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para ejecutar código antes de cada prueba.
import org.junit.jupiter.api.Test; // Importa la anotación para definir pruebas unitarias.

import static org.junit.jupiter.api.Assertions.*; // Importa los métodos de aserción de JUnit.

class ProductServiceTest { // Define la clase de prueba para ProductService.

    private com.example.Practica.ProductService productService; // Declara una instancia de ProductService.

    // Configuración previa a cada prueba: se inicializa una nueva instancia de ProductService.
    @BeforeEach
    void setup() {
        productService = new com.example.Practica.ProductService();
    }

    // Test para verificar que un producto válido se agrega correctamente.
    @Test
    void testAddProductValid() {
        // Se agrega un producto con valores válidos.
        String result = productService.addProduct("Producto1", "10", "15.2", "USD");

        // Se espera que no haya errores (debe retornar null).
        assertNull(result, "El producto debería agregarse sin errores.");

        // Verifica que la lista de productos tiene exactamente 1 elemento.
        assertEquals(1, productService.getProducts().size());

        // Verifica que el formato del producto agregado es el esperado.
        String expected = "Nombre: Producto1, Cantidad: 10, Precio: 15.2 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }

    // Test para verificar que no se permite una cantidad en formato decimal.
    @Test
    void testAddProductInvalidQuantityDouble() {
        // Se intenta agregar un producto con una cantidad no válida (decimal).
        String result = productService.addProduct("Producto2", "15.2", "20", "EUR");

        // Se espera un mensaje de error indicando que la cantidad debe ser un número entero.
        assertEquals("La cantidad debe ser un número entero.", result);

        // Verifica que la lista de productos sigue vacía (el producto no se agregó).
        assertTrue(productService.getProducts().isEmpty());
    }

    // Test para verificar que no se permite agregar un producto con nombre vacío.
    @Test
    void testAddProductEmptyName() {
        // Se intenta agregar un producto con un nombre vacío (solo espacios en blanco).
        String result = productService.addProduct("   ", "10", "15", "USD");

        // Se espera un mensaje de error indicando que todos los campos deben estar completos.
        assertEquals("Todos los campos deben estar completos.", result);

        // Verifica que la lista de productos sigue vacía (el producto no se agregó).
        assertTrue(productService.getProducts().isEmpty());
    }

    // Test para verificar que un producto se puede actualizar correctamente.
    @Test
    void testUpdateProductValid() {
        // Se agrega un producto inicial.
        String result = productService.addProduct("Producto1", "5", "10", "USD");

        // Se espera que la operación de agregar no retorne errores.
        assertNull(result);

        // Se actualiza el producto en la posición 0 con nuevos valores.
        String updateResult = productService.updateProduct(0, "ProductoActualizado", "7", "12.5", "USD");

        // Se espera que la actualización se realice sin errores.
        assertNull(updateResult);

        // Se verifica que el producto fue actualizado correctamente con los nuevos valores.
        String expected = "Nombre: ProductoActualizado, Cantidad: 7, Precio: 12.5 USD";
        assertEquals(expected, productService.getProducts().get(0));
    }

    // Test para verificar que un producto se elimina correctamente.
    @Test
    void testDeleteProduct() {
        // Se agregan dos productos a la lista.
        productService.addProduct("Producto1", "5", "10", "USD");
        productService.addProduct("Producto2", "3", "20", "EUR");

        // Verifica que la lista de productos tiene dos elementos.
        assertEquals(2, productService.getProducts().size());

        // Se elimina el primer producto (índice 0).
        productService.deleteProduct(0);

        // Se verifica que ahora solo queda un producto en la lista.
        assertEquals(1, productService.getProducts().size());

        // Verifica que el producto restante es el segundo que se había agregado originalmente.
        String expected = "Nombre: Producto2, Cantidad: 3, Precio: 20 EUR";
        assertEquals(expected, productService.getProducts().get(0));
    }
}
