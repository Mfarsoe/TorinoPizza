package com.example.torinopizza;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class MainController implements Initializable {


    @FXML
    private TableView<OrdreListObj> ordreTableView;

    @FXML
    private TableColumn<OrdreListObj, Integer> tableViewOrdre;

    @FXML
    private TableColumn<OrdreListObj, String> tableViewKunde;

    @FXML
    private TableColumn<OrdreListObj, Double> tableVIewPris;

    @FXML
    private TableColumn<OrdreListObj, String> tableViewStatus;

    private static MainController instance;

    Connection conn = null;
    PreparedStatement pst = null;
    OrdreListObj ordreListObj = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        opdaterOrdreTable();
    }

    //Opret Singleton mønster til at sikre at vi kun har en MainController. Dermed har vi adgang til vores metoder i andre classer.
    public MainController() {
        instance = this;
    }
    public static MainController getInstance() {
        return instance;
    }

    //Opdatere vores tabel og hvad der ud fra de instanser vi har af OrdreListObj i ObservableList
    public void opdaterOrdreTable() {
        tableViewOrdre.setCellValueFactory(new PropertyValueFactory<OrdreListObj, Integer>("ID"));
        tableViewKunde.setCellValueFactory(new PropertyValueFactory<OrdreListObj, String>("Navn"));
        tableVIewPris.setCellValueFactory(new PropertyValueFactory<OrdreListObj, Double>("Pris"));
        tableViewStatus.setCellValueFactory(new PropertyValueFactory<OrdreListObj, String>("Status"));
        ObservableList<OrdreListObj> listObjs = getOrdreTableData();
        ordreTableView.setItems(listObjs);
    }

    //Henter data ned fra databasen og sætter ind i en observable list som vi bruger til at opdatere vores tabel.
    private ObservableList<OrdreListObj> getOrdreTableData() {
        Connection conn = DatabaseConnector.connection();
        ObservableList<OrdreListObj> list = FXCollections.observableArrayList();
        String sql = "SELECT o.OrdreID, k.Kundenavn, o.TotalPris, o.LeveringsStatus FROM Ordre o JOIN Kunde k ON o.KundeID = k.KundeID;";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new OrdreListObj(Integer.parseInt(
                        rs.getString("OrdreID")),
                        rs.getString("Kundenavn"),
                        rs.getDouble("Totalpris"),
                        rs.getString("LeveringsStatus")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //Åbner nyt vindue med nyordre-view og NyordreViewController
    @FXML
    void nyOrdrePress(ActionEvent event) throws IOException {
        Stage nyOrdreStage = new Stage();
        FXMLLoader nyOrdreLoader = new FXMLLoader(getClass().getResource("nyordre-view.fxml"));
        Scene nyOrdreScene = new Scene(nyOrdreLoader.load());
        nyOrdreStage.setScene(nyOrdreScene);
        nyOrdreStage.setTitle("Torino Pizza - Ny Ordre");
        nyOrdreStage.show();
    }

    //Åbner nyt vindue med kundesys-view og Kundesyscontroller
    @FXML
    void brugerHPress(ActionEvent event) throws IOException {
        Stage brugerH = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("kundesys-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        brugerH.setScene(scene);
        brugerH.setTitle("Torino Pizza - Bruger Håndtering");
        brugerH.show();
    }


    //Sletter Ordre fra den viste liste og i databasen
    @FXML
    void sletOrdrePress(ActionEvent event) {
        sletOrdreDetalje();
        ordreListObj = ordreTableView.getSelectionModel().getSelectedItem();
        conn = DatabaseConnector.connection();
        String sql = "DELETE FROM Ordre WHERE OrdreID = " + ordreListObj.getID();
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        opdaterOrdreTable();
    }

    //Sletter OrdreDetalje der er associeret med OrdreID'et i databasen
    private void sletOrdreDetalje() {
        sletOrdreExtra();
        ordreListObj = ordreTableView.getSelectionModel().getSelectedItem();
        conn = DatabaseConnector.connection();
        String sql = "DELETE FROM ordredetalje WHERE OrdreID = " + ordreListObj.getID();
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Sletter OrdreExtra der er associeret med OrdreDetalje i databasen
    private void sletOrdreExtra(){
        ordreListObj = ordreTableView.getSelectionModel().getSelectedItem();
        conn = DatabaseConnector.connection();
        String sql = "DELETE FROM ordreextra WHERE OrdreDetaljeID IN (SELECT OrdreDetaljeID FROM ordredetalje WHERE OrdreID = " + ordreListObj.getID() + ")";
        try {
            pst = conn.prepareStatement(sql);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


