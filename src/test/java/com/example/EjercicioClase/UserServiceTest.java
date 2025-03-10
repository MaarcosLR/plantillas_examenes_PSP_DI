package com.example.EjercicioClase; // Define el paquete donde se encuentra la clase de prueba.

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para ejecutar código antes de cada prueba.
import org.junit.jupiter.api.Test; // Importa la anotación para definir pruebas unitarias.
import org.mockito.InjectMocks; // Importa la anotación para inyectar mocks en la clase de prueba.
import org.mockito.Mock; // Importa la anotación para crear objetos simulados (mocks).
import org.mockito.MockitoAnnotations; // Importa la clase para inicializar anotaciones de Mockito.
import java.util.Optional; // Importa la clase Optional para manejar valores opcionales.
import static org.junit.jupiter.api.Assertions.*; // Importa métodos de aserción de JUnit.
import static org.mockito.Mockito.*; // Importa métodos de Mockito para simulaciones y verificaciones.

class UserServiceTest { // Define la clase de prueba para UserService.

    @Mock
    private UserRepository userRepository; // Crea un objeto simulado (mock) de UserRepository.

    @InjectMocks
    private UserService userService; // Inyecta el mock de UserRepository en UserService.

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this); // Inicializa los mocks antes de cada prueba.
    }

    @Test
    void testGetUserName() {
        // Simula que el repositorio devuelve un usuario con ID 1 y nombre "Carlos".
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "Carlos")));
        // Verifica que el nombre devuelto por el servicio es "Carlos".
        assertEquals("Carlos", userService.getUserName(1L));
    }

    @Test
    void testUsuarioNoEncontrado() {
        // Simula que el repositorio no encuentra un usuario con ID 2.
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        // Verifica que el mensaje devuelto es "Usuario no encontrado".
        assertEquals("Usuario no encontrado", userService.getUserName(2L));
    }

    @Test
    void testUsuarioConNombreVacio() {
        User user = new User(3L, "Juan"); // Crea un usuario con ID 3 y nombre "Juan".
        when(userRepository.findById(3L)).thenReturn(Optional.of(user)); // Simula la búsqueda en el repositorio.

        // Verifica que el nombre no está vacío.
        assertNotEquals("", userService.getUserName(3L), "El usuario no debe estar vacío");
        // Verifica que el nombre no es null.
        assertNotNull(userService.getUserName(3L), "El usuario no debe estar vacío");
    }

    @Test
    void testFindByIdLlamadoCorrectamente() {
        userService.getUserName(1L); // Llama al método getUserName con ID 1.
        verify(userRepository, times(1)).findById(1L); // Verifica que findById(1L) se llamó una vez.
    }

    @Test
    void testExceptionEnUserRepository() {
        // Simula que el repositorio lanza una excepción cuando se busca el usuario con ID 4.
        when(userRepository.findById(4L)).thenThrow(new RuntimeException("Error en base de datos"));

        // Verifica que se lanza una excepción al llamar getUserName(4L).
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserName(4L);
        });

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("Error en base de datos", exception.getMessage());
    }

    @Test
    void testGetUserNameConIdNull() {
        // Verifica que se lanza una IllegalArgumentException si el ID es null.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserName(null);
        });

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("El ID no puede ser null", exception.getMessage());
    }

    @Test
    void testGetUserNameConIdNegativo() {
        // Verifica que se lanza una IllegalArgumentException si el ID es negativo.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserName(-1L);
        });

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("El ID debe ser positivo", exception.getMessage());
    }

    @Test
    void testUsuarioConNombreSoloEspacios() {
        // Simula que el repositorio devuelve un usuario con solo espacios en el nombre.
        when(userRepository.findById(5L)).thenReturn(Optional.of(new User(5L, "   ")));
        // Verifica que el nombre devuelto es exactamente el mismo (espacios incluidos).
        assertEquals("   ", userService.getUserName(5L));
    }

    @Test
    void testVerificarQueFindByIdNuncaRecibeNull() {
        try {
            userService.getUserName(null); // Llama al método con un ID null.
        } catch (Exception ignored) {} // Captura la excepción sin hacer nada.

        // Verifica que findById nunca fue llamado con un argumento null.
        verify(userRepository, never()).findById(null);
    }

    @Test
    void testFindByIdSoloSeLlamaUnaVez() {
        User user = new User(1L, "Carlos"); // Crea un usuario con ID 1 y nombre "Carlos".
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // Simula la búsqueda en el repositorio.

        userService.getUserName(1L); // Llama al método getUserName con ID 1.
        verify(userRepository, times(1)).findById(1L); // Verifica que findById(1L) se llamó solo una vez.
    }

    @Test
    void testUserServiceManejoDeExcepcion() {
        // Simula que el repositorio lanza una excepción con el mensaje "Error desconocido".
        when(userRepository.findById(10L)).thenThrow(new RuntimeException("Error desconocido"));

        // Verifica que se lanza una excepción al llamar getUserName(10L).
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserName(10L);
        });

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("Error desconocido", exception.getMessage());
    }
}
