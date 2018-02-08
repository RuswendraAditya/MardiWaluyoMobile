package bethesda.com.bethesdahospitalmobile.main.registration.model;

/**
 * Created by Wendra on 10/16/2017.
 */

public class Dokter {
    private int max;
    private String nid;
    private String namaDokter;
    private String hari_ini;
    private String praktek;
    private String response;

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getHari_ini() {
        return hari_ini;
    }

    public void setHari_ini(String hari_ini) {
        this.hari_ini = hari_ini;
    }

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

}
