import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import tools.Pair;

import java.sql.SQLException;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class Program extends Application {
    Controller controller;

    // Fields for graphics
    final private int WIDTH = 1000;
    final private int HEIGHT = 600;
    Group root = new Group();
    VBox strings = new VBox();

    HBox addUserBox = new HBox();
    Button buttonAddUser = new Button("Add user");
    TextField name = new TextField();
    TextField surname = new TextField();
    TextField code = new TextField();

    HBox addBookBox = new HBox();
    Button buttonAddBook = new Button("Add book");
    TextField bookName = new TextField();
    TextField author = new TextField();

    HBox deleteBookBox = new HBox();
    Button buttonDeleteBook = new Button("Delete book");
    TextField bookCode = new TextField();

    HBox changeCodeBox = new HBox();
    Button changeCodeButton = new Button("Change book code");
    TextField oldCode = new TextField();
    TextField newCode = new TextField();

    HBox findBookBox = new HBox();
    Button findBook = new Button("Find book info");
    TextField findCode = new TextField();
    Text bookInfo = new Text();

    HBox userBooksBox = new HBox();
    Button userBooksButton = new Button("Find book info");
    TextField userId = new TextField();
    Text userBookInfo = new Text();
    // The end of fields for graphics


    public void start(Stage primaryStage) throws Exception {
        controller = new Controller();

        root.getChildren().add(strings);

        strings.setPadding(new Insets(10, 30, 10, 30));
        strings.setSpacing(20);

        strings.getChildren().add(new Text("Add new User"));
        strings.getChildren().add(addUserBox);
        strings.getChildren().add(new Text("Add new Book to the library"));
        strings.getChildren().add(addBookBox);
        strings.getChildren().add(new Text("Delete Book from the library"));
        strings.getChildren().add(deleteBookBox);
        strings.getChildren().add(new Text("Change book code"));
        strings.getChildren().add(changeCodeBox);
        strings.getChildren().add(new Text("Find book info"));
        strings.getChildren().add(findBookBox);
        strings.getChildren().add(bookInfo);
        strings.getChildren().add(new Text("Find user's books info"));
        strings.getChildren().add(userBooksBox);
        strings.getChildren().add(userBookInfo);

        // Add new user
        addUserBox.setSpacing(20);
        addUserBox.getChildren().add(new Text("Name: "));
        addUserBox.getChildren().add(name);
        addUserBox.getChildren().add(new Text("Surname: "));
        addUserBox.getChildren().add(surname);
        addUserBox.getChildren().add(buttonAddUser);

        buttonAddUser.setOnAction(e -> {
            try {
                controller.addUser(name.getText(), surname.getText());
                // TODO:: пометка об успешной регистрации
            } catch (SQLException exception) {
                // TODO:: сообщение об ошибке
            }
            name.clear();
            surname.clear();
        });

        // Add new book
        addBookBox.setSpacing(20);
        addBookBox.getChildren().add(new Text("Book name: "));
        addBookBox.getChildren().add(bookName);
        addBookBox.getChildren().add(new Text("Author: "));
        addBookBox.getChildren().add(author);
        addBookBox.getChildren().add(new Text("Code: "));
        addBookBox.getChildren().add(code);
        addBookBox.getChildren().add(buttonAddBook);

        buttonAddBook.setOnAction(e -> {
            // TODO:: пометки об успешном (или не очень) добавлении книги
            try {
                controller.addBook(bookName.getText(), author.getText(), Integer.parseInt(code.getText()));
            } catch (NumberFormatException numberFormatException) {

            } catch (IllegalArgumentException illegalArgumentException) {

            } catch (SQLException sqlException) {

            }
            bookName.clear();
            author.clear();
            code.clear();
        });

        // Delete book
        deleteBookBox.setSpacing(20);
        deleteBookBox.getChildren().add(new Text("Book code: "));
        deleteBookBox.getChildren().add(bookCode);
        deleteBookBox.getChildren().add(buttonDeleteBook);

        buttonDeleteBook.setOnAction(e -> {
            // TODO:: пометки об успешном (или не очень) удалении книги
            try {
                controller.deleteBook(Integer.parseInt(bookCode.getText()));
            } catch (NumberFormatException numberFormatException) {

            } catch (IllegalArgumentException illegalArgumentException) {

            } catch (SQLException sqlException) {

            }
            bookCode.clear();
        });

        // Change code
        changeCodeBox.setSpacing(20);
        changeCodeBox.getChildren().add(new Text("Old book code: "));
        changeCodeBox.getChildren().add(oldCode);
        changeCodeBox.getChildren().add(new Text("New book code: "));
        changeCodeBox.getChildren().add(newCode);
        changeCodeBox.getChildren().add(changeCodeButton);

        changeCodeButton.setOnAction(e -> {
            // TODO:: пометки об успешном (или не очень) изменении кода книги
            try {
                controller.changeBookCode(Integer.parseInt(oldCode.getText()), Integer.parseInt(newCode.getText()));
            } catch (NumberFormatException numberFormatException) {

            } catch (IllegalArgumentException illegalArgumentException) {

            } catch (SQLException sqlException) {

            }
            oldCode.clear();
            newCode.clear();
        });

        // Find book info
        findBookBox.setSpacing(20);
        findBookBox.getChildren().add(new Text("Book code: "));
        findBookBox.getChildren().add(findCode);
        findBookBox.getChildren().add(findBook);

        findBook.setOnAction(e -> {
            // TODO:: пометки об успешном (или не очень) изменении кода книги
            try {
                bookInfo.setText(controller.getBookInfo(Integer.parseInt(findCode.getText())));
            } catch (NumberFormatException numberFormatException) {

            } catch (IllegalArgumentException illegalArgumentException) {

            } catch (SQLException sqlException) {

            }
            findCode.clear();
        });

        // Find user's books info
        userBooksBox.setSpacing(20);
        userBooksBox.getChildren().add(new Text("User id: "));
        userBooksBox.getChildren().add(userId);
        userBooksBox.getChildren().add(userBooksButton);

        userBooksButton.setOnAction(e -> {
            // TODO:: пометки об успешном (или не очень) изменении кода книги
            try {
                StringBuilder sb = new StringBuilder();
                List<Pair> temp = controller.getUserBooks(Integer.parseInt(userId.getText()));
                for (Pair t : temp) {
                    sb.append(t.toString()).append("\n");
                }
                userBookInfo.setText(sb.toString());
            } catch (NumberFormatException numberFormatException) {

            } catch (IllegalArgumentException illegalArgumentException) {

            }
            userId.clear();
        });


        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setTitle("Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
