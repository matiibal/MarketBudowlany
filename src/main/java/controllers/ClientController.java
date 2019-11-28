package controllers;

import ModelFx.ClientService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import utils.Dialogs;

import static utils.FxmlUtils.onlyOneTypeInput;

public class ClientController {

    public static final String onlyAlphabet = "^[a-zA-Z\\s\\p{IsLatin}]*$";
    private static final String onlyHouseNumber = "\\d{1,4}[a-zA-Z\\s\\p{IsLatin}]*?";
    @FXML
    private CheckBox validPhoneNumber;
    @FXML
    private CheckBox validCity;
    @FXML
    private CheckBox validPost;
    @FXML
   private CheckBox validHouseNr;
    @FXML
    private CheckBox validStreet;
    @FXML
    private CheckBox validNip;
    @FXML
    private CheckBox validSecondName;
    private ClientService clientService;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField secondNameTextField;
    @FXML
    private TextField NIPTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField houseTextField;
    @FXML
    private TextField kod1TextField;
    @FXML
    private TextField kod2TextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private TextField numberTextField;
    @FXML
    private Button addClientButton;
    @FXML
   private CheckBox validName;


    @FXML
    public void initialize() {

        validation();
        bindigs();


    }

    public void validation() {
        onlyOneTypeInput(numberTextField, "\\d{0,9}?");
        onlyOneTypeInput(NIPTextField, "\\d{0,10}?");
        onlyOneTypeInput(kod1TextField, "\\d{0,2}?");
        onlyOneTypeInput(kod2TextField, "\\d{0,3}?");
        onlyOneTypeInput(nameTextField, onlyAlphabet);
        onlyOneTypeInput(secondNameTextField, onlyAlphabet);
        onlyOneTypeInput(cityTextField, onlyAlphabet);
        onlyOneTypeInput(streetTextField, onlyAlphabet);
    }

    public void bindigs() {
        this.clientService = new ClientService();
        this.clientService.clientFxObjectPropertyProperty().get().nameProperty().bind(this.nameTextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().secondNameProperty().bind(this.secondNameTextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().NIPProperty().bind(this.NIPTextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().streetProperty().bind(this.streetTextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().houseNumberProperty().bind(this.houseTextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().postcodeProperty1().bind(this.kod1TextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().postcodeProperty2().bind(this.kod2TextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().phoneNumberProperty().bind(this.numberTextField.textProperty());
        this.clientService.clientFxObjectPropertyProperty().get().cityProperty().bind(this.cityTextField.textProperty());


        validName.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validNamePropertyProperty());
        validSecondName.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validSecondNamePropertyProperty());
        validNip.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validNIPPropertyProperty());
        validStreet.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validStreetProperty());
        validHouseNr.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validHouseNumberProperty());
        validPost.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validPoscodePropertyProperty());
        validCity.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validCityPropertyProperty());
        validPhoneNumber.visibleProperty().bind(this.clientService.clientFxObjectPropertyProperty().get().validPhoneNumberProperty());


        this.addClientButton.disableProperty().bind(nameTextField.textProperty().isEmpty().or(secondNameTextField.textProperty().isEmpty()).or(NIPTextField.textProperty().length().isNotEqualTo(10)).or(cityTextField.textProperty().isEmpty())
                .or(streetTextField.textProperty().isEmpty()).or(houseTextField.textProperty().isEmpty()).or(numberTextField.textProperty().length().isNotEqualTo(9).or(kod1TextField.textProperty().length().isNotEqualTo(2).or(kod2TextField.textProperty().length().isNotEqualTo(3)))));

    }


    public void addClient() {
        try {
            this.clientService.persist();
            Dialogs.dialogConfAddClient();  }
        catch (Exception ex)
        {
            //jezeli nip się powtarza
            Dialogs.errorData("client.errorTitle","client.errorHeader");
        }

        //czyszczenie pól
        nameTextField.clear();
        secondNameTextField.clear();
        NIPTextField.clear();
        streetTextField.clear();
        houseTextField.clear();
        kod1TextField.clear();
        kod2TextField.clear();
        cityTextField.clear();
        numberTextField.clear();
    }
}
 