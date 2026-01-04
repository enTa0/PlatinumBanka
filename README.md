# 🏦 Platinum Banka Otomasyon Sistemi

Bu proje, **Yazılım İnşaası** dersi kapsamında geliştirilmiş; Nesne Yönelimli Programlama (OOP) prensiplerine uygun, katmanlı mimariye sahip ve verileri dosya sisteminde saklayan bir masaüstü bankacılık uygulamasıdır.

## 🚀 Proje Hakkında
**Platinum Banka Sistemi**, kullanıcıların temel bankacılık işlemlerini (para yatırma, çekme, havale) grafik arayüz (GUI) üzerinden yapabildiği, yöneticilerin (Admin) ise tüm banka verilerini tek bir panelden takip edebildiği kapsamlı bir otomasyon sistemidir.

Proje geliştirilirken **Clean Code** (Temiz Kod) prensiplerine ve **SOLID** kurallarına dikkat edilmiştir.

## 🛠️ Kullanılan Teknolojiler ve Mimari
* **Dil:** Java (JDK 17+)
* **Arayüz:** Java Swing (Modern Flat Design)
* **Veri Saklama:** File I/O (TXT Dosyaları) - *Veritabanı bağımsız*
* **Mimari:** Katmanlı Mimari (Layered Architecture)
    * **Presentation Layer:** `BankaGUI`
    * **Business Logic Layer:** `Musteri`, `Hesap`
    * **Data Access Layer:** `BankaYonetim`
    * **Interface:** `IBankaIslemleri` (Polymorphism)

## 📋 Özellikler (Features)

### 👤 Müşteri Paneli
* **Giriş & Kayıt Ol:** TC Kimlik No ve Şifre ile güvenli giriş. Kayıt olan her müşteriye otomatik "Vadesiz Hesap" hediyesi.
* **Hesap Yönetimi:** Vadesiz ve Yatırım hesaplarını görüntüleme.
* **Para Transferi (Havale):** Başka bir müşteriye TC ve Hesap No ile anında para gönderme.
* **Yatırım Kısıtı:** Yatırım hesaplarından "Vade Dolmadan" para çekilmesinin engellenmesi (Business Rule).
* **Bakiye İşlemleri:** Para yatırma ve çekme.

### 👮‍♂️ Yönetici (Admin) Paneli
* **Admin Girişi:** Özel yetkili giriş ekranı.
* **Müşteri Listesi:** Bankadaki tüm müşterileri, hesap sayılarını ve bakiyelerini detaylı tabloda görme.
* **Kasa Raporu:** Bankadaki toplam mevduat miktarını anlık görüntüleme.

## 📂 Proje Yapısı (File Structure)

```
PlatinumBank/
├── src/
│   ├── BankaGUI.java        # Kullanıcı Arayüzü ve Main (Başlatıcı)
│   ├── BankaYonetim.java    # Dosya işlemleri ve Veri Yönetimi
│   ├── IBankaIslemleri.java # (Interface) Banka işlem şablonu
│   ├── Musteri.java         # Müşteri nesnesi ve hesap listesi
│   ├── Hesap.java           # (Abstract) Temel hesap sınıfı
│   ├── VadesizHesap.java    # Standart hesap türü
│   └── YatirimHesabi.java   # Kısıtlı hesap türü (Polymorphism örneği)
└── docs/
    ├── Analiz_Raporu.docx    # Gereksinimler ve UML Diyagramları
    ├── Tasarim_Raporu.docx   # Mimari ve Teknik Detaylar
    └── Final_Raporu.docx     # Proje Sonuç Raporu ve Test Çıktıları

```

> **Not:** `musteriler.txt`, `hesaplar.txt` ve `hareketler.txt` gibi veri dosyaları program **ilk kez çalıştırıldığında otomatik olarak oluşturulur.** Manuel ekleme yapmanıza gerek yoktur.

## ⚙️ Kurulum ve Çalıştırma

1. Projeyi bilgisayarınıza indirin:
```bash
git clone https://github.com/enTa0/PlatinumBanka.git

```


2. Favori IDE'nizde (IntelliJ, Eclipse, VS Code) `src` klasörünü açın.
3. **`BankaGUI.java`** dosyasına sağ tıklayıp **Run** diyerek çalıştırın.
4. **Admin Girişi için:**
* **Kullanıcı Adı:** `admin`
* **Şifre:** `admin123`



## 👥 Grup Üyeleri (Katkıda Bulunanlar)

* **Enes Taha SAYGIN**
* **Efe Hamza BAYAV**
* **Mengüalp YILMAZ**
* **Talha Batın KOŞTAN**

---

*Yazılım İnşaası Dersi Final Projesi - 2026*

```

```
