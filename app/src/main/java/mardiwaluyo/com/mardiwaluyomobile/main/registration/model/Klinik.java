package mardiwaluyo.com.mardiwaluyomobile.main.registration.model;

/**
 * Created by Wendra on 10/13/2017.
 */

public class Klinik {
    public Klinik() {

    }

    private String kodeKlinik;
    private String namaKlinik;

    public String getPraktek() {
        return praktek;
    }

    public void setPraktek(String praktek) {
        this.praktek = praktek;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    private String praktek;
    private String response;

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
