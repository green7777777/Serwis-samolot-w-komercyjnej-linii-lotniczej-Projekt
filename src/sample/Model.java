package sample;

import java.io.*;
import java.util.*;

public class Model implements Serializable {
    private String model;
    private String producent;

    private List<Samolot> samoloty = new ArrayList<>();
    private List<Lista_czesci> listaCzesci = new ArrayList<>();
    private static Set<Samolot> wszystkieSamoloty = new HashSet<>();

    private static List<String> modele = new ArrayList<>();

    private static Map<Class, List<Model>> modelContainer = new Hashtable<>();

    public Model(String model, String producent) throws Exception {
        setModel(model);
        this.producent = producent;
        List<Model> container = null;
        Class theClass = this.getClass();

        if (modelContainer.containsKey(theClass)) container = modelContainer.get(theClass);
        else {
            container = new ArrayList<>();
            modelContainer.put(theClass, container);
        }
        container.add(this);
    }

    public void addCzesc(Czesc czesc, int liczbaCzesci) {
        Lista_czesci lista_czesci = new Lista_czesci(liczbaCzesci, this, czesc);
        this.listaCzesci.add(lista_czesci);
        czesc.getListaCzesci().add(lista_czesci);
    }

    public List<Lista_czesci> getListaCzesci() {
        return listaCzesci;
    }

    public void addSamolot(Samolot samolot) throws Exception {
        if (!samoloty.contains(samolot)) {
            if (wszystkieSamoloty.contains(samolot))
                throw new Exception("Ten samolot jest już połaczony ze swoim modelem!");
            samoloty.add(samolot);
            wszystkieSamoloty.add(samolot);
        }
    }

    public List<Samolot> getSamoloty() {
        return samoloty;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) throws Exception {
        if (modele.contains(model)) throw new Exception("Taki model już istnieje!");
        this.model = model;
        modele.add(model);
    }

    public static void deleteModel(Model model) throws IOException, ClassNotFoundException {
        for (Samolot s:model.getSamoloty()) {
            s.remove();
            Samolot.deleteSamolot(s);
        }
        modelContainer.get(model.getClass()).remove(model);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/ModelSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(modelContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/ModelSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        modelContainer = (Hashtable) ois.readObject();
    }
}
