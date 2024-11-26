package com.example.torinopizza;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class KundeSysController implements Initializable {

    //FX:ID
    @FXML
    private TableView<Kunde> tableViewKunde;
    @FXML
    private TableColumn<Kunde, String> tableViewKundeAdresse;
    @FXML
    private TableColumn<Kunde, Integer> tableViewKundeID;
    @FXML
    private TableColumn<Kunde, String> tableViewKundeNavn;
    @FXML
    private TableColumn<Kunde, String> tableViewKundeTelefon;
    @FXML
    private TextField textFieldAdresse;
    @FXML
    private TextField textFieldNavn;
    @FXML
    private TextField textFieldTelefon;

    //initialisering af variabler
    ObservableList<Kunde> listK;
    Kunde kunde = null;
    Connection conn = null;
    PreparedStatement pst = null;


    //Initialisering af scene - kalder på UpdateKundeList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        UpdateKundeList();
    }

    //Indsætter data ind i databasen udfra hvad der står i tekstfelterne
    @FXML
    void opretKundePress(ActionEvent event) {
        conn = DatabaseConnector.connection();
        String sql = "INSERT INTO Kunde (Kundenavn, Addresse, Telefon) VALUES (?,?,?)";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, textFieldNavn.getText());
            pst.setString(2, textFieldAdresse.getText());
            pst.setString(3, textFieldTelefon.getText());
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateKundeList();
    }
    //Sætter kunde object til at være det highlightede i tableviewet. Sender query udfra kundeID fra kundeobject, derefter kalder på UpdateKundeList();
    @FXML
    void sletKundePress(ActionEvent event) {
        kunde = tableViewKunde.getSelectionModel().getSelectedItem();
        conn = DatabaseConnector.connection();
        String query = "DELETE FROM kunde WHERE KundeID = " + kunde.getID();
        try {
            pst = conn.prepareStatement(query);
            pst.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        UpdateKundeList();
    }

    //Henter kunder ned fra database og tilføjer data i en ObservableList af Kunde Objects
    private static ObservableList<Kunde> getKundeData() {
        Connection conn = DatabaseConnector.connection();
        ObservableList<Kunde> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM torinopizza.kunde");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new Kunde(Integer.parseInt(rs.getString("KundeID")), rs.getString("Kundenavn"), rs.getString("Addresse"), rs.getString("Telefon")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    //Opdatere tableView med kunderne fra databasen
    private void UpdateKundeList() {
        tableViewKundeID.setCellValueFactory(new PropertyValueFactory<Kunde, Integer>("ID"));
        tableViewKundeNavn.setCellValueFactory(new PropertyValueFactory<Kunde, String>("Navn"));
        tableViewKundeAdresse.setCellValueFactory(new PropertyValueFactory<Kunde, String>("Addresse"));
        tableViewKundeTelefon.setCellValueFactory(new PropertyValueFactory<Kunde, String>("Telefon"));
        listK = getKundeData();
        tableViewKunde.setItems(listK);
    }

}
