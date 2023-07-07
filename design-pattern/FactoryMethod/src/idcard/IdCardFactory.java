package idcard;

import framework.Factory;
import framework.Product;

public class IdCardFactory extends Factory {

    @Override
    protected Product createProduct(String owner) {
        return new IdCard(owner);
    }

    @Override
    protected void registerProduct(Product product) {
        System.out.println(product + "를 등록했습니다.");
    }
}
