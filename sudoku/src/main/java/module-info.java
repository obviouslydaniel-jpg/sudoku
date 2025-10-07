module com.example.sudoku {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.sudoku.app to javafx.fxml;
    opens com.example.sudoku.controller to javafx.fxml;

    exports com.example.sudoku.app;
    exports com.example.sudoku.controller;
}
