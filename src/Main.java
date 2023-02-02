import Model.Values;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("View/Loginlaunch.fxml"));
        primaryStage.setTitle("CMS-Login (Enseignant)");
        primaryStage.setScene(new Scene(root, 515, 607));
        primaryStage.show();
//        primaryStage.setOnCloseRequest(e->{
//            Values.killApp();
//        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
