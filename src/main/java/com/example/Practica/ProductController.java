package com.example.Practica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;

import java.util.Arrays;
import java.util.List;

public class ProductController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtQuantity;
    @FXML
    private ComboBox<String> currencyComboBox; // ComboBox para la moneda
    @FXML
    private ListView<String> productList;

    private ProductService productService = new ProductService();
    private int selectedIndex = -1; // Índice del producto seleccionado

    public void initialize() {
        // Inicializar el ComboBox con los tipos de monedas
        List<String> currencies = Arrays.asList("USD", "EUR", "GBP", "JPY", "AUD", "CAD", "CHF", "CNY", "SEK", "NZD");
        currencyComboBox.getItems().addAll(currencies);
        currencyComboBox.getSelectionModel().selectFirst(); // Seleccionar la primera moneda por defecto
    }

    public void addProduct(ActionEvent actionEvent) {
        String name = txtName.getText().trim(); // Eliminar espacios
        String price = txtPrice.getText();
        String quantity = txtQuantity.getText();
        String currency = currencyComboBox.getValue(); // Obtener la moneda seleccionada

        if (name.isEmpty() || price.isEmpty() || quantity.isEmpty()) {
            showAlert("Error", "Todos los campos deben estar completos.");
            return;
        }

        String errorMessage = productService.addProduct(name, quantity, price, currency);
        if (errorMessage == null) {
            updateProductList();
            clearFields();
        } else {
            showAlert("Error", errorMessage);
        }
    }

    private void updateProductList() {
        productList.getItems().clear();
        productList.getItems().addAll(productService.getProducts());
    }

    public void editProduct(ActionEvent actionEvent) {
        selectedIndex = productList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String selectedProduct = productList.getItems().get(selectedIndex);
            String[] productDetails = selectedProduct.split(", ");

            // Extrae los detalles del producto
            String name = productDetails[0].substring(8); // "Nombre: "
            String quantity = productDetails[1].substring(10); // "Cantidad: "
            String priceWithCurrency = productDetails[2].substring(8); // "Precio: "
            String[] priceParts = priceWithCurrency.split(" "); // Separar precio y moneda
            String price = priceParts[0]; // Obtener solo el precio
            String currency = priceParts.length > 1 ? priceParts[1] : ""; // Obtener la moneda si existe

            // Establece los campos de texto con los detalles del producto seleccionado
            txtName.setText(name);
            txtQuantity.setText(quantity);
            txtPrice.setText(price);
            currencyComboBox.setValue(currency); // Establecer la moneda seleccionada
        } else {
            showAlert("Error", "Seleccione un producto para editar.");
        }
    }

    public void updateProduct(ActionEvent actionEvent) {
        selectedIndex = productList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            String name = txtName.getText().trim(); // Eliminar espacios
            String price = txtPrice.getText();
            String quantity = txtQuantity.getText();
            String currency = currencyComboBox.getValue(); // Obtener la moneda seleccionada

            String errorMessage = productService.updateProduct(selectedIndex, name, quantity, price, currency);
            if (errorMessage == null) {
                updateProductList();
                clearFields();
            } else {
                showAlert("Error", errorMessage);
            }
        } else {
            showAlert("Error", "Seleccione un producto para actualizar.");
        }
    }

    public void deleteProduct(ActionEvent actionEvent) {
        selectedIndex = productList.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            productService.deleteProduct(selectedIndex);
            updateProductList();
            clearFields();
        } else {
            showAlert("Error", "Seleccione un producto para eliminar.");
        }
    }

    private void clearFields() {
        txtName.clear();
        txtPrice.clear();
        txtQuantity.clear();
        currencyComboBox.getSelectionModel().selectFirst(); // Reiniciar selección de moneda
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
