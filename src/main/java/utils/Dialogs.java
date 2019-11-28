package utils;

import javafx.scene.control.*;

import java.util.Optional;
import java.util.ResourceBundle;

public class Dialogs {
    static ResourceBundle bundle = FxmlUtils.getResourceBundle();


    public static void dialogAboutApp() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("about.title"));
        informationAlert.setHeaderText(bundle.getString("about.header"));
        informationAlert.setContentText(bundle.getString("about.content"));
        informationAlert.showAndWait();
    }

    public static void dialogConfAddClient() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("addClient.title"));
        informationAlert.setHeaderText(bundle.getString("addClient.header"));
        informationAlert.showAndWait();
    }
    public static void dialogConfAddCategory() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("addCategory.title"));
        informationAlert.setHeaderText(bundle.getString("addCategory.header"));
        informationAlert.showAndWait();
    }


    public static Optional<ButtonType> dialogConfirmation() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("exit.title"));
        confirmationAlert.setHeaderText(bundle.getString("exit.header"));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }

    public static Optional<ButtonType> dialogConfirmationEditItems() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("itemsEdit.title"));
        confirmationAlert.setHeaderText(bundle.getString("itemsEdit.header"));
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }


    public static Optional<ButtonType> deleteConfirmation() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle(bundle.getString("category.deleteTitle"));
        confirmationAlert.setHeaderText(bundle.getString("category.deleteHeader"));
        ButtonType okButton = new ButtonType(bundle.getString("category.delete.yes"), ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType(bundle.getString("category.delete.no"), ButtonBar.ButtonData.NO);
        confirmationAlert.getButtonTypes().setAll(okButton, noButton);
        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result;
    }



    public static String editDialog(String value)
    {
        TextInputDialog dialog = new TextInputDialog(value);
        dialog.setTitle(bundle.getString("edit.title"));
        dialog.setHeaderText(bundle.getString("edit.header"));
        Optional<String> result = dialog.showAndWait();
        if(result.isPresent())
        {
            return result.get();
        }
        return null;
    }

    public static void errorData(String keyTittle, String keyHeaderText)
    {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setTitle(bundle.getString(keyTittle));
        errorAlert.setHeaderText(bundle.getString(keyHeaderText));

        errorAlert.showAndWait();
    }

    public static void dialogConfAddItems() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("addItems.title"));
        informationAlert.setHeaderText(bundle.getString("addItems.header"));
        informationAlert.showAndWait();
    }

    public static void dialogConfAddOrder() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle(bundle.getString("addOrder.title"));
        informationAlert.setHeaderText(bundle.getString("addOrder.header"));
        informationAlert.showAndWait();
    }
}
