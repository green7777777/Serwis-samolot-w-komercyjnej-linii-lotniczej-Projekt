package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Zamowione_czesci implements Serializable {
    private Czesc czesc;
    private Zamowienie zamowienie;

    private static Map<Class, List<Zamowione_czesci>> zamowioneCzesciContainer = new Hashtable<>();

    public Zamowione_czesci(Czesc czesc, Zamowienie zamowienie) {
        this.czesc = czesc;
        this.zamowienie = zamowienie;
        List<Zamowione_czesci> container = null;
        Class theClass = this.getClass();

        if (zamowioneCzesciContainer.containsKey(theClass)) container = zamowioneCzesciContainer.get(theClass);
        else {
            container = new ArrayList<>();
            zamowioneCzesciContainer.put(theClass, container);
        }
        container.add(this);
    }

    public Czesc getCzesc() {
        return czesc;
    }

    public Zamowienie getZamowienie() {
        return zamowienie;
    }

    public static void deleteZamowioneCzesci(Zamowione_czesci zamowione_czesci) {
        zamowioneCzesciContainer.get(zamowione_czesci.getClass()).remove(zamowione_czesci);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/ZamowioneCzesciSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(zamowioneCzesciContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/ZamowioneCzesciSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        zamowioneCzesciContainer = (Hashtable) ois.readObject();
    }
}
