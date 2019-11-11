package pl.poznan.put.cie.coffee;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.List;

public class CoffeeTable extends Application {

    private TableView<Coffee> table = new TableView<Coffee>();

    private CoffeeDa dao = new CoffeeDa();

    private ObservableList<Coffee> data;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Coffees");
        stage.setWidth(600);
        stage.setHeight(600);

        List<Coffee> arr = dao.getAll();

        data = FXCollections.observableArrayList(arr);

        final Label label = new Label("CoffeeTableView");
        label.setFont(new Font("Arial", 20));

        table.setEditable(true);

        TableColumn cof_name = new TableColumn("Coffee name");
        cof_name.setCellValueFactory(
                new PropertyValueFactory<Coffee, String>("name"));

        TableColumn sup_id = new TableColumn("Supplier ID");
        sup_id.setCellValueFactory(
                new PropertyValueFactory<Coffee, String>("supplierId"));

        TableColumn price = new TableColumn("Price");
        price.setCellValueFactory(
                new PropertyValueFactory<Coffee, String>("price"));

        TableColumn sales = new TableColumn("Sales");
        sales.setCellValueFactory(
                new PropertyValueFactory<Coffee, Integer>("sales"));

        TableColumn total = new TableColumn("Total");
        total.setCellValueFactory(
                new PropertyValueFactory<Coffee, Integer>("total"));

        table.getColumns().addAll(cof_name, sup_id, price, sales, total);
        table.setItems(data);
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
