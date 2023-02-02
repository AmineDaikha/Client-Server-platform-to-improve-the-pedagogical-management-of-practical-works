package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Created by MedTaki on 09/04/2018.
 */
public class MessagePaneController {


    @FXML
    private VBox message;

    @FXML
    private Label username;

    @FXML
    private Label dateTime;

    @FXML
    private Text messageText;

    public void setmessageText(String text){
        messageText.setText(text);
    }

    public void setUsername(String username1){
        username.setText(username1);
    }

    public Label getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime.setText(dateTime);
    }
}
