package com.example.EjercicioClase;

import java.util.Optional;

public class UserService {
    private final UserRepository userRepository;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUserName(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get().getName();
        } else {
            return "Usuario no encontrado";
        }
    }
}

