package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ControllerListaUprawnien implements Initializable {
    public MenuItem MI_WyswietlListeUprawnien;
    public MenuItem MI_Zapisz;
    public BorderPane BP_Okno;
    public Text T_Zalogowano;
    public Button B_Odswiez;

    @FXML
    private TableView<TableViewUprawnienia> tableView;
    @FXML
    private TableColumn<TableViewUprawnienia, String> nazwa;
    @FXML
    private TableColumn<TableViewUprawnienia, LocalDate> wazneOd;
    @FXML
    private TableColumn<TableViewUprawnienia, LocalDate> wazneDo;

    private Inzynier inzynier = (Inzynier) Main.zalogowanyPracownik;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Main.edytowaneUprawnienie = null;
        Main.typBledu = TypBledu.unknown;
        T_Zalogowano.setText("Uprawnienia użytkownika " + Main.imieNazwiskoZalogowanyPracownik);
        nazwa.setCellValueFactory(new PropertyValueFactory<>("Nazwa"));
        wazneOd.setCellValueFactory(new PropertyValueFactory<>("wazneOd"));
        wazneDo.setCellValueFactory(new PropertyValueFactory<>("wazneDo"));

        tableView.setItems(getData());
    }

    public ObservableList<TableViewUprawnienia> getData() {
        ObservableList<TableViewUprawnienia> uprawnienie = FXCollections.observableArrayList();
        for (int i = 0; i < inzynier.getUprawnienieInzynier().size(); i++)
            uprawnienie.add(new TableViewUprawnienia(inzynier.getUprawnienieInzynier().get(i).getUprawnienie().getNazwa(), inzynier.getUprawnienieInzynier().get(i).getWazneOd(), inzynier.getUprawnienieInzynier().get(i).getWazneDo()));
        return uprawnienie;
    }

    @FXML
    public void MI_Zapisz_Action() {
        try {
            Main.saveEverything();
        } catch (Exception e) {
            System.out.println("Nie udało sie zapisać stanu aplikacji!\n" + e.getMessage());
        }
    }

    public void B_Odswiez_Action() {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Main.edytowaneUprawnienie = inzynier.getUprawnienieInzynier().stream()
                    .filter(uprawnienie -> tableView.getSelectionModel().getSelectedItem().getNazwa()
                            .equals(uprawnienie.getUprawnienie().getNazwa())).findAny().orElse(null);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("sampleOdswiezUprawnienie.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 500, 300);
                Stage stage = new Stage();
                stage.setTitle("Ośwież uprawnienie");
                stage.setScene(scene);
                stage.getIcons().add(new Image("icon/icon.jpg"));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) BP_Okno.getScene().getWindow();
            stage.close();
        } else {
            Main.typBledu = TypBledu.recordNotSelected;
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("sampleBlad.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 300, 100);
                Stage stage = new Stage();
                stage.setTitle("Błąd");
                stage.setScene(scene);
                stage.getIcons().add(new Image("icon/icon.jpg"));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    public void MI_WyswietlListeUprawnien_Action() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("sampleListaUprawnien.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 400);
            Stage stage = new Stage();
            stage.setTitle("Lista Uprawnień");
            stage.setScene(scene);
            stage.getIcons().add(new Image("icon/icon.jpg"));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) BP_Okno.getScene().getWindow();
        stage.close();
    }
}
