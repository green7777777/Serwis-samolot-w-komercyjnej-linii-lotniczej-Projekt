package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Main extends Application {

    public static Pracownik zalogowanyPracownik;
    public static String imieNazwiskoZalogowanyPracownik;
    public static Enum typBledu;
    public static UprawnienieInzynier edytowaneUprawnienie;

    public static void loadEverything() throws Exception {
        Samolot.readContainer();
        Model.readContainer();
        Lista_czesci.readContainer();
        Czesc.readContainer();
        Zamowione_czesci.readContainer();
        Zamowienie.readContainer();
        Serwis.readContainer();
        SamolotOdbywalSerwis.readContainer();
        Pracownik.readContainer();
        UprawnienieInzynier.readContainer();
        Uprawnienie.readContainer();
    }

    public static void saveEverything() throws Exception {
        Samolot.writeContainer();
        Model.writeContainer();
        Lista_czesci.writeContainer();
        Czesc.writeContainer();
        Zamowione_czesci.writeContainer();
        Zamowienie.writeContainer();
        Serwis.writeContainer();
        SamolotOdbywalSerwis.writeContainer();
        Pracownik.writeContainer();
        UprawnienieInzynier.writeContainer();
        Uprawnienie.writeContainer();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sampleInzynier.fxml"));
        primaryStage.setTitle("Serwis Samolotów");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.getIcons().add(new Image("icon/icon.jpg"));
        primaryStage.show();
    }


    public static void main(String[] args) throws Exception {
        typBledu = TypBledu.unknown;
        loadEverything();

        /*
        Model model2 = new Model("737", "Boeing");
        Model model3 = new Model("757", "Boeing");
        Model model5 = new Model("A320", "Airbus");

        Samolot samolot1 = Samolot.createSamolot(model2,122314218,LocalDate.of(2010,10,10));
        Samolot samolot2 = Samolot.createSamolot(model3,452314265,LocalDate.of(2005,7,30));
        Samolot samolot3 = Samolot.createSamolot(model5,452314561,LocalDate.of(2012,6,11));
        Samolot samolot4 = Samolot.createSamolot(model5,672345218,LocalDate.of(2013,6,11));
        samolot1.makePasazerski(150);
        samolot2.makePasazerski(250);
        samolot3.makePasazerski(160);
        samolot4.makeCombi(20,100,true);

        Czesc czesc1 = new Czesc(3515234, "Skrzydło737", "Specyfikacja",4000);
        Czesc czesc2 = new Czesc(3515235, "Kadłub737", "Specyfikacja",6000);
        Czesc czesc3 = new Czesc(3515236, "Silnik737", "Specyfikacja",5000);
        Czesc czesc4 = new Czesc(3534234, "Skrzydło757", "Specyfikacja",8000);
        Czesc czesc5 = new Czesc(3535235, "Kadłub757", "Specyfikacja",8000);
        Czesc czesc6 = new Czesc(3536236, "Silnik757", "Specyfikacja",7000);
        Czesc czesc7 = new Czesc(1215234, "SkrzydłoA320", "Specyfikacja",4000);
        Czesc czesc8 = new Czesc(1215235, "KadłubA320", "Specyfikacja",6000);
        Czesc czesc9 = new Czesc(1215236, "SilnikA320", "Specyfikacja",6000);

        model2.addCzesc(czesc1,1);
        model2.addCzesc(czesc2,1);
        model2.addCzesc(czesc3,2);
        model3.addCzesc(czesc4,1);
        model3.addCzesc(czesc5,1);
        model3.addCzesc(czesc6,2);
        model5.addCzesc(czesc7,1);
        model5.addCzesc(czesc8,1);
        model5.addCzesc(czesc9,2);

        Inzynier inzynier1 = new Inzynier("12345", "Jan", "Maczek", 1000);
        Inzynier inzynier2 = new Inzynier("23543", "Jeremi", "Wojtaszek", 2000);
        Inzynier inzynier3 = new Inzynier("43566", "Arnold", "Kowalski", 4000);
        Inzynier inzynier4 = new Inzynier("23543", "Maciej", "Pisz", 2000);

        Pilot pilot = new Pilot("46436", "Franciszek", "Witkacy", 6000,10000);

        Pracownik_administracji pracownik_administracji = new Pracownik_administracji("46436", "Franciszek", "Witkacy", 6000,444555666);

        Uprawnienie uprawnienie1 = new Uprawnienie("Ekspertyza spawów","Uprawniony do analizy spawów lotniczych");
        Uprawnienie uprawnienie2 = new Uprawnienie("Ekspertyza silników","Uprawniony do dogłebnej analizy silników lotniczych");
        Uprawnienie uprawnienie3 = new Uprawnienie("Kontroller ogumienia","Uprawniony do testów ogumienia samolotu i jego wymiany");
        Uprawnienie uprawnienie4 = new Uprawnienie("Ekspertyza okablowania","Uprawniony do wymiany i testów okablowania samolotów");

        inzynier1.addUprawnienieInzynier(LocalDate.of(2021,10,10),LocalDate.of(2022,10,10),uprawnienie1);

        inzynier2.addUprawnienieInzynier(LocalDate.of(2021,10,10),LocalDate.of(2022,10,10),uprawnienie2);
        inzynier2.addUprawnienieInzynier(LocalDate.of(2020,10,10),LocalDate.of(2021,10,10),uprawnienie4);

        inzynier3.addUprawnienieInzynier(LocalDate.of(2021,10,10),LocalDate.of(2022,10,10),uprawnienie3);
        inzynier3.addUprawnienieInzynier(LocalDate.of(2021,10,10),LocalDate.of(2022,10,10),uprawnienie2);
        inzynier3.addUprawnienieInzynier(LocalDate.of(2020,10,10),LocalDate.of(2021,10,10),uprawnienie4);

        inzynier4.addUprawnienieInzynier(LocalDate.of(2021,10,10),LocalDate.of(2022,10,10),uprawnienie4);
        inzynier4.addUprawnienieInzynier(LocalDate.of(2021,10,10),LocalDate.of(2022,10,10),uprawnienie1);

        Serwis serwis1 = new Serwis(303001, "Serwis ogólny");
        Serwis serwis2 = new Serwis(303002, "Serwis szczegółowy");
        Serwis serwis3 = new Serwis(303003, "Serwis ogólny");

        serwis1.addSamolotOdbywalSerwis(LocalDate.of(2021,12,1),"podstawowy",samolot1);
        serwis1.zakonczSerwis(LocalDate.of(2022,1,1));
        serwis2.addSamolotOdbywalSerwis(LocalDate.of(2022,9,1),"podstawowy",samolot1);
        serwis2.zakonczSerwis(LocalDate.of(2022,10,1));
        serwis3.addSamolotOdbywalSerwis(LocalDate.of(2022,8,8),"obszerny",samolot4);
        serwis3.zakonczSerwis(LocalDate.of(2022,9,1));

        Zamowienie zamowienie1 = new Zamowienie(1234, LocalDate.of(2021,12,10));
        zamowienie1.addCzesc(czesc1);
        serwis1.addZamowienie(zamowienie1);
        zamowienie1.setDataDostarczenia(LocalDate.of(2021,12,20));

        inzynier1.addXorRole(Inzynier.ROLE_DIRECTOR_OF);
        inzynier2.addXorRole(Inzynier.ROLE_DIRECTOR_OF);
        inzynier3.addXorRole(Inzynier.ROLE_DIRECTOR_OF);
        inzynier4.addXorRole(Inzynier.ROLE_DIRECTOR_OF);

        inzynier1.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis1);
        inzynier1.addXorLink(Inzynier.ROLE_DIRECTOR_OF, Serwis.ROLE_DIRECTS, serwis1);
        inzynier3.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis1);

        inzynier2.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis2);
        inzynier2.addXorLink(Inzynier.ROLE_DIRECTOR_OF, Serwis.ROLE_DIRECTS, serwis2);
        inzynier3.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis2);
        inzynier1.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis2);

        inzynier3.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis3);
        inzynier3.addXorLink(Inzynier.ROLE_DIRECTOR_OF, Serwis.ROLE_DIRECTS, serwis3);
        inzynier1.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis3);
        inzynier2.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis3);
        inzynier4.addXorLink(Inzynier.ROLE_WORKS_ON, Serwis.ROLE_WORKS, serwis3);

        */
        //zalogowanyPracownik = inzynier3;
        zalogowanyPracownik = Pracownik.getPracownikContainer().get(Inzynier.class).get(2);
        //System.out.println(Inzynier.getPracownikContainer());

        //saveEverything();

        launch(args);
    }
}
