package umn.ac.id.projectuas_cerdas;

public class Kos {
    private String name;
    private String alamat;
    private String gambar;
    private String detail;
    private String jenis;
    private int harga;
    private int kamarTersedia;
    private int jumlahKamar;
    public static int total;

    public Kos(String name, String alamat, String gambar, String detail, String jenis, int harga, int kamarTersedia, int jumlahKamar) {
        this.name = name;
        this.alamat = alamat;
        this.gambar = gambar;
        this.detail = detail;
        this.jenis = jenis;
        this.harga = harga;
        this.kamarTersedia = kamarTersedia;
        this.jumlahKamar = jumlahKamar;
        total++;
    }

    public String getName() {
        return name;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getGambar() {
        return gambar;
    }

    public String getDetail() {
        return detail;
    }

    public String getJenis() {
        return jenis;
    }

    public int getHarga() {
        return harga;
    }

    public int getKamarTersedia() {
        return kamarTersedia;
    }

    public int getJumlahKamar() {
        return jumlahKamar;
    }

    public String getHargaString(){
        return String.valueOf(harga);
    }
}
