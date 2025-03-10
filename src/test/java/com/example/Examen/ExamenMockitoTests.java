package com.example.Examen; // Define el paquete donde se encuentra la clase de prueba.

import org.junit.jupiter.api.BeforeEach; // Importa la anotación para ejecutar código antes de cada prueba.
import org.junit.jupiter.api.Test; // Importa la anotación para definir pruebas unitarias.
import org.mockito.InjectMocks; // Importa la anotación para inyectar mocks en la clase de prueba.
import org.mockito.Mock; // Importa la anotación para definir un mock.
import org.mockito.MockitoAnnotations; // Importa la clase para inicializar los mocks.

import java.util.Optional; // Importa la clase Optional para manejar valores opcionales.

import static org.junit.jupiter.api.Assertions.*; // Importa los métodos de aserción de JUnit.
import static org.mockito.ArgumentMatchers.any; // Importa ArgumentMatchers para cualquier objeto de tipo User.
import static org.mockito.Mockito.*; // Importa los métodos de Mockito para simular comportamientos.

public class ExamenMockitoTests { // Define la clase de prueba para UserService con Mockito.

    @Mock
    private UserRepository userRepository; // Crea un mock de UserRepository.

    @InjectMocks
    private UserService userService; // Inyecta el mock de UserRepository en UserService.

    private User user; // Declara un objeto de tipo User para usar en las pruebas.

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this); // Inicializa los mocks antes de cada prueba.
        user = new User(1L, "Juan", "juan@gmail.com"); // Crea un usuario de prueba.
    }

    // Test para verificar que no se puede crear un usuario con un email ya registrado.
    @Test
    void tesCreateUser() {
        // Simula que el repositorio ya tiene un usuario con el mismo email.
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Verifica que al intentar crear un usuario con un email duplicado, se lanza una excepción.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("El correo electrónico ya está registrado", exception.getMessage());

        // Verifica que el método save nunca es llamado, ya que no se debe guardar un usuario duplicado.
        verify(userRepository, never()).save(any(User.class));
    }

    // Test para verificar que obtener un usuario por ID que no existe lanza una excepción.
    @Test
    void testGetUserById_NotFound() {
        // Simula que el repositorio no encuentra un usuario con el ID proporcionado.
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica que se lanza una excepción cuando el usuario no existe.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());
    }

    // Test para verificar que obtener un usuario por ID que sí existe devuelve el usuario correcto.
    @Test
    void testGetUserById_Found() {
        // Simula que el repositorio encuentra un usuario con el ID proporcionado.
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Llama al método getUserById para obtener el usuario.
        User foundUser = userService.getUserById(1L);

        // Verifica que el usuario encontrado es el mismo que el esperado.
        assertEquals(user, foundUser);
    }

    // Test para verificar que eliminar un usuario existente llama al método deleteById.
    @Test
    void testDeleteUser_UserFound() {
        // Simula que el repositorio encuentra el usuario antes de eliminarlo.
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Llama al método deleteUser para eliminar el usuario.
        userService.deleteUser(1L);

        // Verifica que el método deleteById se llamó exactamente una vez con el ID correcto.
        verify(userRepository, times(1)).deleteById(1L);
    }

    // Test para verificar que intentar eliminar un usuario inexistente lanza una excepción.
    @Test
    void testDeleteUser_UserNotFound() {
        // Simula que el repositorio no encuentra un usuario con el ID proporcionado.
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        // Verifica que al intentar eliminar un usuario inexistente, se lanza una excepción.
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));

        // Verifica que el mensaje de la excepción es el esperado.
        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());

        // Verifica que el método deleteById nunca es llamado, ya que el usuario no existe.
        verify(userRepository, never()).deleteById(anyLong());
    }
}
