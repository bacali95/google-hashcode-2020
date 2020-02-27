import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Solution {

    public static void main(String[] args) throws IOException {
        File dir = new File("./files/in");
        ProcessBuilder builder = new ProcessBuilder("zip", "-r", "files/source", "src");
        builder.directory(new File("."));
        builder.start();
        List<File> files = Arrays.stream(Objects.requireNonNull(dir.listFiles()))
                .filter(file -> file.getName().endsWith(".txt"))
                .sorted(Comparator.comparingInt(f -> f.getName().charAt(0)))
                .collect(Collectors.toList());
        for (File file : files) {
            BufferedReader in = new BufferedReader(new FileReader(file));
            PrintWriter out = new PrintWriter(new FileWriter("./files/out/" + file.getName().replace(".txt", ".ans")));
            Solution sol = new Solution();
            sol.run(in, out);
            System.out.println(String.format("Input '%s' Done!", file.getName()));
            in.close();
            out.close();
        }
    }

    private void run(BufferedReader in, PrintWriter out) throws IOException {
        int[] line = stringArrayToIntArray(in.readLine().split(" "));
        int books = line[0];
        int libraries = line[1];
        int days = line[2];
        line = stringArrayToIntArray(in.readLine().split(" "));
        Map<Integer, Book> booksMap = new HashMap<>();
        for (int i = 0; i < line.length; i++) {
            booksMap.put(i, new Book(i, line[i]));
        }
        Map<Integer, Library> librariesMap = new HashMap<>();
        for (int i = 0; i < libraries; i++) {
            line = stringArrayToIntArray(in.readLine().split(" "));
            Library library = new Library(i, line[0], line[1], line[2]);
            line = stringArrayToIntArray(in.readLine().split(" "));
            for (int bookId : line) {
                library.books.put(bookId, booksMap.get(bookId));
                library.booksScore += booksMap.get(bookId).score;
            }
            library.calculateScore();
            librariesMap.put(i, library);
        }
        List<Library> librariesList = new ArrayList<>(librariesMap.values());
        librariesList.sort(Comparator.comparingDouble(lib -> -lib.score));
        int nbrLibrariesToSignUp = 0;
        List<Library> librariesToSignUp = new ArrayList<>();
        while (!librariesList.isEmpty() && days > librariesList.get(0).signUpDays) {
            Library currentLib = librariesList.get(0);
            librariesList.remove(0);
            if (currentLib.numberOfBooks == 0) {
                continue;
            }
            days -= currentLib.signUpDays;
            nbrLibrariesToSignUp++;
            librariesToSignUp.add(currentLib);
            for (Library library : librariesList) {
                currentLib.books.forEach((id, book) -> {
                    if (library.books.containsKey(id)) {
                        library.booksScore -= book.score;
                        library.books.remove(id);
                        library.numberOfBooks--;
                    }
                });
                library.calculateScore();
            }
            librariesList.sort(Comparator.comparingDouble(lib -> -lib.score));
        }
        out.println(nbrLibrariesToSignUp);
        for (Library library : librariesToSignUp) {
            if (nbrLibrariesToSignUp == 0) {
                break;
            }
            out.println(library.id + " " + library.numberOfBooks);
            List<Book> bl = new ArrayList<>(library.books.values());
            bl.sort(Comparator.comparingInt(b -> -b.score));
            bl.forEach(book -> out.print(book.id + " "));
            out.println();
            nbrLibrariesToSignUp--;
        }
    }

    static int[] stringArrayToIntArray(String[] in) {
        int[] res = new int[in.length];
        for (int i = 0; i < in.length; i++) {
            res[i] = Integer.parseInt(in[i]);
        }
        return res;
    }
}
