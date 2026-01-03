import java.io.*;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankaYonetim {
    private ArrayList<Musteri> musteriler;

    public BankaYonetim() {
        this.musteriler = new ArrayList<>();
        verileriYukle();
    }

    // --- DOSYA YÖNETİMİ ---
    public void verileriKaydet() {
        try {
            // Müşterileri Yaz
            BufferedWriter bwM = new BufferedWriter(new FileWriter("musteriler.txt"));
            for (Musteri m : musteriler) {
                bwM.write(m.adSoyad + "," + m.tcNo + "," + m.sifre);
                bwM.newLine();
            }
            bwM.close();

            // Hesapları Yaz
            BufferedWriter bwH = new BufferedWriter(new FileWriter("hesaplar.txt"));
            for (Musteri m : musteriler) {
                for (Hesap h : m.hesaplar) {
                    bwH.write(m.tcNo + "," + h.getHesapNo() + "," + h.getBakiye() + "," + h.tur);
                    bwH.newLine();
                }
            }
            bwH.close();
        } catch (IOException e) {
            System.out.println("Dosya yazma hatası: " + e.getMessage());
        }
    }

    private void verileriYukle() {
        try {
            // Müşterileri Yükle
            File fMusteri = new File("musteriler.txt");
            if (fMusteri.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(fMusteri));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 3) {
                        musteriler.add(new Musteri(data[0], data[1], data[2]));
                    }
                }
                br.close();
            }

            // Hesapları Yükle
            File fHesap = new File("hesaplar.txt");
            if (fHesap.exists()) {
                BufferedReader br = new BufferedReader(new FileReader(fHesap));
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 4) {
                        Musteri sahip = musteriBul(data[0]);
                        if (sahip != null) {
                            String hNo = data[1];
                            double bakiye = Double.parseDouble(data[2]);
                            String tur = data[3];

                            if (tur.equals("VADESIZ")) {
                                sahip.hesapEkle(new VadesizHesap(hNo, bakiye));
                            } else {
                                sahip.hesapEkle(new YatirimHesabi(hNo, bakiye));
                            }
                        }
                    }
                }
                br.close();
            }
        } catch (IOException e) {
            System.out.println("Dosya okuma hatası: " + e.getMessage());
        }
    }

    public void genelLogEkle(String mesaj) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("hareketler.txt", true));
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            bw.write("[" + dtf.format(LocalDateTime.now()) + "] " + mesaj);
            bw.newLine();
            bw.close();
        } catch (IOException e) {}
    }
    //Müşteri ekleme
    public void musteriEkle(String ad, String tc, String sifre) {
        musteriler.add(new Musteri(ad, tc, sifre));
        verileriKaydet();
        genelLogEkle("Yeni Üye: " + ad + " (" + tc + ")");
    }

    public Musteri musteriBul(String tc) {
        for (Musteri m : musteriler) {
            if (m.tcNo.equals(tc)) return m;
        }
        return null;
    }


    // Verilen TC ve HesapNo'ya sahip hesap nesnesini bul
    public Hesap hesapBul(String tc, String hesapNo) {
        Musteri m = musteriBul(tc);
        if (m != null) {
            for (Hesap h : m.hesaplar) {
                if (h.getHesapNo().equals(hesapNo)) {
                    return h;
                }
            }
        }
        return null;
    }

    // --- ADMİN İÇİN RAPOR METODLARI ---
    public void tumMusterileriListele() {
        System.out.println("\n=== BANKA MÜŞTERİ LİSTESİ ===");
        for (Musteri m : musteriler) {
            System.out.println("- " + m.adSoyad + " (TC: " + m.tcNo + ") | Hesap Sayısı: " + m.hesaplar.size());
        }
    }

    public void bankadakiToplamPara() {
        double toplam = 0;
        for (Musteri m : musteriler) {
            for (Hesap h : m.hesaplar) {
                toplam += h.getBakiye();
            }
        }
        System.out.println("\n>> BANKADAKİ TOPLAM MEVDUAT: " + toplam + " TL");
    }

    public ArrayList<Musteri> getMusteriler() {
        return this.musteriler;
    }
}