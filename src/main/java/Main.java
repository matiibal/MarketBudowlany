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


    private static final String LOADING_FXML = "/fxml/Loading.fxml";
    private static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";
    private static volatile boolean isLoading = true;
    private static Thread thread, thread1;

    public static void main(String[] args) {
        thread = new Thread(Main::loading);
        launch(args);


    }

    @Override
    public void start(Stage primaryStage) throws InterruptedException {

        Pane pane = FxmlUtils.fxmlLoader(LOADING_FXML);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        thread.start();
        thread1 = new Thread(()->
        {
            changeGUI(primaryStage);
        });
    }


    private static void loading() {
        DbManager dbManager = new DbManager();
        dbManager.openCurrentSession();
        dbManager.openCurrentSessionwithTransaction();
        isLoading = false;
        Platform.setImplicitExit(false);
        thread1.start();
    }


    private void changeGUI(Stage primaryStage)
    {
        Platform.runLater(new Runnable() {
            public void run() {
                primaryStage.hide();
                Pane borderPane = FxmlUtils.fxmlLoader(BORDER_PANE_MAIN_FXML);
                if (borderPane != null) {
                    Scene scene1 = new Scene(borderPane);
                    primaryStage.setScene(scene1);
                }
                setUserAgentStylesheet(STYLESHEET_CASPIAN);

                primaryStage.setTitle(FxmlUtils.getResourceBundle().getString("title.application"));
                primaryStage.isMaximized();

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
        });
    }

}



