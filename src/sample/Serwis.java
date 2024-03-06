package sample;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Serwis implements IObjectPlus, Serializable {
    private int numerSerwisu;
    private String nazwa;
    private Enum<StatusSerwis> status;
    private String notatkaSeriwsowa;
    private List<SamolotOdbywalSerwis> samolotOdbywalSerwis = new ArrayList<>();
    private List<Zamowienie> zamowienia = new ArrayList<>();

    private Map<String, List<IObjectPlus>> map = new HashMap<>();
    private List<String> rolesList = new LinkedList<>();
    public static final String ROLE_WORKS = "Works";
    public static final String ROLE_DIRECTS = "Directs";

    private static List<Integer> numerySerwisow = new ArrayList<>();

    private static Map<Class, List<Serwis>> serwisContainer = new Hashtable<>();

    public Serwis(int numerSerwisu, String nazwa) throws Exception {
        setNumerSerwisu(numerSerwisu);
        this.nazwa = nazwa;
        status = StatusSerwis.Nowy;
        List<Serwis> container = null;
        Class theClass = this.getClass();

        if (serwisContainer.containsKey(theClass)) container = serwisContainer.get(theClass);
        else {
            container = new ArrayList<>();
            serwisContainer.put(theClass, container);
        }
        container.add(this);
    }

    public void addSamolotOdbywalSerwis(LocalDate dataOd, String zakres, Samolot samolot) {
        SamolotOdbywalSerwis odbywalSerwis = new SamolotOdbywalSerwis(dataOd, zakres, samolot, this);
        this.samolotOdbywalSerwis.add(odbywalSerwis);
        samolot.getSamolotOdbywalSerwis().add(odbywalSerwis);
        this.status = StatusSerwis.W_trakcie;
    }

    public void addZamowienie(Zamowienie zamowienie) {
        zamowienia.add(zamowienie);
        zamowienie.setSerwis(this);
    }

    public void zakonczSerwis(LocalDate dataDo) {
        for (SamolotOdbywalSerwis sos: samolotOdbywalSerwis) sos.setDataDo(dataDo);
        status = StatusSerwis.Zakonczony;
    }

    public List<SamolotOdbywalSerwis> getSamolotOdbywalSerwis() {
        return samolotOdbywalSerwis;
    }

    public int getNumerSerwisu() {
        return numerSerwisu;
    }

    public void setNumerSerwisu(int numerSerwisu) throws Exception {
        if (numerySerwisow.contains(numerSerwisu)) throw new Exception("Serwis o takim numerze już istnieje!");
        this.numerSerwisu = numerSerwisu;
        numerySerwisow.add(numerSerwisu);
    }

    @Override
    public void addLink(String stringA, String stringB, IObjectPlus object) throws Exception {
        if (object == null) throw new Exception("Given object to link is null!");
        if (!map.containsKey(stringA)) map.put(stringA, new ArrayList<>());
        List<IObjectPlus> list = map.get(stringA);
        if (!list.isEmpty() && list.contains(object))
            throw new Exception("Link " + stringA + " to " + object + " already exists!");
        list.add(object);
        Map<String, List<IObjectPlus>> otherMap = object.getMap();
        if (!otherMap.containsKey(stringB)) otherMap.put(stringB, new ArrayList<>());
        List<IObjectPlus> otherList = otherMap.get(stringB);
        if (!otherList.isEmpty() && otherList.contains(this))
            throw new Exception("Link " + stringB + " to " + this + " already exists!");
        otherList.add(this);
    }

    public boolean isLink(String stringA, IObjectPlus object) {
        if (map.containsKey(stringA))
            return map.get(stringA).contains(object);
        else return false;
    }

    public void addXorRole(String roleName) {
        rolesList.add(roleName);
        map.put(roleName, new ArrayList<>());
    }

    public boolean isXorLink() {
        for (String role : rolesList) if (map.containsKey(role)) if (!map.get(role).isEmpty()) return true;
        return false;
    }

    public void addXorLink(String stringA, String stringB, IObjectPlus object) throws Exception {
        if (rolesList.contains(stringA))
            if (isXorLink()) throw new Exception("There is a link for rule " + stringA + "!");
        addLink(stringA, stringB, object);
    }

    public void showLinks(String string) {
        if (map.containsKey(string))
            for (int i = 0; i < map.get(string).size(); i++) System.out.println(string + " " + map.get(string).get(i));
        else System.out.println(string + " nothing");
    }

    public Map<String, List<IObjectPlus>> getMap() {
        return map;
    }

    public static void deleteSerwis(Serwis serwis) {
        serwisContainer.get(serwis.getClass()).remove(serwis);
    }

    public static void writeContainer() throws IOException {
        FileOutputStream fos = new FileOutputStream("saves/SerwisSaveFile.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(serwisContainer);
    }

    public static void readContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("saves/SerwisSaveFile.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        serwisContainer = (Hashtable) ois.readObject();
    }
}
