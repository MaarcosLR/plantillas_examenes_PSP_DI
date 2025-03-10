package com.example.Examen; // Define el paquete donde se encuentra la clase de prueba.

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para ejecutar código antes de cada prueba.
import org.junit.jupiter.api.Test; // Importa la anotación para definir pruebas unitarias.

import static org.junit.jupiter.api.Assertions.*; // Importa los métodos de aserción de JUnit.

public class ExamenJUnitTest { // Define la clase de prueba para la clase User.

    private User user; // Declara un objeto de tipo User para las pruebas.

    @BeforeEach
    void setUp() {
        // Inicializa un usuario antes de cada prueba con ID 1, nombre "Juan" y correo "juan@gmail.com".
        user = new User(1L, "Juan", "juan@gmail.com");
    }

    @Test
    void testGetters() {
        // Verifica que los valores iniciales del usuario sean correctos.
        assertEquals(1L, user.getId()); // Verifica que el ID es 1.
        assertEquals("Juan", user.getName()); // Verifica que el nombre es "Juan".
        assertEquals("juan@gmail.com", user.getEmail()); // Verifica que el email es "juan@gmail.com".
    }

    @Test
    void testSetters() {
        // Modifica los valores del usuario y verifica que se actualizan correctamente.
        user.setName("Manolo"); // Cambia el nombre a "Manolo".
        assertEquals("Manolo", user.getName()); // Verifica que el nombre se actualizó.

        user.setId(2L); // Cambia el ID a 2.
        assertEquals(2L, user.getId()); // Verifica que el ID se actualizó.

        user.setEmail("juan@gmail.com"); // Cambia el email (mismo valor).
        assertEquals("juan@gmail.com", user.getEmail()); // Verifica que el email se mantiene igual.
    }

    // Test para equals: El objeto siempre debe ser igual a sí mismo.
    @Test
    void testEquals_SameObject() {
        assertEquals(user, user); // Comparar el mismo objeto, debe ser igual a sí mismo.
    }

    // Test para equals: Dos objetos con el mismo email deben considerarse iguales.
    @Test
    void testEquals_EqualObjects() {
        User user2 = new User(2L, "Juan", "juan@gmail.com"); // Crea otro usuario con el mismo email.
        assertEquals(user, user2); // Como el email es el mismo, deben considerarse iguales.
    }

    // Test para equals: Dos objetos con diferente email no deben ser iguales.
    @Test
    void testEquals_DifferentObjects() {
        User user2 = new User(3L, "Maria", "maria@gmail.com"); // Crea otro usuario con email diferente.
        assertNotEquals(user, user2); // Como el email es diferente, no deben ser iguales.
    }

    // Test para hashCode: Dos objetos con el mismo email deben tener el mismo hashCode.
    @Test
    void testHashCode_SameEmail() {
        User user2 = new User(2L, "Juan", "juan@gmail.com"); // Crea otro usuario con el mismo email.
        assertEquals(user.hashCode(), user2.hashCode()); // Como el email es el mismo, deben tener el mismo hashCode.
    }

    // Test para hashCode: Dos objetos con diferente email deben tener diferente hashCode.
    @Test
    void testHashCode_DifferentEmail() {
        User user2 = new User(3L, "Maria", "maria@gmail.com"); // Crea otro usuario con email diferente.
        assertNotEquals(user.hashCode(), user2.hashCode()); // Como el email es diferente, deben tener diferente hashCode.
    }
}
