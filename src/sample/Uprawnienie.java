package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Uprawnienie implements Serializable {
    private String nazwa;
    private String opis;
    private List<UprawnienieInzynier> uprawnienieInzynier = new ArrayList<>();
    private static List<String> wszystkieNazwy = new ArrayList<>();

    private static Map<Class, List<Uprawnienie>> uprawnienieContainer = new Hashtable<>();

    public Uprawnienie(String nazwa, String opis) throws Exception {
        setNazwa(nazwa);
        this.opis = opis;
        List<Uprawnienie> container = null;
        Class theClass = this.getClass();

        if (uprawnienieContainer.containsKey(theClass)) container = uprawnienieContainer.get(theClass);
        else {
            container = new ArrayList<>();
            uprawnienieContainer.put(theClass, container);
        }
        container.add(this);
    }

    public void addUprawnienieInzynier(LocalDate wazneOd, LocalDate wazneDo, Inzynier inzynier) {
        UprawnienieInzynier uprawnienieInz = new UprawnienieInzynier(wazneOd, wazneDo, this, inzynier);
        uprawnienieInzynier.add(uprawnienieInz);
        inzynier.getUprawnienieInzynier().add(uprawnienieInz);
    }

    public List<UprawnienieInzynier> getUprawnienieInzynier() {
        return uprawnienieInzynier;
    }

    public void setNazwa(String nazwa) throws Exception {
        if (wszystkieNazwy.contains(nazwa)) throw new Exception("Istnieje już takie uprawnienie!");
        this.nazwa = nazwa;
        wszystkieNazwy.add(nazwa);
    }

    public String getNazwa() {
        return nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public static void deleteUprawnienie(Uprawnienie uprawnienie) {
        uprawnienieContainer.get(uprawnienie.getClass()).remove(uprawnienie);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/UprawnienieSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(uprawnienieContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/UprawnienieSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        uprawnienieContainer = (Hashtable) ois.readObject();
    }
}
