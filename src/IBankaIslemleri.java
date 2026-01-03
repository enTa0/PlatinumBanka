public interface IBankaIslemleri {
    void paraYatir(double miktar);
    void paraCek(double miktar);
    boolean transferYap(Hesap aliciHesap, double miktar);
}