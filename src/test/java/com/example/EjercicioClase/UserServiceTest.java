package com.example.EjercicioClase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUserName() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User(1L, "Carlos")));
        assertEquals("Carlos", userService.getUserName(1L));
    }

    @Test
    void testUsuarioNoEncontrado() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertEquals("Usuario no encontrado", userService.getUserName(2L));
    }

    @Test
    void testUsuarioConNombreVacio() {
        User user = new User(3L, "Juan");
        when(userRepository.findById(3L)).thenReturn(Optional.of(user));
        assertNotEquals("", userService.getUserName(3L), "El usuario no debe estar vacío");
        assertNotNull(userService.getUserName(3L), "El usuario no debe estar vacío");
    }

    @Test
    void testFindByIdLlamadoCorrectamente() {
        userService.getUserName(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testExceptionEnUserRepository() {
        when(userRepository.findById(4L)).thenThrow(new RuntimeException("Error en base de datos"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserName(4L);
        });

        assertEquals("Error en base de datos", exception.getMessage());
    }

    @Test
    void testGetUserNameConIdNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserName(null);
        });

        assertEquals("El ID no puede ser null", exception.getMessage());
    }

    @Test
    void testGetUserNameConIdNegativo() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserName(-1L);
        });

        assertEquals("El ID debe ser positivo", exception.getMessage());
    }

    @Test
    void testUsuarioConNombreSoloEspacios() {
        when(userRepository.findById(5L)).thenReturn(Optional.of(new User(5L, "   ")));
        assertEquals("   ", userService.getUserName(5L));
    }

    @Test
    void testVerificarQueFindByIdNuncaRecibeNull() {
        try {
            userService.getUserName(null);
        } catch (Exception ignored) {}

        verify(userRepository, never()).findById(null);
    }

    @Test
    void testFindByIdSoloSeLlamaUnaVez() {
        User user = new User(1L, "Carlos");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.getUserName(1L);
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testUserServiceManejoDeExcepcion() {
        when(userRepository.findById(10L)).thenThrow(new RuntimeException("Error desconocido"));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            userService.getUserName(10L);
        });

        assertEquals("Error desconocido", exception.getMessage());
    }


}