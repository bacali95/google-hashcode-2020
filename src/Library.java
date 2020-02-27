import java.util.HashMap;
import java.util.Map;

public class Library {
    int id;
    int numberOfBooks;
    int signUpDays;
    int numberOfBooksPerDay;
    int booksScore;
    double score;
    Map<Integer, Book> books;

    public Library(int id, int numberOfBooks, int signUpDays, int numberOfBooksPerDay) {
        this.id = id;
        this.numberOfBooks = numberOfBooks;
        this.signUpDays = signUpDays;
        this.numberOfBooksPerDay = numberOfBooksPerDay;
        books = new HashMap<>();
    }

    public void calculateScore() {
        score = (booksScore * 1.0) / (signUpDays * 1.0);
    }

    @Override
    public String toString() {
        return "Library{" +
                "id=" + id +
                ", numberOfBooks=" + numberOfBooks +
                ", signUpDays=" + signUpDays +
                ", numberOfBooksPerDay=" + numberOfBooksPerDay +
                ", score=" + score +
                ", books=" + books +
                '}';
    }
}
