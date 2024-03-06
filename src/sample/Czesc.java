package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Czesc implements Serializable {
    private int numerIdentyfikacyjny;
    private String nazwa;
    private String specyfikacjaTechniczna;
    private int cena;
    private List<Lista_czesci> listaCzesci = new ArrayList<>();
    private List<Zamowione_czesci> zamowioneCzesci = new ArrayList<>();

    private static List<Integer> numeryIdentyfikacyjne = new ArrayList<>();

    private static Map<Class, List<Czesc>> czescContainer = new Hashtable<>();

    public Czesc(int numerIdentyfikacyjny, String nazwa, String specyfikacjaTechniczna, int cena) throws Exception {
        setNumerIdentyfikacyjny(numerIdentyfikacyjny);
        this.nazwa = nazwa;
        this.specyfikacjaTechniczna = specyfikacjaTechniczna;
        this.cena = cena;
        List<Czesc> container = null;
        Class theClass = this.getClass();

        if (czescContainer.containsKey(theClass)) container = czescContainer.get(theClass);
        else {
            container = new ArrayList<>();
            czescContainer.put(theClass, container);
        }
        container.add(this);
    }

    public List<Lista_czesci> getListaCzesci() {
        return listaCzesci;
    }

    public List<Zamowione_czesci> getZamowioneCzesci() {
        return zamowioneCzesci;
    }

    public void addModel(Model model, int liczbaCzesci) {
        Lista_czesci lista_czesci = new Lista_czesci(liczbaCzesci,model,this);
        this.listaCzesci.add(lista_czesci);
        model.getListaCzesci().add(lista_czesci);
    }

    public void addZamowienie(Zamowienie zamowienie) {
        Zamowione_czesci zamowione_czesci = new Zamowione_czesci(this,zamowienie);
        this.zamowioneCzesci.add(zamowione_czesci);
        zamowienie.getZamowioneCzesci().add(zamowione_czesci);
    }

    public int getNumerIdentyfikacyjny() {
        return numerIdentyfikacyjny;
    }

    public void setNumerIdentyfikacyjny(int numerIdentyfikacyjny) throws Exception {
        if (numeryIdentyfikacyjne.contains(numerIdentyfikacyjny))
            throw new Exception("Część o takim numerze już istnieje!");
        this.numerIdentyfikacyjny = numerIdentyfikacyjny;
        numeryIdentyfikacyjne.add(numerIdentyfikacyjny);
    }

    public static void deleteCzesc(Czesc czesc) {
        czescContainer.get(czesc.getClass()).remove(czesc);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/CzescSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(czescContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/CzescSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        czescContainer = (Hashtable) ois.readObject();
    }
}
