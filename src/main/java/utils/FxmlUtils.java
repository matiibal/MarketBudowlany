package utils;

import controllers.MenuController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.util.ResourceBundle;

public class FxmlUtils {

    public static Pane fxmlLoader(String fxmlPath)
    {


        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.getController();
        loader.setResources(getResourceBundle());


        try {
            return loader.load();
        } catch (Exception e) {
            System.out.println("Nie udało się załadować nakładki");
            System.out.println(e.getCause().toString());
          // Dialogs.errorDialog(e.getMessage());
        }
return null;

    }
    public static void onlyOneTypeInput(TextField kod2TextField, String s) {
        kod2TextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches(s)) {
                    kod2TextField.setText(oldValue);
                }
            }
        });
    }
    public static ResourceBundle getResourceBundle()
    {
        return ResourceBundle.getBundle("bundles.message");
    }


    public static FXMLLoader getLoader(String fxmlPath)
    {
        FXMLLoader loader = new FXMLLoader(FxmlUtils.class.getResource(fxmlPath));
        loader.getController();
        loader.setResources(getResourceBundle());

        return loader;

    }
}
