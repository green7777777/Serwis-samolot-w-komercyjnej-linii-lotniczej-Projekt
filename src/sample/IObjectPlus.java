package sample;

import java.util.*;

public interface IObjectPlus {

    void addLink(String stringA, String stringB, IObjectPlus object) throws Exception;

    boolean isLink(String stringA, IObjectPlus object);

    void addXorRole(String roleName);

    boolean isXorLink();

    void addXorLink(String stringA, String stringB, IObjectPlus object) throws Exception;

    void showLinks(String string);

    Map<String, List<IObjectPlus>> getMap();
}
