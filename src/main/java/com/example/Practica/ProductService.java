package com.example.Practica;

import java.util.ArrayList;

public class ProductService {
    private ArrayList<String> products = new ArrayList<>();

    public String addProduct(String name, String quantity, String price, String currency) {
        String validationError = validateProduct(name, quantity, price);
        if (validationError == null) {
            products.add("Nombre: " + name + ", Cantidad: " + quantity + ", Precio: " + price + " " + currency);
            return null; // Sin errores
        }
        return validationError; // Devuelve el mensaje de error
    }

    public String updateProduct(int index, String name, String quantity, String price, String currency) {
        String validationError = validateProduct(name, quantity, price);
        if (validationError == null) {
            products.set(index, "Nombre: " + name + ", Cantidad: " + quantity + ", Precio: " + price + " " + currency);
            return null; // Sin errores
        }
        return validationError; // Devuelve el mensaje de error
    }

    public void deleteProduct(int index) {
        products.remove(index);
    }

    public ArrayList<String> getProducts() {
        return products;
    }

    private String validateProduct(String name, String quantity, String price) {
        // Validaciones de los campos
        if (name.isEmpty() || quantity.isEmpty() || price.isEmpty()) {
            return "Todos los campos deben estar completos.";
        }

        try {
            int qty = Integer.parseInt(quantity);
            if (qty < 0) {
                return "La cantidad no puede ser negativa.";
            }
        } catch (NumberFormatException e) {
            return "La cantidad debe ser un número entero.";
        }

        try {
            double prc = Double.parseDouble(price.replace(",", "."));
            if (prc < 0) {
                return "El precio no puede ser negativo.";
            }
        } catch (NumberFormatException e) {
            return "El precio debe ser un número válido.";
        }

        return null; // Sin errores
    }
}
