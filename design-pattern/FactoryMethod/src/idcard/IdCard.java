package idcard;

import framework.Product;

public class IdCard extends Product {
    private String owner;

    IdCard(String owner) {
        System.out.println(owner + "의 신분증을 발급합니다.");
        this.owner = owner;
    }

    @Override
    public void use() {
        System.out.println(owner + "의 신분증을 사용합니다.");
    }

    @Override
    public String toString() {
        return "[IdCard: " + owner + "]";
    }

    public String getOwner() {
        return owner;
    }
}
