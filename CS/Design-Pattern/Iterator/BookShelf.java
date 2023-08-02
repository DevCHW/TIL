import java.util.Iterator;

public class BookShelf implements Iterable{
    private Book[] books;
    private int last = 0;

    public BookShelf(int size) {
        books = new Book[size];
    }

    public Book getBook(int index) {
        return books[index];
    }

    public int getLength() {
        return last;
    }

    public void appendBook(Book book) {
        if (last > books.length) {
            throw new RuntimeException("책장이 꽉 찼습니다.");
        }
        this.books[last] = book;
        last++;
    }

    @Override
    public Iterator createIterator() {
        return new BookShelfIterator(this);
    }
}
