package com.example.EjercicioClase; // Define el paquete donde se encuentra la clase de prueba.

import static org.junit.jupiter.api.Assertions.*; // Importa los métodos de aserción de JUnit.

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para ejecutar código antes de cada prueba.
import org.junit.jupiter.api.Test; // Importa la anotación para definir pruebas unitarias.

class UserTest { // Define la clase de prueba para la clase User.

    private User user; // Declara un objeto de tipo User para las pruebas.

    @BeforeEach
    void setUp() {
        user = new User(1L, "Juan"); // Inicializa un usuario antes de cada prueba.
    }

    @Test
    void testGettersAndSetters() {
        // Verificar valores iniciales del usuario
        assertEquals(1L, user.getId()); // Verifica que el ID inicial es 1.
        assertEquals("Juan", user.getName()); // Verifica que el nombre inicial es "Juan".

        // Modificar valores y verificar actualización
        user.setName("Carlos"); // Cambia el nombre a "Carlos".
        assertEquals("Carlos", user.getName()); // Verifica que el nombre se actualizó correctamente.

        user.setId(2L); // Cambia el ID a 2.
        assertEquals(2L, user.getId()); // Verifica que el ID se actualizó correctamente.
    }

    @Test
    void testUserNameNoVacio() {
        // Verifica que el nombre del usuario no está vacío.
        assertFalse(user.getName().isEmpty(), "El nombre del usuario no debe estar vacío");
    }

    @Test
    void testUserIdNull() {
        User userNullId = new User(1L, "Pedro"); // Crea un usuario con ID 1 y nombre "Pedro".
        // ERROR: La prueba está mal escrita, ya que se le asigna un ID y luego se espera que sea null.
        assertEquals(null, userNullId.getId(), "El ID no puede ser nulo");
    }

    @Test
    void testUserNameNull() {
        User userNullName = new User(3L, null); // Crea un usuario con ID 3 y nombre null.

        // Verifica que el nombre no es null.
        assertNotNull(userNullName.getName(), "El nombre del usuario no debería ser null");

        // Verifica que el nombre no sea la cadena "null".
        assertNotEquals("null", userNullName.getName(), "El nombre del usuario no debe ser la cadena 'null'");
    }

    @Test
    void testUserNameSoloEspacios() {
        user.setName("   "); // Asigna un nombre que solo contiene espacios en blanco.
        // Verifica que, después de eliminar espacios, el nombre no esté vacío.
        assertFalse(user.getName().trim().isEmpty(), "El nombre del usuario no debe ser solo espacios en blanco");
    }

    @Test
    void testSetUserNameNull() {
        user.setName(null); // Asigna null al nombre del usuario.
        // Verifica que el nombre se haya convertido en null.
        assertNull(user.getName(), "El nombre debería poder ser null si se asigna null");
    }

    @Test
    void testSetUserIdNull() {
        user.setId(null); // Asigna null al ID del usuario.
        // Verifica que el ID se haya convertido en null.
        assertNull(user.getId(), "El ID debería poder ser null si se asigna null");
    }

    @Test
    void testUserNameCaracteresEspeciales() {
        user.setName("J@v!er_123"); // Asigna un nombre con caracteres especiales.
        // Verifica que el nombre se asignó correctamente.
        assertEquals("J@v!er_123", user.getName(), "El nombre del usuario debería aceptar caracteres especiales");
    }

    @Test
    void testUserNameLongitudMaxima() {
        String nombreLargo = "EsteEsUnNombreExcesivamenteLargoParaUnUsuarioYDeberiaSerCortado"; // Define un nombre largo.
        user.setName(nombreLargo); // Asigna el nombre largo al usuario.
        // Verifica que el nombre se asignó correctamente (asumiendo que no hay una validación de longitud).
        assertEquals(nombreLargo, user.getName(), "El nombre del usuario debería permitir nombres largos");
    }
}
