package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Lista_czesci implements Serializable {
    private int liczbaCzesci;

    private Model model;
    private Czesc czesc;

    private static Map<Class, List<Lista_czesci>> listaCzesciContainer = new Hashtable<>();

    public Lista_czesci(int liczbaCzesci, Model model, Czesc czesc) {
        this.liczbaCzesci = liczbaCzesci;
        this.model = model;
        this.czesc = czesc;
        List<Lista_czesci> container = null;
        Class theClass = this.getClass();

        if (listaCzesciContainer.containsKey(theClass)) container = listaCzesciContainer.get(theClass);
        else {
            container = new ArrayList<>();
            listaCzesciContainer.put(theClass, container);
        }
        container.add(this);
    }

    public int getLiczbaCzesci() {
        return liczbaCzesci;
    }

    public Model getModel() {
        return model;
    }

    public Czesc getCzesc() {
        return czesc;
    }

    public static void deleteListaCzesci(Lista_czesci lista_czesci) {
        listaCzesciContainer.get(lista_czesci.getClass()).remove(lista_czesci);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/ListaCzesciSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(listaCzesciContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/ListaCzesciSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        listaCzesciContainer = (Hashtable) ois.readObject();
    }
}
