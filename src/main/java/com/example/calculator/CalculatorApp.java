package com.example.calculator;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CalculatorApp extends Application {

    @FXML
    private TextField display;

    @FXML
    private TextField inputField;

    private String currentInput = "";
    private String currentOperation = "";
    private double result = 0.0;

    private List<String> calculationHistory = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        VBox root = loader.load();
        instance = this;

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Калькулятор");
        primaryStage.show();
    }

    @FXML
    private void handleDigit(javafx.event.ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        currentInput += sourceButton.getText();
        inputField.setText(currentInput);

    }

    @FXML
    private void handleOperation(javafx.event.ActionEvent event) {
        Button sourceButton = (Button) event.getSource();
        currentOperation = sourceButton.getText();
        result = Double.parseDouble(currentInput);
        clearInput();
    }

    @FXML
    private void handleDecimal() {
        if (!currentInput.contains(".")) {
            currentInput += ".";
            inputField.setText(currentInput);
        }
    }

    @FXML
    private void handleEquals() {
        if (!currentOperation.isEmpty()) {
            double secondOperand = Double.parseDouble(currentInput);
            String historyEntry = String.format("%s %s %s = %s", result, currentOperation, secondOperand, result + secondOperand);

            switch (currentOperation) {
                case "+":
                    result += secondOperand;
                    break;
                case "-":
                    result -= secondOperand;
                    break;
                case "*":
                    result *= secondOperand;
                    break;
                case "/":
                    if (secondOperand != 0) {
                        result /= secondOperand;
                    } else {
                        inputField.setText("Ошибка");
                        return;
                    }
                    break;
            }
            display.setText(Double.toString(result));
            clearInput();

            calculationHistory.add(historyEntry);
            updateHistoryTextArea();
        }
    }

    @FXML
    private void clearCalculator() {
        clearInput();
        result = 0.0;
        display.clear();
        textFieldA.clear();
        textFieldB.clear();
        textFieldC.clear();
    }

    @FXML
    private void clearAll() {
        clearCalculator();
    }

    private void clearInput() {
        currentInput = "";
        inputField.clear();
    }

    @FXML
    private TextField textFieldA;

    @FXML
    private TextField textFieldB;

    @FXML
    private TextField textFieldC;

    @FXML
    private void handleDiscriminantButton(ActionEvent actionEvent) {
        try {
            double a = parseCoefficient(textFieldA);
            double b = parseCoefficient(textFieldB);
            double c = parseCoefficient(textFieldC);

            double discriminant = calculateDiscriminant(a, b, c);

            String resultMessage = "Дискриминант: " + discriminant;
            showResultDialog("Результат", resultMessage);
        } catch (NumberFormatException e) {
            showErrorDialog("Ошибка", "Пожалуйста, введите корректные числовые значения для коэффициентов.");
        }
    }

    private double parseCoefficient(TextField textField) {
        return Double.parseDouble(textField.getText());
    }

    private double calculateDiscriminant(double a, double b, double c) {
        return b * b - 4 * a * c;
    }

    private void showResultDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }




    @FXML
    private void showHistory() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("history-view.fxml"));
            Parent root = loader.load();

            Stage historyStage = new Stage();
            historyStage.initModality(Modality.APPLICATION_MODAL);
            historyStage.setTitle("История вычислений");
            historyStage.setScene(new Scene(root));

            HistoryController historyController = loader.getController();
            historyController.setMainController(this);

            historyStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static CalculatorApp instance;

    public static CalculatorApp getInstance() {
        return instance;
    }

    private void updateHistoryTextArea() {
        StringBuilder historyText = new StringBuilder();
        for (String entry : calculationHistory) {
            historyText.append(entry).append("\n");
        }
    }

    public List<String> getCalculationHistory() {
        return calculationHistory;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void clearHistory() {
    }
}
