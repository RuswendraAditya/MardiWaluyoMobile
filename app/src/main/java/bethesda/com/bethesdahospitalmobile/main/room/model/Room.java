package bethesda.com.bethesdahospitalmobile.main.room.model;

/**
 * Created by Rs Bethesda on 10/28/2017.
 */

public class Room {

    public Room(String kodeKelas, String namaKelas, Integer kamarKosong) {
        this.kodeKelas = kodeKelas;
        this.namaKelas = namaKelas;
        this.kamarKosong = kamarKosong;
    }

    private String kodeKelas;
    private String namaKelas;
    private Integer kamarKosong;

    public String getKodeKelas() {
        return kodeKelas;
    }

    public void setKodeKelas(String kodeKelas) {
        this.kodeKelas = kodeKelas;
    }

    public String getNamaKelas() {
        return namaKelas;
    }

    public void setNamaKelas(String namaKelas) {
        this.namaKelas = namaKelas;
    }

    public Integer getKamarKosong() {
        return kamarKosong;
    }

    public void setKamarKosong(Integer kamarKosong) {
        this.kamarKosong = kamarKosong;
    }

}
