public class VadesizHesap extends Hesap {

    public VadesizHesap(String hesapNo, double bakiye) {
        super(hesapNo, bakiye, "VADESIZ");
    }

    @Override
    public void paraYatir(double miktar) {
        this.bakiye += miktar;
        logEkle("Vadesiz Hesaba " + miktar + " TL yatırıldı. Yeni Bakiye: " + bakiye);
    }

    @Override
    public void paraCek(double miktar) {
        if (bakiye >= miktar) {
            this.bakiye -= miktar;
            logEkle("Vadesiz Hesaptan " + miktar + " TL çekildi. Kalan: " + bakiye);
        } else {
            System.out.println(">> HATA: Yetersiz Bakiye!");
        }
    }

    @Override
    public boolean transferYap(Hesap alici, double miktar) {
        if (bakiye >= miktar) {
            this.bakiye -= miktar;
            alici.paraYatir(miktar);
            logEkle("Transfer yapıldı: " + miktar + " TL -> " + alici.getHesapNo());
            return true;
        }
        return false;
    }
}