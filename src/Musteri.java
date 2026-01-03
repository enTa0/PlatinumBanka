import java.util.ArrayList;

public class Musteri {
    public String adSoyad;
    public String tcNo;
    public String sifre; // YENİ: Şifre alanı
    public ArrayList<Hesap> hesaplar;

    public Musteri(String adSoyad, String tcNo, String sifre) {
        this.adSoyad = adSoyad;
        this.tcNo = tcNo;
        this.sifre = sifre;
        this.hesaplar = new ArrayList<>();
    }

    public void hesapEkle(Hesap hsp) {
        hesaplar.add(hsp);
    }
}