import tools.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {
    Connection connection;

    Controller() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:library.db");
        //System.out.println("Successful");
    }

    // Try to change some data in DB
    private void changeData(String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
    }

    // Returns id of found element or -1 otherwise.
    private int getId(String table, String column, String value) {
        try {
            Statement statement = connection.createStatement();
            return (statement.executeQuery("SELECT * FROM " + table + " WHERE " + column + " = " + value))
                    .getInt("id");
        } catch (Exception e) {
            return -1;
        }
    }

    // Add user by name and surname.
    void addUser(String name, String surname) throws SQLException {
        changeData("INSERT INTO users (name, surname) VALUES ('" + name + "', '" + surname + "')");
    }

    // Add book by name and it's author. Set user_id as 1: it means that book is in library now and nobody take it.
    void addBook(String name, String author, int code) throws SQLException {
        changeData("INSERT INTO books (user_id, code, name, author) VALUES ('1', '" + code + "', '" + name
                + "', '" + author + "')");
    }

    // Checking the existence of a book by code.
    private void checkCode(int code, boolean shouldExist) throws IllegalArgumentException {
        int id = getId("books", "code", code + "");
        if (shouldExist && id == -1) {
            throw new IllegalArgumentException("Book with code " + code + " doesn't exist");
        }
        if (!shouldExist && id != -1) {
            throw new IllegalArgumentException("Book with code " + code + " is already exists");
        }
    }

    // Change book code in data base if it exists, throws IllegalArgumentException otherwise.
    void changeBookCode(int curCode, int newCode) throws IllegalArgumentException, SQLException {
        checkCode(newCode, false);
        changeData("UPDATE books SET code = " + newCode + " WHERE code = " + curCode);
    }

    // Delete book from data base if it exists, throws IllegalArgumentException otherwise.
    void deleteBook(int code) throws IllegalArgumentException, SQLException {
        checkCode(code, true);
        changeData("DELETE FROM books WHERE code = '" + code + "'");
    }

    // Find a list of books by any parameter in table.
    private List<Pair> getBookList(String column, int value) {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT name, author FROM books WHERE " + column + " = " + value);
            List<Pair> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Pair(rs.getString("author"), rs.getString("name")));
            }
            return result;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    // Returns book name and author of this book if it exists, throws IllegalArgumentException if it doesn't.
    String getBookInfo(int code) throws IllegalArgumentException, SQLException {
        checkCode(code, true);
        List<Pair> res = getBookList("code", code);
        if (res.isEmpty()) {
            throw new SQLException();
        }
        return res.get(0).toString();
    }

    // Returns list of user's books. Return empty list if user didn't take any book.
    List<Pair> getUserBooks(int user_id) {
        return getBookList("user_id", user_id);
    }

    void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
