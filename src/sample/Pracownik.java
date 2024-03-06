package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public abstract class Pracownik implements Serializable {
    private String PESEL;
    private String imie;
    private String nazwisko;
    private static final int PLACA_MINIMALNA = 5000;
    private int dodatekSpecjalizacyjny;
    private int pensja;

    private List<String> PESELe = new ArrayList<>();

    private static Map<Class, List<Pracownik>> pracownikContainer = new Hashtable<>();

    public Pracownik(String PESEL, String imie, String nazwisko, int dodatekSpecjalizacyjny) throws Exception {
        setPESEL(PESEL);
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.dodatekSpecjalizacyjny = dodatekSpecjalizacyjny;
        this.pensja = PLACA_MINIMALNA + dodatekSpecjalizacyjny;
        List<Pracownik> container = null;
        Class theClass = this.getClass();

        if (pracownikContainer.containsKey(theClass)) container = pracownikContainer.get(theClass);
        else {
            container = new ArrayList<>();
            pracownikContainer.put(theClass, container);
        }
        container.add(this);
    }

    public void setPESEL(String PESEL) throws Exception{
        if (PESELe.contains(PESEL)) throw new Exception("Pracownik o takim numerze PESEL już istnieje!");
        this.PESEL = PESEL;
        PESELe.add(PESEL);
    }

    public String getPESEL() {
        return PESEL;
    }

    //to acces pracownik at the start of application
    public static Map<Class, List<Pracownik>> getPracownikContainer() {
        return pracownikContainer;
    }

    public String getImie() {
        return imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public static void deletePracownik(Pracownik pracownik) {
        pracownikContainer.get(pracownik.getClass()).remove(pracownik);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/PracownikSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(pracownikContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/PracownikSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        pracownikContainer = (Hashtable) ois.readObject();
    }
}
