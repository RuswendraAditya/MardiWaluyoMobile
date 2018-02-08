package bethesda.com.bethesdahospitalmobile.main.registration.model;

/**
 * Created by Wendra on 10/17/2017.
 */

public class Registration {

    private String noRM;
    private String kodeKlinik;
    private String kodeDokter;

    private String tglReg;
    public String getNoRM() {
        return noRM;
    }

    public void setNoRM(String noRM) {
        this.noRM = noRM;
    }

    public String getKodeKlinik() {
        return kodeKlinik;
    }

    public void setKodeKlinik(String kodeKlinik) {
        this.kodeKlinik = kodeKlinik;
    }

    public String getKodeDokter() {
        return kodeDokter;
    }

    public void setKodeDokter(String kodeDokter) {
        this.kodeDokter = kodeDokter;
    }

    public String getTglReg() {
        return tglReg;
    }

    public void setTglReg(String tglReg) {
        this.tglReg = tglReg;
    }

}
