package sample;

import java.time.LocalDate;
import java.util.*;

public class Inzynier extends Pracownik implements IObjectPlus {
    private List<UprawnienieInzynier> uprawnienieInzynier = new ArrayList<>();

    private Map<String, List<IObjectPlus>> map = new HashMap<>();
    private List<String> rolesList = new LinkedList<>();
    public static final String ROLE_WORKS_ON = "Works on";
    public static final String ROLE_DIRECTOR_OF = "Director of";

    public Inzynier(String PESEL, String imie, String nazwisko, int dodatekSpecjalizacyjny) throws Exception {
        super(PESEL, imie, nazwisko, dodatekSpecjalizacyjny);
    }

    public void addUprawnienieInzynier(LocalDate wazneOd, LocalDate wazneDo, Uprawnienie uprawnienie) {
        UprawnienieInzynier uprawnienieInz = new UprawnienieInzynier(wazneOd, wazneDo, uprawnienie, this);
        uprawnienieInzynier.add(uprawnienieInz);
        uprawnienie.getUprawnienieInzynier().add(uprawnienieInz);
    }

    public List<UprawnienieInzynier> getUprawnienieInzynier() {
        return uprawnienieInzynier;
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
        if (rolesList.contains(stringA)) if (isXorLink()) throw new Exception("There is a link for rule "+stringA+"!");
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
}
