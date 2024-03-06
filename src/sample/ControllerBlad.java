package sample;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerBlad implements Initializable {
    public BorderPane BP_Okno;
    public Button B_Ok;
    public Text T_TypBledu;
    public Text T_OpisBledu;

    public void B_Ok_Action() {
        Stage stage = (Stage) BP_Okno.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (TypBledu.recordNotSelected.equals(Main.typBledu)) {
            T_TypBledu.setText("Nie wybrano rekordu z tabeli!");
            T_OpisBledu.setText("");
        } else if (TypBledu.wrongDataFormat.equals(Main.typBledu)) {
            T_TypBledu.setText("Wprowadzono błędny format daty");
            T_OpisBledu.setText("Prawidłowy format to RRRR:MM:DD");
        } else if (TypBledu.wrongData.equals(Main.typBledu)) {
            T_OpisBledu.setText("Wprowadzono błędną kombinację dat!");
            T_OpisBledu.setText("Data początku nie może być po dacie końca! Nowa data początku musi być po starej dacie początku!");
        } else {
            T_OpisBledu.setText("Nieznany błąd");
            T_OpisBledu.setText("");
        }
    }
}
