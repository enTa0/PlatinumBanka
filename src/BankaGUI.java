import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class BankaGUI extends JFrame {
    private BankaYonetim banka;
    private Musteri aktifMusteri;

    // --- RENK PALETİ ---
    private final Color COLOR_PRIMARY = new Color(44, 62, 80);
    private final Color COLOR_ACCENT = new Color(52, 152, 219);
    private final Color COLOR_SUCCESS = new Color(39, 174, 96);
    private final Color COLOR_WARNING = new Color(243, 156, 18);
    private final Color COLOR_BG = new Color(236, 240, 241);
    private final Color COLOR_TEXT = new Color(52, 73, 94);

    private JPanel mainPanel;
    private CardLayout cardLayout;

    public BankaGUI() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {}

        banka = new BankaYonetim();

        setTitle("Platinum Bank - Dijital Sube");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(girisPaneliOlustur(), "GIRIS");
        mainPanel.add(kayitPaneliOlustur(), "KAYIT");

        add(mainPanel);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                banka.verileriKaydet();
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton btn = new JButton(text);
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // --- 1. GİRİŞ PANELİ ---
    private JPanel girisPaneliOlustur() {
        JPanel panel = new JPanel(null);
        panel.setBackground(COLOR_PRIMARY);

        JPanel card = new JPanel(null);
        card.setBounds(300, 100, 400, 380);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 1));

        JLabel title = new JLabel("★ PLATINUM BANK", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(COLOR_PRIMARY);
        title.setBounds(0, 30, 400, 40);
        card.add(title);

        JLabel subtitle = new JLabel("Hosgeldiniz, lutfen giris yapin.", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitle.setForeground(Color.GRAY);
        subtitle.setBounds(0, 70, 400, 20);
        card.add(subtitle);

        JLabel l1 = new JLabel("TC Kimlik No");
        l1.setBounds(50, 110, 300, 20);
        l1.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(l1);

        JTextField tTc = new JTextField();
        tTc.setBounds(50, 135, 300, 35);
        tTc.setFont(new Font("SansSerif", Font.PLAIN, 14));
        card.add(tTc);

        JLabel l2 = new JLabel("Sifre");
        l2.setBounds(50, 180, 300, 20);
        l2.setFont(new Font("SansSerif", Font.BOLD, 12));
        card.add(l2);

        JPasswordField tPass = new JPasswordField();
        tPass.setBounds(50, 205, 300, 35);
        card.add(tPass);

        JButton btnGiris = createStyledButton("Giris Yap »", COLOR_SUCCESS);
        btnGiris.setBounds(50, 270, 300, 40);
        card.add(btnGiris);

        JLabel lblNoAcc = new JLabel("Hesabiniz yok mu?");
        lblNoAcc.setBounds(90, 320, 120, 30);
        card.add(lblNoAcc);

        JButton btnKayit = new JButton("Kayit Ol");
        btnKayit.setBorderPainted(false);
        btnKayit.setContentAreaFilled(false);
        btnKayit.setForeground(COLOR_ACCENT);
        btnKayit.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnKayit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnKayit.setBounds(200, 320, 100, 30);
        card.add(btnKayit);

        panel.add(card);

        btnGiris.addActionListener(e -> {
            String tc = tTc.getText();
            String pass = new String(tPass.getPassword());

            // --- ADMİN GİRİŞİ KONTROLÜ ---
            if(tc.equals("admin") && pass.equals("admin123")) {
                mainPanel.add(adminPaneliOlustur(), "ADMIN");
                cardLayout.show(mainPanel, "ADMIN");
                tTc.setText(""); tPass.setText("");
                return;
            }

            Musteri m = banka.musteriBul(tc);
            if (m != null && m.sifre.equals(pass)) {
                aktifMusteri = m;
                mainPanel.add(dashboardPaneliOlustur(), "DASHBOARD");
                cardLayout.show(mainPanel, "DASHBOARD");
                tTc.setText(""); tPass.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Hatali Bilgiler!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnKayit.addActionListener(e -> cardLayout.show(mainPanel, "KAYIT"));

        return panel;
    }

    // --- 2. KAYIT PANELİ ---
    private JPanel kayitPaneliOlustur() {
        JPanel panel = new JPanel(null);
        panel.setBackground(COLOR_BG);

        JPanel card = new JPanel(null);
        card.setBounds(300, 80, 400, 420);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createLineBorder(new Color(200,200,200), 1));

        JLabel title = new JLabel("✚ Yeni Hesap Olustur", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(COLOR_PRIMARY);
        title.setBounds(0, 30, 400, 40);
        card.add(title);

        JTextField tAd = new JTextField();
        addFormItem(card, "Ad Soyad:", tAd, 90);

        JTextField tTc = new JTextField();
        addFormItem(card, "TC Kimlik No:", tTc, 150);

        JPasswordField tPass = new JPasswordField();
        addFormItem(card, "Sifre:", tPass, 210);

        JButton btnKaydet = createStyledButton("✓ Kaydi Tamamla", COLOR_PRIMARY);
        btnKaydet.setBounds(50, 290, 300, 40);
        card.add(btnKaydet);

        JButton btnGeri = new JButton("« Giris Ekranina Don");
        btnGeri.setBounds(50, 340, 300, 30);
        btnGeri.setBorderPainted(false);
        btnGeri.setContentAreaFilled(false);
        btnGeri.setForeground(Color.GRAY);
        card.add(btnGeri);

        panel.add(card);

        btnKaydet.addActionListener(e -> {
            String ad = tAd.getText();
            String tc = tTc.getText();
            String pass = new String(tPass.getPassword());

            if(ad.isEmpty() || tc.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tum alanlari doldurun."); return;
            }

            banka.musteriEkle(ad, tc, pass);
            Musteri m = banka.musteriBul(tc);
            m.hesapEkle(new VadesizHesap("TR-" + (int)(Math.random()*1000), 0));
            banka.verileriKaydet();

            JOptionPane.showMessageDialog(this, "Tebrikler! Kayit basarili.");
            cardLayout.show(mainPanel, "GIRIS");
        });

        btnGeri.addActionListener(e -> cardLayout.show(mainPanel, "GIRIS"));
        return panel;
    }

    private void addFormItem(JPanel p, String label, JComponent comp, int y) {
        JLabel l = new JLabel(label);
        l.setFont(new Font("SansSerif", Font.BOLD, 12));
        l.setBounds(50, y, 300, 20);
        p.add(l);
        comp.setBounds(50, y+25, 300, 35);
        comp.setFont(new Font("SansSerif", Font.PLAIN, 14));
        p.add(comp);
    }

    // --- 3. DASHBOARD (MÜŞTERİ) ---
    private JPanel dashboardPaneliOlustur() {
        JPanel panel = new JPanel(new BorderLayout());

        // SOL MENÜ
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(7, 1, 0, 10));
        sidebar.setBackground(COLOR_PRIMARY);
        sidebar.setPreferredSize(new Dimension(230, 600));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblUser = new JLabel("● " + aktifMusteri.adSoyad, SwingConstants.CENTER);
        lblUser.setForeground(Color.WHITE);
        lblUser.setFont(new Font("SansSerif", Font.BOLD, 14));
        sidebar.add(lblUser);

        JButton btnYatir = createStyledButton("✚ Para Yatir", COLOR_ACCENT);
        JButton btnCek = createStyledButton("▬ Para Cek", COLOR_ACCENT);
        JButton btnHavale = createStyledButton("➤ Havale Yap", COLOR_ACCENT);
        JButton btnYeni = createStyledButton("★ Hesap Ac", COLOR_ACCENT);
        JButton btnCikis = createStyledButton("✖ Cikis", new Color(192, 57, 43));

        sidebar.add(btnYatir);
        sidebar.add(btnCek);
        sidebar.add(btnHavale);
        sidebar.add(btnYeni);
        sidebar.add(new JLabel(""));
        sidebar.add(btnCikis);

        panel.add(sidebar, BorderLayout.WEST);

        // İÇERİK
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel("Hesap Ozetiniz", SwingConstants.LEFT);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblTitle.setForeground(COLOR_TEXT);
        contentPanel.add(lblTitle, BorderLayout.NORTH);

        DefaultListModel<String> listModel = new DefaultListModel<>();
        guncelleHesapListesi(listModel);

        JList<String> hesapListesi = new JList<>(listModel);
        hesapListesi.setFont(new Font("Monospaced", Font.PLAIN, 15));
        hesapListesi.setFixedCellHeight(40);

        contentPanel.add(new JScrollPane(hesapListesi), BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        // --- İŞLEVLER ---

        btnYatir.addActionListener(e -> {
            if(hesapSeciliMi(hesapListesi)) {
                String miktarStr = JOptionPane.showInputDialog(this, "Yatirilacak Tutar (TL):");
                if(miktarStr != null) {
                    try {
                        double miktar = Double.parseDouble(miktarStr);
                        aktifMusteri.hesaplar.get(hesapListesi.getSelectedIndex()).paraYatir(miktar);
                        banka.verileriKaydet();
                        banka.genelLogEkle(aktifMusteri.adSoyad + " GUI: yatirdi " + miktar);
                        guncelleHesapListesi(listModel);
                        JOptionPane.showMessageDialog(this, "Islem Basarili! ✓");
                    } catch(Exception ex) { errorMsg("Gecersiz Tutar!"); }
                }
            }
        });

        btnCek.addActionListener(e -> {
            if(hesapSeciliMi(hesapListesi)) {
                int idx = hesapListesi.getSelectedIndex();
                Hesap secilenHesap = aktifMusteri.hesaplar.get(idx);

                if(secilenHesap.tur.equals("YATIRIM")) {
                    errorMsg("⚠ Yatirim hesabindan vade dolmadan para cekilemez!");
                    return;
                }

                String miktarStr = JOptionPane.showInputDialog(this, "Cekilecek Tutar (TL):");
                if(miktarStr != null) {
                    try {
                        double miktar = Double.parseDouble(miktarStr);
                        double bakiyeOnce = secilenHesap.getBakiye();
                        secilenHesap.paraCek(miktar);

                        if(secilenHesap.getBakiye() < bakiyeOnce) {
                            banka.verileriKaydet();
                            banka.genelLogEkle(aktifMusteri.adSoyad + " GUI: cekti " + miktar);
                            guncelleHesapListesi(listModel);
                            JOptionPane.showMessageDialog(this, "Islem Basarili! ✓");
                        } else {
                            errorMsg("Bakiye Yetersiz!");
                        }
                    } catch(Exception ex) { errorMsg("Gecersiz Tutar!"); }
                }
            }
        });

        btnHavale.addActionListener(e -> {
            if(hesapSeciliMi(hesapListesi)) {
                String aliciTc = JOptionPane.showInputDialog("Alici TC No:");
                if(aliciTc == null) return;
                String aliciNo = JOptionPane.showInputDialog("Alici Hesap No (Orn: TR-123):");
                if(aliciNo == null) return;

                Hesap alici = banka.hesapBul(aliciTc, aliciNo);
                if(alici == null) { errorMsg("Alici hesap bulunamadi!"); return; }

                String miktarStr = JOptionPane.showInputDialog("Gonderilecek Tutar:");
                if(miktarStr != null) {
                    try {
                        double tutar = Double.parseDouble(miktarStr);
                        boolean ok = aktifMusteri.hesaplar.get(hesapListesi.getSelectedIndex()).transferYap(alici, tutar);
                        if(ok) {
                            banka.verileriKaydet();
                            banka.genelLogEkle("GUI TRANSFER: " + aktifMusteri.adSoyad + " -> " + aliciTc);
                            guncelleHesapListesi(listModel);
                            JOptionPane.showMessageDialog(this, "Transfer Gonderildi! ➤");
                        } else { errorMsg("Bakiye Yetersiz!"); }
                    } catch(Exception ex) { errorMsg("Gecersiz Tutar!"); }
                }
            }
        });

        btnYeni.addActionListener(e -> {
            String[] opts = {"Vadesiz Hesap", "Yatirim Hesabi"};
            int secim = JOptionPane.showOptionDialog(this, "Hesap Turu", "Yeni Hesap", 0, 1, null, opts, opts[0]);
            if(secim != -1) {
                String no = "TR-" + (int)(Math.random() * 10000);
                if(secim == 0) aktifMusteri.hesapEkle(new VadesizHesap(no, 0));
                else aktifMusteri.hesapEkle(new YatirimHesabi(no));
                banka.verileriKaydet();
                guncelleHesapListesi(listModel);
            }
        });

        btnCikis.addActionListener(e -> {
            aktifMusteri = null;
            cardLayout.show(mainPanel, "GIRIS");
        });

        return panel;
    }

    // --- 4. YENİ EKRAN: ADMIN PANELİ (TABLOLU) ---
    private JPanel adminPaneliOlustur() {
        JPanel panel = new JPanel(new BorderLayout());

        // Üst Başlık
        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER));
        header.setBackground(COLOR_PRIMARY);
        header.setBorder(new EmptyBorder(15, 0, 15, 0));
        JLabel lblBaslik = new JLabel("YÖNETİCİ PANELİ / MÜŞTERİ LİSTESİ");
        lblBaslik.setForeground(Color.WHITE);
        lblBaslik.setFont(new Font("SansSerif", Font.BOLD, 18));
        header.add(lblBaslik);
        panel.add(header, BorderLayout.NORTH);

        // Tablo Verileri
        String[] kolonlar = {"Ad Soyad", "TC No", "Sifre", "Hesap Sayisi", "Toplam Bakiye (TL)"};

        // Bankadan müşteri listesini al
        ArrayList<Musteri> musteriler = banka.getMusteriler();

        Object[][] veri = new Object[musteriler.size()][5];
        double genelToplamMevduat = 0;

        for(int i=0; i < musteriler.size(); i++) {
            Musteri m = musteriler.get(i);
            double kisiToplamPara = 0;
            for(Hesap h : m.hesaplar) kisiToplamPara += h.getBakiye();

            veri[i][0] = m.adSoyad;
            veri[i][1] = m.tcNo;
            veri[i][2] = m.sifre;
            veri[i][3] = m.hesaplar.size();
            veri[i][4] = kisiToplamPara;

            genelToplamMevduat += kisiToplamPara;
        }

        // Tablo Oluştur
        DefaultTableModel model = new DefaultTableModel(veri, kolonlar);
        JTable table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        table.getTableHeader().setBackground(Color.LIGHT_GRAY);

        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Alt Bilgi (Toplam Para ve Çıkış)
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(10, 20, 10, 20));
        footer.setBackground(Color.WHITE);

        JLabel lblToplam = new JLabel("BANKADAKİ GENEL TOPLAM MEVDUAT: " + genelToplamMevduat + " TL");
        lblToplam.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblToplam.setForeground(new Color(192, 57, 43));
        footer.add(lblToplam, BorderLayout.WEST);

        JButton btnCikis = createStyledButton("Çıkış Yap", COLOR_PRIMARY);
        btnCikis.setPreferredSize(new Dimension(150, 40));
        btnCikis.addActionListener(e -> cardLayout.show(mainPanel, "GIRIS"));
        footer.add(btnCikis, BorderLayout.EAST);

        panel.add(footer, BorderLayout.SOUTH);

        return panel;
    }

    private void guncelleHesapListesi(DefaultListModel<String> model) {
        model.clear();
        for(Hesap h : aktifMusteri.hesaplar) {
            String renk = h.tur.equals("VADESIZ") ? "#2980b9" : "#27ae60";
            String veri = String.format("<html><b style='color:%s'>[%s]</b> %s <span style='float:right'>| <b>%.2f TL</b></span></html>",
                    renk, h.tur, h.getHesapNo(), h.getBakiye());
            model.addElement(veri);
        }
    }

    private boolean hesapSeciliMi(JList list) {
        if(list.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Lutfen islem yapilacak hesabi secin!", "Uyari", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    private void errorMsg(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Hata", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankaGUI().setVisible(true));
    }
}