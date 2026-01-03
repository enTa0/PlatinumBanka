public class YatirimHesabi extends Hesap {

    public YatirimHesabi(String hesapNo) {
        super(hesapNo, 0, "YATIRIM");
    }

    // 2. Dosyadan okunan hesap için
    public YatirimHesabi(String hesapNo, double bakiye) {
        super(hesapNo, bakiye, "YATIRIM");
    }

    @Override
    public void paraYatir(double miktar) {
        double bonus = miktar * 0.05; // %5 Bonus
        this.bakiye += (miktar + bonus);
        logEkle("Yatırım Hesabına (Bonuslu) yatırıldı: " + (miktar + bonus));
    }

    @Override
    public void paraCek(double miktar) {
        System.out.println(">> UYARI: Yatırım hesabından vade dolmadan para çekilemez!");
    }

    @Override
    public boolean transferYap(Hesap alici, double miktar) {
        return false;
    }
}