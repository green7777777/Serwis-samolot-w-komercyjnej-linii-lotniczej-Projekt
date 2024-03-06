package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Samolot implements Serializable {
    private int numerRejestracyjny;
    private LocalDate dataProdukcji;
    private LocalDate dataOstatniegoSerwisu;
    private List<String> notatki = new ArrayList<>();
    private Model model;
    private List<SamolotOdbywalSerwis> samolotOdbywalSerwis = new ArrayList<>();

    private static List<Integer> numeryRejestracyjne = new ArrayList<>();

    private int liczbaPasazerow;
    private int ladownoscCargo;
    private boolean czyLadowniaHermetyzowana;

    private EnumSet<TypSamolotu> typSamolotu = EnumSet.of(TypSamolotu.Samolot);

    private static Map<Class, List<Samolot>> samolotContainer = new Hashtable<>();

    private Samolot(Model model, int numerRejestracyjny, LocalDate dataProdukcji) throws Exception {
        this.model = model;
        this.numerRejestracyjny = numerRejestracyjny;
        this.dataProdukcji = dataProdukcji;
        this.dataOstatniegoSerwisu = null;
        model.addSamolot(this);
        List<Samolot> container = null;
        Class theClass = this.getClass();

        if (samolotContainer.containsKey(theClass)) container = samolotContainer.get(theClass);
        else {
            container = new ArrayList<>();
            samolotContainer.put(theClass, container);
        }
        container.add(this);
    }

    public void addSamolotOdbywalSerwis(LocalDate dataOd, String zakres, Serwis serwis) {
        SamolotOdbywalSerwis odbywalSerwis = new SamolotOdbywalSerwis(dataOd, zakres, this, serwis);
        this.samolotOdbywalSerwis.add(odbywalSerwis);
        serwis.getSamolotOdbywalSerwis().add(odbywalSerwis);
    }

    public List<SamolotOdbywalSerwis> getSamolotOdbywalSerwis() {
        return samolotOdbywalSerwis;
    }

    public static Samolot createSamolot(Model model, int numerRejestracyjny, LocalDate dataProdukcji) throws Exception {
        if (model == null) throw new Exception("Dany model nie istnieje!");
        Samolot samolot = new Samolot(model, numerRejestracyjny, dataProdukcji);
        model.addSamolot(samolot);
        return samolot;
    }

    public void remove() {
        numeryRejestracyjne.remove(this.numerRejestracyjny);
        model.getSamoloty().remove(this);
        this.model = null;
        for (SamolotOdbywalSerwis sos : this.samolotOdbywalSerwis) {
            sos.setSamolot(null);
        }
        this.samolotOdbywalSerwis = null;
        deleteSamolot(this);
    }

    public Model getModel() {
        return model;
    }

    public void makePasazerski(int liczbaPasazerow) {
        this.liczbaPasazerow = liczbaPasazerow;
        this.typSamolotu.add(TypSamolotu.Pasażerski);
    }

    public void makeTransportowy(int ladownoscCargo) {
        this.ladownoscCargo = ladownoscCargo;
        this.typSamolotu.add(TypSamolotu.Transportowy);
    }

    public void makeCombi(int liczbaPasazerow, int ladownoscCargo, boolean czyLadowniaHermetyzowana) {
        this.makePasazerski(liczbaPasazerow);
        this.makeTransportowy(ladownoscCargo);
        this.czyLadowniaHermetyzowana = czyLadowniaHermetyzowana;
        this.typSamolotu.add(TypSamolotu.Combi);
    }

    public void removePasazerski() {
        this.liczbaPasazerow = 0;
        this.typSamolotu.remove(TypSamolotu.Pasażerski);
    }

    public void removeTransportowy() {
        this.ladownoscCargo = 0;
        this.typSamolotu.remove(TypSamolotu.Transportowy);
    }

    public void removeCombi() {
        this.typSamolotu.remove(TypSamolotu.Combi);
    }

    public LocalDate getDataOstatniegoSerwisu() {
        return dataOstatniegoSerwisu;
    }

    public void setDataOstatniegoSerwisu(LocalDate dataOstatniegoSerwisu) {
        this.dataOstatniegoSerwisu = dataOstatniegoSerwisu;
    }

    public int getNumerRejestracyjny() {
        return numerRejestracyjny;
    }

    public void setNumerRejestracyjny(int numerRejestracyjny) throws Exception {
        if (numeryRejestracyjne.contains(numerRejestracyjny))
            throw new Exception("Samolot o takim numerze rejestracyjnym już istnieje!");
        this.numerRejestracyjny = numerRejestracyjny;
        numeryRejestracyjne.add(numerRejestracyjny);
    }

    public void dodajNotatke(String notatka) {
        notatki.add(notatka);
    }

    public List<String> getNotatki() {
        return notatki;
    }

    public static Map<Class, List<Samolot>> getSamolotContainer() {
        return samolotContainer;
    }

    public static void deleteSamolot(Samolot samolot) {
        samolotContainer.get(samolot.getClass()).remove(samolot);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/SamolotSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(samolotContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/SamolotSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        samolotContainer = (Hashtable) ois.readObject();
    }
}
