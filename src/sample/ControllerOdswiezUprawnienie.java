package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOdswiezUprawnienie implements Initializable {
    public MenuItem MI_WyswietlListeUprawnien;
    public MenuItem MI_Zapisz;
    public BorderPane BP_Okno;
    public Text T_Nazwa;
    public Button B_Zapisz;
    public TextField TF_NowaDataOd;
    public TextField TF_NowaDataDo;

    public UprawnienieInzynier uprawnienieInzynier = Main.edytowaneUprawnienie;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        T_Nazwa.setText("Odświeżenie uprawnienia: " + uprawnienieInzynier.getUprawnienie().getNazwa());

    }

    public void B_Zapisz_Action() {
        boolean wasError = false;
        boolean errorMessageLong = false;
        try {
            uprawnienieInzynier.updateDaty(TF_NowaDataOd.getText(),TF_NowaDataDo.getText());
        } catch (Exception e) {
            wasError = true;
            String error = e.getMessage();
            if (error.equals("Zły format daty!")) Main.typBledu = TypBledu.wrongDataFormat;
            else if (error.equals("Nowa data początku uprawnienia nie może być przed ówczesną datą początku uprawnienia!")) {
                Main.typBledu = TypBledu.wrongData;
                errorMessageLong = true;
            }
            else if (error.equals("Nowa data upływu uprawnienia nie może być przed ówczesną datą upływu uprawnienia!")) {
                Main.typBledu = TypBledu.wrongData;
                errorMessageLong = true;
            }
            else if (error.equals("Nowa data nabycia uprawnienia nie może być przed nową datą upływu uprawnienia!")) {
                Main.typBledu = TypBledu.wrongData;
                errorMessageLong = true;
            }
            else Main.typBledu = TypBledu.wrongDataFormat;
        }
        if (wasError) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("sampleBlad.fxml"));
                Scene scene;
                if (errorMessageLong) scene = new Scene(fxmlLoader.load(), 700, 100);
                else scene = new Scene(fxmlLoader.load(), 300, 100);
                Stage stage = new Stage();
                stage.setTitle("Błąd");
                stage.setScene(scene);
                stage.getIcons().add(new Image("icon/icon.jpg"));
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
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
}
