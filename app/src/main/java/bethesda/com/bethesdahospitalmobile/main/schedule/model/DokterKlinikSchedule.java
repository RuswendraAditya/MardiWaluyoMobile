package bethesda.com.bethesdahospitalmobile.main.schedule.model;

/**
 * Created by Wendra on 10/23/2017.
 */

public class DokterKlinikSchedule {

    private String namaDokter;
    private String hari;
    private String jam_dari;
    private String jam_selesai;

    private String namaKlinik;

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getHari() {
        return hari;
    }

    public void setHari(String hari) {
        this.hari = hari;
    }

    public String getJam_dari() {
        return jam_dari;
    }

    public void setJam_dari(String jam_dari) {
        this.jam_dari = jam_dari;
    }

    public String getJam_selesai() {
        return jam_selesai;
    }

    public void setJam_selesai(String jam_selesai) {
        this.jam_selesai = jam_selesai;
    }

    public String getNamaKlinik() {
        return namaKlinik;
    }

    public void setNamaKlinik(String namaKlinik) {
        this.namaKlinik = namaKlinik;
    }

}
