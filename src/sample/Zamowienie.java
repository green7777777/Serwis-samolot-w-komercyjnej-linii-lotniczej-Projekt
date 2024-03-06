package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Zamowienie implements Serializable {
    private int numerZamowienia;
    private int sumarycznaCena;
    private LocalDate dataZlozenia;
    private LocalDate dataDostarczenia;
    private Enum<StatusZamowienie> status;
    private List<Zamowione_czesci> zamowioneCzesci = new ArrayList<>();
    private Serwis serwis;

    private static List<Integer> numeryZamowien = new ArrayList<>();

    private static Map<Class, List<Zamowienie>> zamowienieContainer = new Hashtable<>();

    public Zamowienie(int numerZamowienia, LocalDate dataZlozenia) throws Exception {
        setNumerZamowienia(numerZamowienia);
        this.sumarycznaCena = 0;
        this.dataZlozenia = dataZlozenia;
        this.dataDostarczenia = null;
        this.status = StatusZamowienie.Nowe;
        List<Zamowienie> container = null;
        Class theClass = this.getClass();

        if (zamowienieContainer.containsKey(theClass)) container = zamowienieContainer.get(theClass);
        else {
            container = new ArrayList<>();
            zamowienieContainer.put(theClass, container);
        }
        container.add(this);
    }

    public List<Zamowione_czesci> getZamowioneCzesci() {
        return zamowioneCzesci;
    }

    public void addCzesc(Czesc czesc) {
        Zamowione_czesci zamowione_czesci = new Zamowione_czesci(czesc,this);
        this.zamowioneCzesci.add(zamowione_czesci);
        czesc.getZamowioneCzesci().add(zamowione_czesci);
        this.status = StatusZamowienie.W_trakcie;
    }

    public void setDataDostarczenia(LocalDate dataDostarczenia) {
        this.dataDostarczenia = dataDostarczenia;
        this.status = StatusZamowienie.Zakonczone;
    }

    public int getNumerZamowienia() {
        return numerZamowienia;
    }

    public void setNumerZamowienia(int numerZamowienia) throws Exception {
        if (numeryZamowien.contains(numerZamowienia)) throw new Exception("Zamówienie o takim numerze już istnieje!");
        this.numerZamowienia = numerZamowienia;
        numeryZamowien.add(numerZamowienia);
    }

    public static void deleteZamowienie(Zamowienie zamowienie) {
        zamowienieContainer.get(zamowienie.getClass()).remove(zamowienie);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/ZamowienieSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(zamowienieContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/ZamowienieSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        zamowienieContainer = (Hashtable) ois.readObject();
    }

    public void setSerwis(Serwis serwis) {
        this.serwis = serwis;
    }
}
