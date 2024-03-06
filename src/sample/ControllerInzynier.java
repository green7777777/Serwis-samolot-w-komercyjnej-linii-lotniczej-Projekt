package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerInzynier implements Initializable {
    public MenuItem MI_WyswietlListeUprawnien;
    public MenuItem MI_Zapisz;
    public BorderPane BP_Okno;
    public Text T_Zalogowano;

    @FXML
    public void MI_Zapisz_Action() {
        try {
            Main.saveEverything();
        } catch (Exception e) {
            System.out.println("Nie udało sie zapisać stanu aplikacji!\n" + e.getMessage());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String stanowisko = Main.zalogowanyPracownik.getClass().getName();
        System.out.println(Main.zalogowanyPracownik.getClass().getName());
        if (stanowisko.equals("sample.Inzynier"))
            stanowisko = "Inżynier";
        else if (stanowisko.equals("sample.Pilot"))
            stanowisko = "Pilot";
        else if (stanowisko.equals("sample.Pracownik_administracji"))
            stanowisko = "Pracownik Administracji";
        else
            stanowisko = "Pracownik";

        Main.imieNazwiskoZalogowanyPracownik = Main.zalogowanyPracownik.getImie() + " " + Main.zalogowanyPracownik.getNazwisko() + " (" + stanowisko + ")";
        T_Zalogowano.setText("Zalogowano jako " + Main.imieNazwiskoZalogowanyPracownik);
    }
}
