import java.util.Iterator;

public class Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(10);

        Book book1 = new Book("Bilbe");
        Book book2 = new Book("Cinderella");
        Book book3 = new Book("Daddy-Long-Legs");

        bookShelf.appendBook(book1);
        bookShelf.appendBook(book2);
        bookShelf.appendBook(book3);

        System.out.println("현재 꽂혀있는 책 : " + bookShelf.getLength() + "권");

        Iterator iter = bookShelf.createIterator();

        while (iter.hasNext()) {
            Book book = (Book) iter.next();
            System.out.println(book.getName());
        }
    }
}
