import framework.Factory;
import framework.Product;
import idcard.IdCardFactory;

public class Main {
    public static void main(String[] args) {
        Factory factory = new IdCardFactory();
        Product card1 = factory.create("Hyunwoo Choi");
        Product card2 = factory.create("Josh");
        Product card3 = factory.create("Niko");

        card1.use();
        card2.use();
        card3.use();

        /**
         Hyunwoo Choi의 신분증을 발급합니다.
         [IdCard: Hyunwoo Choi]를 등록했습니다.
         Josh의 신분증을 발급합니다.
         [IdCard: Josh]를 등록했습니다.
         Niko의 신분증을 발급합니다.
         [IdCard: Niko]를 등록했습니다.
         Hyunwoo Choi의 신분증을 사용합니다.
         Josh의 신분증을 사용합니다.
         Niko의 신분증을 사용합니다.
         */
    }
}