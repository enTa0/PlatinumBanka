import java.util.ArrayList;

public abstract class Hesap implements IBankaIslemleri {
    protected String hesapNo;
    protected double bakiye;
    public String tur;
    protected ArrayList<String> islemGecmisi;


    public Hesap(String hesapNo, double baslangicBakiye, String tur) {
        this.hesapNo = hesapNo;
        this.bakiye = baslangicBakiye;
        this.tur = tur;
        this.islemGecmisi = new ArrayList<>();
    }

    public double getBakiye() { return bakiye; }
    public String getHesapNo() { return hesapNo; }

    protected void logEkle(String mesaj) {
        islemGecmisi.add(mesaj);
        System.out.println(">> " + mesaj);
    }
}