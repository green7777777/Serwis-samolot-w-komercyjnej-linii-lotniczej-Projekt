package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class UprawnienieInzynier implements Serializable {
    private LocalDate wazneOd;
    private LocalDate wazneDo;
    private Uprawnienie uprawnienie;
    private Inzynier inzynier;

    private static Map<Class, List<UprawnienieInzynier>> uprawnienieInzynierContainer = new Hashtable<>();

    public UprawnienieInzynier(LocalDate wazneOd, LocalDate wazneDo, Uprawnienie uprawnienie, Inzynier inzynier) {
        this.wazneOd = wazneOd;
        this.wazneDo = wazneDo;
        this.uprawnienie = uprawnienie;
        this.inzynier = inzynier;
        List<UprawnienieInzynier> container = null;
        Class theClass = this.getClass();

        if (uprawnienieInzynierContainer.containsKey(theClass)) container = uprawnienieInzynierContainer.get(theClass);
        else {
            container = new ArrayList<>();
            uprawnienieInzynierContainer.put(theClass, container);
        }
        container.add(this);
    }

    public LocalDate getWazneOd() {
        return wazneOd;
    }

    public LocalDate getWazneDo() {
        return wazneDo;
    }

    public Uprawnienie getUprawnienie() {
        return uprawnienie;
    }

    public Inzynier getInzynier() {
        return inzynier;
    }

    public void setWazneOd(LocalDate wazneOd) {
        this.wazneOd = wazneOd;
    }

    public void setWazneDo(LocalDate wazneDo) {
        this.wazneDo = wazneDo;
    }

    public void updateDaty(String wazneOdText, String wazneDoText) throws Exception {
        String[] wazneOdList = wazneOdText.split(":");
        LocalDate wazneOd = LocalDate.of(Integer.parseInt(wazneOdList[0]), Integer.parseInt(wazneOdList[1]), Integer.parseInt(wazneOdList[2]));
        String[] wazneDoList = wazneDoText.split(":");
        LocalDate wazneDo = LocalDate.of(Integer.parseInt(wazneDoList[0]), Integer.parseInt(wazneDoList[1]), Integer.parseInt(wazneDoList[2]));
        if (wazneOdList.length != 3 || wazneDoList.length != 3)
            throw new Exception("Zły format daty!");
        if (wazneOd.isBefore(this.wazneOd))
            throw new Exception("Nowa data początku uprawnienia nie może być przed ówczesną datą początku uprawnienia!");
        if (wazneDo.isBefore(this.wazneDo))
            throw new Exception("Nowa data upływu uprawnienia nie może być przed ówczesną datą upływu uprawnienia!");
        if (wazneDo.isBefore(wazneOd))
            throw new Exception("Nowa data nabycia uprawnienia nie może być przed nową datą upływu uprawnienia!");
        this.wazneOd = wazneOd;
        this.wazneDo = wazneDo;
    }

    private LocalDate dateFormatChecker(String data) throws Exception {
        String[] splitData = data.split(":");
        if (splitData.length != 3) throw new Exception("Nieprawidłowy format daty! Prawidłowy format to DD:MM:RRRR!");
        return LocalDate.of(Integer.parseInt(splitData[0]), Integer.parseInt(splitData[1]), Integer.parseInt(splitData[2]));
    }

    public static void deleteUprawnienieInzynier(UprawnienieInzynier uprawnienieInzynier) {
        uprawnienieInzynierContainer.get(uprawnienieInzynier.getClass()).remove(uprawnienieInzynier);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/UprawnienieInzynierSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(uprawnienieInzynierContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/UprawnienieInzynierSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        uprawnienieInzynierContainer = (Hashtable) ois.readObject();
    }
}
