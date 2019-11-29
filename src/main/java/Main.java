import database.dbutils.DbManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import utils.Dialogs;
import utils.FxmlUtils;
import java.util.Optional;


public class Main extends Application {

    public static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {

        DbManager dbManager = new DbManager();
        dbManager.openCurrentSession();
        dbManager.openCurrentSessionwithTransaction();
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) {


        Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
        if (borderPane != null) {
            Scene scene = new Scene(borderPane);
            primaryStage.setScene(scene);
        }
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));

        //zamykanie przez x
        Platform.setImplicitExit(false);
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Optional<ButtonType> result = Dialogs.dialogConfirmation();
                if (result.get() == ButtonType.OK) {
                    Platform.exit();
                    System.exit(0);
                }
            }
        });
        primaryStage.show();


    }
}
