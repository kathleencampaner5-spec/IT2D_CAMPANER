import dao.DatabaseConnection;
import dao.ScenarioDao;
import model.Book;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String LINE = "=".repeat(52);
    private static final Scanner scanner = new Scanner(System.in);
    private static final ScenarioDao dao = new ScenarioDao();

    public static void main(String[] args) {
        DatabaseConnection.initializeDatabase();

        boolean running = true;
        while (running) {
            printMenu();
            int choice = readChoice();
            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewCatalog();
                case 3 -> {
                    System.out.println("\nExiting system... Thank you!");
                    running = false;
                }
                default -> System.out.println("\nINVALID CHOICE: Please enter 1, 2, or 3.");
            }
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("\n" + LINE);
        System.out.println("              LIBRARY BOOK CATALOG");
        System.out.println(LINE);
        System.out.println("[1] Add New Book");
        System.out.println("[2] View Book Catalog");
        System.out.println("[3] Exit System");
        System.out.println(LINE);
        System.out.print("Enter choice: ");
    }

    // Reads a whole line, so no leftover newline corrupts later string input
    private static int readChoice() {
        String input = scanner.nextLine().trim();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private static void addBook() {
        System.out.println("\n--- ADD NEW BOOK ---");
        String title = readNonEmpty("Enter Title: ");
        String author = readNonEmpty("Enter Author: ");

        String isbn;
        while (true) {
            System.out.print("Enter ISBN: ");
            isbn = scanner.nextLine().trim();
            if (isbn.matches("\\d{13}")) break;
            System.out.println("ERROR: ISBN must be exactly 13 digits.");
        }

        String status;
        while (true) {
            System.out.print("Enter Status (AVAILABLE / BORROWED): ");
            status = scanner.nextLine().trim().toUpperCase();
            if (status.equals("AVAILABLE") || status.equals("BORROWED")) break;
            System.out.println("ERROR: Status must be AVAILABLE or BORROWED.");
        }

        if (dao.addBook(new Book(title, author, isbn, status))) {
            System.out.println("\nSUCCESS: Book cataloged into database!");
        } else {
            System.out.println("\nFAILED: Could not save the book.");
        }
    }

    private static String readNonEmpty(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = scanner.nextLine().trim();
            if (!value.isEmpty()) return value;
            System.out.println("ERROR: This field cannot be empty.");
        }
    }

    private static void viewCatalog() {
        List<Book> books = dao.getAllBooks();
        String border = "+" + "-".repeat(4) + "+" + "-".repeat(27) + "+" + "-".repeat(22)
                + "+" + "-".repeat(16) + "+" + "-".repeat(12) + "+";
        String format = "| %-2s | %-25s | %-20s | %-14s | %-10s |%n";

        System.out.println();
        System.out.println(border);
        System.out.printf(format, "ID", "TITLE", "AUTHOR", "ISBN", "STATUS");
        System.out.println(border);
        for (Book b : books) {
            System.out.printf(format, b.getId(), b.getTitle(), b.getAuthor(), b.getIsbn(), b.getStatus());
        }
        System.out.println(border);
        System.out.println("Total Records Displayed: " + books.size());
    }
}
