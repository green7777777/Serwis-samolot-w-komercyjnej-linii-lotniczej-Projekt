package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class SamolotOdbywalSerwis implements Serializable {
    private LocalDate dataOd;
    private LocalDate dataDo;
    private String zakres;
    private Samolot samolot;
    private Serwis serwis;

    private static Map<Class, List<SamolotOdbywalSerwis>> samolotOdbywalSerwisContainer = new Hashtable<>();

    public SamolotOdbywalSerwis(LocalDate dataOd, String zakres, Samolot samolot, Serwis serwis) {
        this.dataOd = dataOd;
        this.dataDo = null;
        this.zakres = zakres;
        this.samolot = samolot;
        this.serwis = serwis;
        List<SamolotOdbywalSerwis> container = null;
        Class theClass = this.getClass();

        if (samolotOdbywalSerwisContainer.containsKey(theClass)) container = samolotOdbywalSerwisContainer.get(theClass);
        else {
            container = new ArrayList<>();
            samolotOdbywalSerwisContainer.put(theClass, container);
        }
        container.add(this);
    }

    public LocalDate getDataOd() {
        return dataOd;
    }

    public LocalDate getDataDo() {
        return dataDo;
    }

    public String getZakres() {
        return zakres;
    }

    public Samolot getSamolot() {
        return samolot;
    }

    public Serwis getSerwis() {
        return serwis;
    }

    public void setSamolot(Samolot samolot) {
        this.samolot = samolot;
    }

    public void setSerwis(Serwis serwis) {
        this.serwis = serwis;
    }

    public void setDataDo(LocalDate dataDo) {
        this.dataDo = dataDo;
        LocalDate last = dataDo;
        for (SamolotOdbywalSerwis sos:this.samolot.getSamolotOdbywalSerwis()) {
            if (sos.getDataDo()!=null&&last.isBefore(sos.getDataDo())) last = sos.getDataDo();
        }
        this.samolot.setDataOstatniegoSerwisu(last);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/SamolotOdbywalSerwisSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(samolotOdbywalSerwisContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/SamolotOdbywalSerwisSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        samolotOdbywalSerwisContainer = (Hashtable) ois.readObject();
    }
}
