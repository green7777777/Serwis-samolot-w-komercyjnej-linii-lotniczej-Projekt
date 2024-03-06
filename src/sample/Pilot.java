package sample;

public class Pilot extends Pracownik{
    private int wylataneGodziny;

    public Pilot(String PESEL, String imie, String nazwisko, int dodatekSpecjalizacyjny, int wylataneGodziny) throws Exception {
        super(PESEL, imie, nazwisko, dodatekSpecjalizacyjny);
        this.wylataneGodziny = wylataneGodziny;
    }

    public int getWylataneGodziny() {
        return wylataneGodziny;
    }

    public void setWylataneGodziny(int wylataneGodziny) {
        this.wylataneGodziny = wylataneGodziny;
    }
}
