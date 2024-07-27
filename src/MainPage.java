import autocomplete.WordPair;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import autocomplete.Trie;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class MainPage {

    @FXML
    private TextArea userTextField;
    @FXML
    private TextField sugestionTextField;

    public static Trie trie = new Trie();
    public static ArrayList<WordPair> suggestions = new ArrayList<>();

    int location = 0;
    String text;
    String singleWord;

    public void initialize() {
        userTextField.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; ");
        sugestionTextField.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00; ");
        trie.fillTrie();
        userTextField.textProperty().addListener((obs, oldText, newText) -> {

            location = 0;
            suggestions.clear();
            text = userTextField.getText();
            singleWord = "";

            for (int i = text.length() - 1; i >= 0; i--) {

                if (text.charAt(i) == ' ') {
                    break;
                }

                singleWord = text.charAt(i) + singleWord;
            }

            suggestions = trie.completeString(singleWord);
            suggestions.sort(WordPair::compareTo);

            if (suggestions.isEmpty()) {
                sugestionTextField.setText("");
            }
            else {
                sugestionTextField.setText(suggestions.get(0).getWord());
            }
        });

        userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ALT)) {
                    String userText = userTextField.getText();
                    userTextField.setText(userText + sugestionTextField.getText().substring(singleWord.length()));
                    userTextField.end(); // move cursor to end
                }
                else if (ke.getCode().equals(KeyCode.DOWN)) {
                    location++;
                    sugestionTextField.clear();

                    try {
                        sugestionTextField.setText(suggestions.get(location).getWord());
                    }
                    catch (IndexOutOfBoundsException ignored) {

                    }
                }
            }
        });

    }
}
