package sample;

public class Pracownik_administracji extends Pracownik{
    private int telefonKontaktowy;

    public Pracownik_administracji(String PESEL, String imie, String nazwisko, int dodatekSpecjalizacyjny, int telefonKontaktowy) throws Exception {
        super(PESEL, imie, nazwisko, dodatekSpecjalizacyjny);
        this.telefonKontaktowy = telefonKontaktowy;
    }

    public int getTelefonKontaktowy() {
        return telefonKontaktowy;
    }

    public void setTelefonKontaktowy(int telefonKontaktowy) {
        this.telefonKontaktowy = telefonKontaktowy;
    }
}
