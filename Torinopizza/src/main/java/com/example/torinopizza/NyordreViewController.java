package com.example.torinopizza;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class NyordreViewController implements Initializable {

    //FX:ID
    @FXML
    private TextField antalTextField;
    @FXML
    private CheckBox basilikumCheckBox;
    @FXML
    private CheckBox skinkeCheckBox;
    @FXML
    private CheckBox tomatSauceCheckBox;
    @FXML
    private CheckBox champignonCheckBox;
    @FXML
    private CheckBox extraOstCheckBox;
    @FXML
    private CheckBox pepperoniCheckBox;
    @FXML
    private CheckBox ananasCheckBox;
    @FXML
    private ComboBox<String> kundeComboBox;
    @FXML
    private TableView<String> ordreTableView;
    @FXML
    private ListView<String> pizzaListView;
    @FXML
    private Label brugerLabel = null;
    @FXML
    private Label prisLabel;

    //Initialisering af variabler, List, Hashmap, Connection, Resultset og PreparedStatement
    private int pizzaID;
    private int currentOrdreID;
    private int currentPizzaID;
    private double temppris;
    ObservableList<String> ordreExtraObservableList;
    HashMap<String, Pizza> pizzaMap = new HashMap<>();
    HashMap<Integer, String> idkunde = new HashMap<>();

    //Connection, Resultset og Prepared statement er til lettere brug i løbet af koden.
    Connection conn = null;
    ResultSet rs = null;
    PreparedStatement pst = null;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Listen med CheckBox skal ligge her, for at blive initialiseret korrekt, så de ikke returner null.
        CheckBox[] checkBoxes = {ananasCheckBox, basilikumCheckBox, champignonCheckBox, extraOstCheckBox,pepperoniCheckBox,skinkeCheckBox,tomatSauceCheckBox};

        //Sætter variabler til 0 ved initialisering
        temppris = 0;
        currentOrdreID = 0;

        //Service metoder til at hente brugernes navne, opdatere inholdet i combobox og i listviewet med pizzanavne.
        fetchMap();
        updateComboBox();
        updatePizzaListView();

        //Skjuler Label
        brugerLabel.setVisible(false);
    }

    //Bruges til at oprette ordre med den valgte kunde, da det skal bruges til næste del af oprettelsen af ordren.
    @FXML
    void bekraeftPress(ActionEvent event) {
        conn = DatabaseConnector.connection();
        String sql = "INSERT INTO ordre (KundeID, Ordredato, TotalPris) VALUES (?,?,?)";
        LocalDateTime now = LocalDateTime.now();
        brugerLabel.setVisible(true);

        try {
            pst = conn.prepareStatement(sql);
            pst.setInt(1, getCustomerID(kundeComboBox, idkunde));
            pst.setObject(2, now);
            pst.setDouble(3, 0.00);
            pst.execute();
            brugerLabel.setText("Success");
        } catch (Exception e) {
            brugerLabel.setText("Failed");
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) conn.close(); // Luk forbindelsen
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        //Sætter Current Ordre til sidste ordre som er oprettets id.
        currentOrdreID = getLastOrderID();
    }

    @FXML
    void TilfojTilOrdrePress(ActionEvent event) {
        addPizzatoOrder();
        addExtratoPizza();
        setPrisLabel();
        uncheckCheckbox();
        updateOrdreList();
    }



    @FXML
    void faerdigorOrdrePress(ActionEvent event) {
        opdaterPris();

        MainController.getInstance().opdaterOrdreTable();
        //Lukker vinduet
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private void opdaterPris() {
        conn = DatabaseConnector.connection();
        String sql = "UPDATE ordre SET totalpris = ? WHERE ordreid = " + currentOrdreID;
        try {
            pst = conn.prepareStatement(sql);
            pst.setDouble(1, temppris);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void addPizzatoOrder() {
        pizzaID = getPizzaID(pizzaListView,pizzaMap);
        System.out.println(pizzaID);
        conn = DatabaseConnector.connection();
        String sql = "INSERT INTO ordredetalje (OrdreID, PizzaID, Antal, subTotal) VALUES (?,?,?,?)";
        try{
            pst = conn.prepareStatement(sql);
            pst.setInt(1, currentOrdreID);
            pst.setInt(2, pizzaID);
            pst.setInt(3, Integer.parseInt(antalTextField.getText()));
            pst.setDouble(4, getPizzaPris(pizzaListView,pizzaMap));
            pst.execute();
            temppris += (getPizzaPris(pizzaListView,pizzaMap) * Integer.parseInt(antalTextField.getText()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addExtratoPizza() {
        currentPizzaID = getCurrentPizzaDetaljeID();
        conn = DatabaseConnector.connection();
        CheckBox[] checkBoxes = {ananasCheckBox, basilikumCheckBox, champignonCheckBox, extraOstCheckBox,pepperoniCheckBox,skinkeCheckBox,tomatSauceCheckBox};
        String sql = "INSERT INTO ordreextra (OrdreDetaljeID, InventarID, Antal, ExtraPris) VALUES (?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(sql);
            System.out.println("SQL: " + sql);
            System.out.println("Connection: " + conn);
            for (int i = 0; i < checkBoxes.length; i++){
                CheckBox checkBox = checkBoxes[i];
                if (checkBox != null && checkBox.isSelected()){
                    int value = i + 1;
                    pst.setInt(1, currentPizzaID);
                    pst.setInt(2, value);
                    pst.setInt(3, 1);
                    pst.setDouble(4, 5.0);
                    pst.execute();
                    temppris += 5;
                    prisLabel.setText(String.valueOf(temppris));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getCurrentPizzaDetaljeID() {
        conn = DatabaseConnector.connection();
        String sql = "SELECT OrdreDetaljeID FROM ordredetalje ORDER BY OrdreDetaljeID DESC LIMIT 1";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            return rs.getInt("OrdreDetaljeID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    private int getPizzaID(ListView<String> pizzaListView, HashMap<String, Pizza> pizzaMap) {
        String valgtPizza = pizzaListView.getSelectionModel().getSelectedItem();
        if (valgtPizza != null) {
            Pizza pizza = pizzaMap.get(valgtPizza);
            if (pizza != null) {
                return pizza.getPizzaID();
            }
        }
        return -1;
    }

    private double getPizzaPris(ListView<String> pizzaListView, HashMap<String, Pizza> pizzaMap) {
        // Hent det valgte pizza-navn fra ListView
        String valgtPizza = pizzaListView.getSelectionModel().getSelectedItem();
        if (valgtPizza != null) {
            // Hent Pizza-objektet fra HashMap
            Pizza pizza = pizzaMap.get(valgtPizza);
            if (pizza != null) {
                return pizza.getPris();
            }
        }
        return -1;
    }


    private void updatePizzaListView(){
        conn = DatabaseConnector.connection();
        String sql = "SELECT * FROM menu";

        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            ObservableList<String> pizzaList = FXCollections.observableArrayList();
            while (rs.next()) {
                int pizzaID = rs.getInt("PizzaID");
                String navn = rs.getString("Navn");
                String beskrivelse = rs.getString("Beskrivelse");
                double pris = rs.getDouble("Pris");
                Pizza pizza = new Pizza(pizzaID, navn, beskrivelse, pris);
                pizzaMap.put(navn, pizza);
                pizzaList.add(navn);
            }
            pizzaListView.setItems(pizzaList);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private Integer getCustomerID(ComboBox<String> comboBox, Map<Integer, String> kundeMap) {
        String valgtnavn = comboBox.getValue();
        if (valgtnavn != null) {
            return kundeMap.entrySet().stream().filter(entry -> entry.getValue().equals(valgtnavn)).map(Map.Entry::getKey).findFirst().orElse(null);
        }
        return null;
    }

    private void fetchMap() {
        conn = DatabaseConnector.connection();
        String sql = "SELECT kundeid, kundenavn FROM kunde";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("KundeID");
                String kundenavn = rs.getString("Kundenavn");
                idkunde.put(id, kundenavn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void uncheckCheckbox(){
        champignonCheckBox.setSelected(false);
        ananasCheckBox.setSelected(false);
        basilikumCheckBox.setSelected(false);
        tomatSauceCheckBox.setSelected(false);
        skinkeCheckBox.setSelected(false);
        pepperoniCheckBox.setSelected(false);
        extraOstCheckBox.setSelected(false);

    }

    private void updateComboBox(){
        kundeComboBox.getItems().addAll(idkunde.values());
    }


    private int getLastOrderID() {
        conn = DatabaseConnector.connection();
        String sql = "SELECT OrdreID FROM ordre ORDER BY OrdreID DESC LIMIT 1";
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            rs.next();
            return rs.getInt("OrdreID");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    //
    private void updateOrdreList() {
        TableColumn<String, String> pizzaColumn = new TableColumn<>("Pizza Navn");
        pizzaColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()));

        ordreTableView.getColumns().clear(); // Ryd gamle kolonner, hvis nødvendigt
        ordreTableView.getColumns().add(pizzaColumn);
        ordreExtraObservableList = getOrdreExtraList();
        ordreTableView.setItems(ordreExtraObservableList);
    }

    //Observablelist med alle ordreextras
    private ObservableList<String> getOrdreExtraList() {
        conn = DatabaseConnector.connection();
        String sql = "SELECT m.Navn FROM menu m JOIN ordredetalje od ON m.PizzaID = od.PizzaID WHERE od.OrdreID = " + currentOrdreID;
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            pst = conn.prepareStatement(sql);
            rs = pst.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Navn"));
            }
            System.out.println(list);
            return list;
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    //Opdatere prislable
    private void setPrisLabel(){
        prisLabel.setText(String.valueOf(temppris));
    }
}
