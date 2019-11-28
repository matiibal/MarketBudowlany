package ModelFx;

import javafx.beans.property.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class ClientFx {

    @FXML
    private Button addClientButton;

    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty name = new SimpleStringProperty();
    private StringProperty secondName = new SimpleStringProperty();
    private StringProperty NIP = new SimpleStringProperty();
    private StringProperty postcode1 = new SimpleStringProperty();
    private StringProperty postcode2 = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty houseNumber = new SimpleStringProperty();
    private StringProperty street = new SimpleStringProperty();
    private StringProperty phoneNumber = new SimpleStringProperty();






    private StringProperty post = new SimpleStringProperty();

    private BooleanProperty validNameProperty = new SimpleBooleanProperty();
    private BooleanProperty validSecondNameProperty = new SimpleBooleanProperty();
    private BooleanProperty validNIPProperty = new SimpleBooleanProperty();
    private BooleanProperty validPoscodeProperty = new SimpleBooleanProperty();
    private BooleanProperty validCityProperty = new SimpleBooleanProperty();
    private BooleanProperty validHouseNumber = new SimpleBooleanProperty();
    private BooleanProperty validStreet = new SimpleBooleanProperty();
    private BooleanProperty validPhoneNumber = new SimpleBooleanProperty();






    public ClientFx() {
        validNameProperty.bind(name.isNotEmpty());
        validSecondNameProperty.bind(secondName.isNotEmpty());

        validNIPProperty.bind(NIP.length().isEqualTo(10));
        validPoscodeProperty.bind(postcode1.length().isEqualTo(2).and(postcode2.length().isEqualTo(3)));
        validCityProperty.bind(city.isNotEmpty());
        validHouseNumber.bind(houseNumber.isNotEmpty());
        validStreet.bind(street.isNotEmpty());
        validPhoneNumber.bind(phoneNumber.length().isEqualTo(9));



    }


    public boolean getValidNameProperty() {
        return validNameProperty.get();
    }

    public BooleanProperty validNamePropertyProperty() {
        return validNameProperty;
    }

    public void setValidNameProperty(boolean validNameProperty) {
        this.validNameProperty.set(validNameProperty);
    }


    public String getHouseNumber() {
        return houseNumber.get();
    }

    public StringProperty houseNumberProperty() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber.set(houseNumber);
    }


    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getSecondName() {
        return secondName.get();
    }

    public StringProperty secondNameProperty() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName.set(secondName);
    }

    public String getNIP() {
        return NIP.get();
    }

    public StringProperty NIPProperty() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP.set(NIP);
    }

    public String getPostcode1() {
        return postcode1.get();
    }

    public StringProperty postcodeProperty1() {
        return postcode1;
    }

    public void setPostcode2(String postcode) {
        this.postcode2.set(postcode);
    }

    public String getPostcode2() {
        return postcode2.get();
    }

    public StringProperty postcodeProperty2() {
        return postcode2;
    }

    public void setPostcode1(String postcode) {
        this.postcode1.set(postcode);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }


    public String getStreet() {
        return street.get();
    }

    public StringProperty streetProperty() {
        return street;
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
    }

    public StringProperty postcode1Property() {
        return postcode1;
    }

    public StringProperty postcode2Property() {
        return postcode2;
    }

    public boolean isValidNameProperty() {
        return validNameProperty.get();
    }

    public boolean isValidSecondNameProperty() {
        return validSecondNameProperty.get();
    }

    public BooleanProperty validSecondNamePropertyProperty() {
        return validSecondNameProperty;
    }

    public void setValidSecondNameProperty(boolean validSecondNameProperty) {
        this.validSecondNameProperty.set(validSecondNameProperty);
    }

    public boolean isValidNIPProperty() {
        return validNIPProperty.get();
    }

    public BooleanProperty validNIPPropertyProperty() {
        return validNIPProperty;
    }

    public void setValidNIPProperty(boolean validNIPProperty) {
        this.validNIPProperty.set(validNIPProperty);
    }

    public boolean isValidPoscodeProperty() {
        return validPoscodeProperty.get();
    }

    public BooleanProperty validPoscodePropertyProperty() {
        return validPoscodeProperty;
    }

    public void setValidPoscodeProperty(boolean validPoscodeProperty) {
        this.validPoscodeProperty.set(validPoscodeProperty);
    }

    public boolean isValidCityProperty() {
        return validCityProperty.get();
    }

    public BooleanProperty validCityPropertyProperty() {
        return validCityProperty;
    }

    public void setValidCityProperty(boolean validCityProperty) {
        this.validCityProperty.set(validCityProperty);
    }

    public boolean isValidHouseNumber() {
        return validHouseNumber.get();
    }

    public BooleanProperty validHouseNumberProperty() {
        return validHouseNumber;
    }

    public void setValidHouseNumber(boolean validHouseNumber) {
        this.validHouseNumber.set(validHouseNumber);
    }

    public boolean isValidStreet() {
        return validStreet.get();
    }

    public BooleanProperty validStreetProperty() {
        return validStreet;
    }

    public void setValidStreet(boolean validStreet) {
        this.validStreet.set(validStreet);
    }

    public boolean isValidPhoneNumber() {
        return validPhoneNumber.get();
    }

    public BooleanProperty validPhoneNumberProperty() {
        return validPhoneNumber;
    }

    public void setValidPhoneNumber(boolean validPhoneNumber) {
        this.validPhoneNumber.set(validPhoneNumber);
    }
    public void setPost(String post) {
        this.post.set(post);
    }
    public StringProperty postProperty() {
        return post;
    }
}
