package com.example.Examen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ExamenJUnitTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Juan", "juan@gmail.com");
    }

    @Test
    void testGetters() {
        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getName());
        assertEquals("juan@gmail.com", user.getEmail());
    }

    @Test
    void testSetters() {
        user.setName("Manolo");
        assertEquals("Manolo", user.getName());

        user.setId(2L);
        assertEquals(2L, user.getId());

        user.setEmail("juan@gmail.com");
        assertEquals("juan@gmail.com", user.getEmail());
    }

    // Test para equals: El objeto siempre debe ser igual a sí mismo
    @Test
    void testEquals_SameObject() {
        assertEquals(user, user); // Comparar el mismo objeto
    }

    // Test para equals: Dos objetos con el mismo email deben ser iguales
    @Test
    void testEquals_EqualObjects() {
        User user2 = new User(2L, "Juan", "juan@gmail.com");
        assertEquals(user, user2); // Mismo email, deben ser iguales
    }

    // Test para equals: Dos objetos con diferente email no deben ser iguales
    @Test
    void testEquals_DifferentObjects() {
        User user2 = new User(3L, "Maria", "maria@gmail.com");
        assertNotEquals(user, user2); // Diferente email, no deben ser iguales
    }

    // Test para hashCode: Dos objetos con el mismo email deben tener el mismo hashCode
    @Test
    void testHashCode_SameEmail() {
        User user2 = new User(2L, "Juan", "juan@gmail.com");
        assertEquals(user.hashCode(), user2.hashCode()); // Mismo email, deben tener el mismo hashCode
    }

    // Test para hashCode: Dos objetos con diferente email deben tener diferente hashCode
    @Test
    void testHashCode_DifferentEmail() {
        User user2 = new User(3L, "Maria", "maria@gmail.com");
        assertNotEquals(user.hashCode(), user2.hashCode()); // Diferente email, deben tener diferentes hashCode
    }
}
