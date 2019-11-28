import ModelFx.BuiltItemService;
import ModelFx.OrdersService;
import database.dbutils.DbManager;
import ModelFx.ClientService;
import database.models.BuiltItems;
import database.models.Orders;
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

import java.time.LocalDate;
import java.util.*;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    public static Double total(Map<BuiltItems,Integer> map)
    {

        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        map.forEach(
                (e,f)->
                {
                    total.updateAndGet(v -> (double) (v + e.getPrice() * f));
                }
        );



        return total.get();
    }

    public static final String BORDER_PANE_MAIN_FXML = "/fxml/BorderPaneMain.fxml";

    public static void main(String[] args) {
        DbManager dbManager = new DbManager();
        dbManager.openCurrentSession();
        dbManager.openCurrentSessionwithTransaction();

//              OrdersService ordersService = new OrdersService();
//
//
//
//
//        BuiltItemService builtItemService = new BuiltItemService();
//        ClientService clientService = new ClientService();
//        List<BuiltItems> list = new ArrayList<>();
//        list  = builtItemService.findAll();
//
//
//        Map<BuiltItems, Integer> towary = new HashMap<>();
//        towary.put(builtItemService.findById(273), 5);
//        towary.put(builtItemService.findById(274), 15);
//
//
//        Orders orders3 = new Orders();
//        orders3.setTotalPrice(total(towary));
//        orders3.setClient(clientService.findById(31));
//        orders3.setOrderDate(LocalDate.now());
//        orders3.setItem(towary);
//
//
//
//        ordersService.persist(orders3);
//



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
       // primaryStage.setResizable(false);


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
