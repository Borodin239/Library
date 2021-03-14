package tools;

public class Pair {
    private final String author;
    private final String bookName;

    public Pair(String first, String second) {
        this.author = first;
        this.bookName = second;
    }

    public String getAuthor() {
        return author;
    }

    public String getBookName() {
        return bookName;
    }

    @Override
    public String toString() {
        return "Author: " + author + '\n' + "Book name: " + bookName;
    }
}
