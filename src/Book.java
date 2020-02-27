public class Book {
    int id;
    int score;

    public Book(int id, int score) {
        this.id = id;
        this.score = score;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", score=" + score +
                '}';
    }
}
