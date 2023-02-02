package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;


/**
 * Created by MedTaki on 09/06/2018.
 */
public class NotificationPaneController {


    @FXML
    private Label NotificationText;

    public Label getNotificationText() {
        return NotificationText;
    }

    public void setNotificationText(String  notificationText) {
        NotificationText.setText(notificationText);
    }
}
