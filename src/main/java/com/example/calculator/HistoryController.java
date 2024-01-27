package com.example.calculator;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import java.util.List;

public class HistoryController {

    @FXML
    private TextArea historyTextArea;

    private CalculatorApp mainController;

    public void setMainController(CalculatorApp mainController) {
        this.mainController = mainController;
        setCalculationHistory(mainController.getCalculationHistory());
    }


    public void setCalculationHistory(List<String> calculationHistory) {
        StringBuilder historyText = new StringBuilder();
        for (String entry : calculationHistory) {
            historyText.append(entry).append("\n");
        }
        historyTextArea.setText(historyText.toString());
    }
    @FXML
    private void clearHistory() {
        if (mainController != null) {
            mainController.clearHistory();
        }
        historyTextArea.clear();
    }


}
