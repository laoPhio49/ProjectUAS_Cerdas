package umn.ac.id.projectuas_cerdas;

public class Kos {
    private String id;
    private String name;
    private String alamat;
    private String gambar;
    private String detail;
    private String jenis;
    private String pemilik;
    private String harga;
    private String kamarTersedia;
    private String jumlahKamar;
    public static int total;

    public Kos(String id, String name, String alamat, String gambar, String detail, String jenis,String pemilik, String harga, String kamarTersedia, String jumlahKamar) {
        this.id = id;
        this.name = name;
        this.alamat = alamat;
        this.gambar = gambar;
        this.detail = detail;
        this.jenis = jenis;
        this.pemilik = pemilik;
        this.harga = harga;
        this.kamarTersedia = kamarTersedia;
        this.jumlahKamar = jumlahKamar;
        total++;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }

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

    public String getPemilik() {
        return pemilik;
    }

    public String getHarga() {
        return harga;
    }

    public String getKamarTersedia() {
        return kamarTersedia;
    }

    public String getJumlahKamar() {
        return jumlahKamar;
    }

    public String getHargaString(){
        return String.valueOf(harga);
    }
}
