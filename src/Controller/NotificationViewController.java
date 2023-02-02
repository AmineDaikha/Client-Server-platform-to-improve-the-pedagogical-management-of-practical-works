package Controller;

import Model.Values;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

/**
 * Created by MedTaki on 09/06/2018.
 */
public class NotificationViewController implements Initializable {

    @FXML
    private AnchorPane body;

    @FXML
    private VBox NotificationBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Iterator<String> notification = Values.getNotificationTexts().iterator();
        while (notification.hasNext()){
            Parent root = null;
            FXMLLoader loader = new FXMLLoader();
            try {
                root = loader.load(getClass().getResource("../View/NotificationPane.fxml").openStream());
                NotificationPaneController controller = loader.getController();
                controller.setNotificationText(notification.next());
                NotificationBox.getChildren().add(root);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }
}
