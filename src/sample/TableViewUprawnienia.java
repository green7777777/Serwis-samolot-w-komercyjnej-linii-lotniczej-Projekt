package sample;

import java.time.LocalDate;

public class TableViewUprawnienia {
    private String nazwa;
    private LocalDate wazneOd;
    private LocalDate wazneDo;

    public TableViewUprawnienia(String nazwa, LocalDate wazneOd, LocalDate wazneDo) {
        this.nazwa = nazwa;
        this.wazneOd = wazneOd;
        this.wazneDo = wazneDo;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public LocalDate getWazneOd() {
        return wazneOd;
    }

    public void setWazneOd(LocalDate wazneOd) {
        this.wazneOd = wazneOd;
    }

    public LocalDate getWazneDo() {
        return wazneDo;
    }

    public void setWazneDo(LocalDate wazneDo) {
        this.wazneDo = wazneDo;
    }
}
