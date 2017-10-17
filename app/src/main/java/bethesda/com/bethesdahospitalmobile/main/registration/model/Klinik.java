package bethesda.com.bethesdahospitalmobile.main.registration.model;

/**
 * Created by Wendra on 10/13/2017.
 */

public class Klinik {

    private String kodeKlinik;
    private String namaKlinik;

    public Klinik(String kodeKlinik, String namaKlinik) {
        this.kodeKlinik = kodeKlinik;
        this.namaKlinik = namaKlinik;
    }

    public String getKodeKlinik() {
        return kodeKlinik;
    }

    public void setKodeKlinik(String kodeKlinik) {
        this.kodeKlinik = kodeKlinik;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

}
