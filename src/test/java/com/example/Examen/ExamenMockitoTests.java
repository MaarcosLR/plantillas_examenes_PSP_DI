package com.example.Examen;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ExamenMockitoTests {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user = new User(1L, "Juan", "juan@gmail.com");
    }

    @Test
    void tesCreateUser() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.createUser(user));
        assertEquals("El correo electrónico ya está registrado", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testGetUserById_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.getUserById(1L));
        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());
    }

    @Test
    void testGetUserById_Found() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        User foundUser = userService.getUserById(1L);
        assertEquals(user, foundUser);
    }

    @Test
    void testDeleteUser_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));
        assertEquals("Usuario no encontrado con ID: 1", exception.getMessage());
        verify(userRepository, never()).deleteById(anyLong());
    }
}
