package com.example.EjercicioClase;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(1L, "Juan");
    }

    @Test
    void testGettersAndSetters() {

        // Verificar valores iniciales
        assertEquals(1L, user.getId());
        assertEquals("Juan", user.getName());

        // Modificar valores y verificar actualización
        user.setName("Carlos");
        assertEquals("Carlos", user.getName());

        user.setId(2L);
        assertEquals(2L, user.getId());
    }

    @Test
    void testUserNameNoVacio() {
        assertFalse(user.getName().isEmpty(), "El nombre del usuario no debe estar vacío");
    }

    @Test
    void testUserIdNull() {
        User userNullId = new User(1L, "Pedro");
        assertEquals(null, userNullId.getId(), "El ID no puede ser nulo");
    }

    @Test
    void testUserNameNull() {
        User userNullName = new User(3L, null);

        // Verificar que el nombre no es null
        assertNotNull(userNullName.getName(), "El nombre del usuario no debería ser null");

        // Verificar que el nombre no sea la cadena "null"
        assertNotEquals("null", userNullName.getName(), "El nombre del usuario no debe ser la cadena 'null'");
    }


    @Test
    void testUserNameSoloEspacios() {
        user.setName("   ");
        assertFalse(user.getName().trim().isEmpty(), "El nombre del usuario no debe ser solo espacios en blanco");
    }

    @Test
    void testSetUserNameNull() {
        user.setName(null);
        assertNull(user.getName(), "El nombre debería poder ser null si se asigna null");
    }

    @Test
    void testSetUserIdNull() {
        user.setId(null);
        assertNull(user.getId(), "El ID debería poder ser null si se asigna null");
    }

    @Test
    void testUserNameCaracteresEspeciales() {
        user.setName("J@v!er_123");
        assertEquals("J@v!er_123", user.getName(), "El nombre del usuario debería aceptar caracteres especiales");
    }

    @Test
    void testUserNameLongitudMaxima() {
        String nombreLargo = "EsteEsUnNombreExcesivamenteLargoParaUnUsuarioYDeberiaSerCortado";
        user.setName(nombreLargo);
        assertEquals(nombreLargo, user.getName(), "El nombre del usuario debería permitir nombres largos");
    }

}

