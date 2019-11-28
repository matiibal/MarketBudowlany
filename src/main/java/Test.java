import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.zip.ZipInputStream;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Test extends Application {

    private final String dataURL = "http://www2.census.gov/geo/gazetteer/2013_Gazetteer/2013_Gaz_counties_national.zip";

    @Override
    public void start(Stage primaryStage) throws IOException {
        Map<String, List<String>> countiesByState = readCountiesByState(
                dataURL, true);

        TableView<Location> table = new TableView<>();
        table.setEditable(true);
        TableColumn<Location, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<Location, String> stateCol = new TableColumn<>("State");
        stateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
        TableColumn<Location, String> countyCol = new TableColumn<>("County");
        countyCol.setCellValueFactory(new PropertyValueFactory<>("county"));

        stateCol.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections
                .observableArrayList(countiesByState.keySet())));
        stateCol.setEditable(true);

        countyCol
                .setCellFactory(col -> {
                    TableCell<Location, String> cell = new TableCell<>();
                    // this annoyingly generates lots of warnings in the current
                    // version:
                    // StringBinding state =
                    // Bindings.selectString(cell.tableRowProperty(), "item",
                    // "state");
                    // so we have to do it ourselves...
                    StringBinding state = this.createStringBinding(cell);
                    cell.textProperty().bind(cell.itemProperty());
                    ComboBox<String> countyCombo = new ComboBox<String>();
                    countyCombo.setOnAction(event -> cell
                            .commitEdit(countyCombo.getValue()));
                    countyCombo
                            .setPlaceholder(new Label("Choose a state first"));
                    state.addListener((obs, oldState, newState) -> {
                        if (newState == null) {
                            countyCombo.getItems().clear();
                        } else {
                            countyCombo.getItems().setAll(
                                    countiesByState.get(newState));
                        }
                    });
                    cell.graphicProperty()
                            .bind(Bindings
                                    .when(Bindings
                                            .isNull(createLocationBinding(cell)))
                                    .then((Node) null).otherwise(countyCombo));
                    cell.setContentDisplay(ContentDisplay.TEXT_ONLY);
                    cell.editingProperty()
                            .addListener(
                                    (obs, wasEditing, isEditing) -> cell
                                            .setContentDisplay(isEditing ? ContentDisplay.GRAPHIC_ONLY
                                                    : ContentDisplay.TEXT_ONLY));
                    return cell;
                });
        countyCol.setEditable(true);
        countyCol.setOnEditCommit(event -> event.getRowValue().setCounty(
                event.getNewValue()));

        table.getColumns().addAll(nameCol, stateCol, countyCol);

        TextField newLocationField = new TextField();
        newLocationField.setPromptText("Enter name and press enter");
        newLocationField.setOnAction(event -> {
            final Location location = new Location(newLocationField.getText());
            table.getItems().add(location);
            newLocationField.setText(null);
        });

        BorderPane root = new BorderPane(table, null, null, newLocationField,
                null);

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private StringBinding createStringBinding(
            final TableCell<Location, String> cell) {

        return new StringBinding() {

            {
                final ChangeListener<String> stateListener = (obs, oldState,
                                                              newState) -> {
                    invalidate();
                };

                final ChangeListener<Location> itemListener = (obs, oldItem,
                                                               newItem) -> {
                    if (oldItem != null) {
                        oldItem.stateProperty().removeListener(stateListener);
                    }
                    if (newItem != null) {
                        newItem.stateProperty().addListener(stateListener);
                    }
                    invalidate();
                };
                final ChangeListener<TableRow> rowListener = (obs, oldRow,
                                                              newRow) -> {
                    if (oldRow != null) {
                        oldRow.itemProperty().removeListener(itemListener);
                        if (oldRow.getItem() != null) {
                            ((Location) oldRow.getItem()).stateProperty()
                                    .removeListener(stateListener);
                        }
                    }
                    if (newRow != null) {
                        newRow.itemProperty().addListener(itemListener);
                        if (newRow.getItem() != null) {
                            ((Location) newRow.getItem()).stateProperty()
                                    .addListener(stateListener);
                        }
                    }
                    invalidate();
                };
                cell.tableRowProperty().addListener(rowListener);
            }

            @Override
            protected String computeValue() {
                String value;
                if (cell.getTableRow() == null) {
                    value = null;
                } else if (cell.getTableRow().getItem() == null) {
                    value = null;
                } else {
                    value = ((Location) cell.getTableRow().getItem())
                            .getState();
                }
                return value;
            }

        };
    }

    private ObjectBinding<Location> createLocationBinding(
            TableCell<Location, String> cell) {
        return new ObjectBinding<Location>() {

            {
                final ChangeListener<Location> itemListener = (obs, oldItem,
                                                               newItem) -> invalidate();
                final ChangeListener<TableRow> rowListener = (obs, oldRow,
                                                              newRow) -> {
                    if (oldRow != null) {
                        oldRow.itemProperty().removeListener(itemListener);
                    }
                    if (newRow != null) {
                        newRow.itemProperty().addListener(itemListener);
                    }
                    invalidate();
                };
                cell.tableRowProperty().addListener(rowListener);
            }

            @Override
            protected Location computeValue() {
                if (cell.getTableRow() == null) {
                    return null;
                } else {
                    return (Location) cell.getTableRow().getItem();
                }
            }

        };
    }

    private Map<String, List<String>> readCountiesByState(String url,
                                                          boolean zip) throws IOException {
        try (Stream<String> lines = getReader(url, zip).lines()) {
            return lines.skip(1) // skip header
                    .map(line -> line.split("\t")) // split tab-delimited line
                    .collect(Collectors.groupingBy(tokens -> tokens[0], // first
                            // column
                            // is
                            // state
                            TreeMap::new, // use a map that sorts keys
                            Collectors.mapping(tokens -> tokens[3], // fourth
                                    // column is
                                    // county
                                    Collectors.toList())));
        }
    }

    private BufferedReader getReader(String url, boolean zip)
            throws IOException {
        URLConnection conn = new URL(url).openConnection();
        InputStream is = conn.getInputStream();
        InputStream inputStream;
        if (zip) {
            ZipInputStream zipInputStream = new ZipInputStream(is);
            zipInputStream.getNextEntry();
            inputStream = zipInputStream;
        } else {
            inputStream = is;
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    public static class Location {
        private final StringProperty name;
        private final StringProperty state;
        private final StringProperty county;

        public Location(String name) {
            this.name = new SimpleStringProperty(this, "name", name);
            this.state = new SimpleStringProperty(this, "state");
            this.county = new SimpleStringProperty(this, "county");
        }

        public final String getName() {
            return name.get();
        }

        public final void setName(String name) {
            this.name.set(name);
        }

        public final StringProperty nameProperty() {
            return name;
        }

        public final String getState() {
            return state.get();
        }

        public final void setState(String state) {
            this.state.set(state);
        }

        public final StringProperty stateProperty() {
            return state;
        }

        public final String getCounty() {
            return county.get();
        }

        public final void setCounty(String county) {
            this.county.set(county);
        }

        public final StringProperty countyProperty() {
            return county;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}