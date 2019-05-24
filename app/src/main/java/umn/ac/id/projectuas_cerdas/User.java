package umn.ac.id.projectuas_cerdas;

public class User {
    private String nama;
    private String email;
    private String password;
    private String phone;
    private String occupation;
    private String favorite;
    private String type;
    private String idx;

    public User(String nama, String email, String phone, String occupation, String favorite, String type) {
        this.nama = nama;
        this.email = email;
        this.phone = phone;
        this.occupation = occupation;
        this.favorite = favorite;
        this.type = type;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdx() {
        return idx;
    }

    public void setIdx(String idx) {
        this.idx = idx;
    }
}
