package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import static model.Inventory.deletePart;
import static model.Inventory.deleteProduct;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import static model.Inventory.lookupPart;
import static model.Inventory.lookupProduct;
import model.Part;
import model.Product;

/**
 * This is the FXML controller class for the Main Screen.
 *
 * @author Audrey Hababag
 */
public class MainFormController implements Initializable
{

    Stage stage;
    Parent scene;
    private static Part modPart;
    private static int partIndex;
    private static Product modProduct;
    private static int productIndex;
    Product TemProduct;

    @FXML
    private TextField partSearchTxt;
    @FXML
    private TableView<Part> tableViewParts;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TextField productSearchTxt;
    @FXML
    private TableView<Product> tableViewProducts;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;

    /**
     * Provides a means to search for a part. The user will be alerted if a
     * match is not found.
     * <p>
     * There are two problems that are encountered while writing this method.
     * The issues are not errors but are comparably critical. When a match is
     * not found, the table will be empty. When a letter or a number does not
     * exist in the list, the table will be empty.
     * <p>
     * Feedback from the method is essential for a smoother user experience. The
     * aforementioned issues are tackled using INFORMATION alerts. The tables
     * are also repopulated when the search method does not find a match.
     *
     * @param event when the return key is actuated
     */
    @FXML
    void partSearch(ActionEvent event)
    {
        String StrSearch;
        int intSearch;

        if (isInteger(partSearchTxt.getText())) // If searching for a matching id
        {
            intSearch = Integer.parseInt((partSearchTxt.getText()));
            lookupPart(intSearch);
            partSearchTxt.clear(); //clears the text area after return is actuated
            if (lookupPart(intSearch) == null)
            {
                tableViewParts.setItems(getAllParts()); // repopulates table
                InventoryAlerts.notFound(); // alert if no match is found
                return;
            }
            // repopulates the table with items that matched the search
            ObservableList<Part> temp = FXCollections.observableArrayList();
            temp.add(lookupPart(intSearch));
            tableViewParts.setItems(temp);
        }
        else // if searching for a matching name
        {
            StrSearch = partSearchTxt.getText();
            lookupPart(StrSearch);
            partSearchTxt.clear(); //clears the text area after return is actuated
            if (lookupPart(StrSearch) == null || lookupPart(StrSearch).isEmpty())
            {
                tableViewParts.setItems(getAllParts()); // repopulates table
                InventoryAlerts.notFound(); // alert if no match is found
                return;
            }
            // repopulates the table with items that matched the search
            tableViewParts.setItems(lookupPart(StrSearch));
        }
    }

    /**
     * Provides a means to search for a product. The user will be alerted if a
     * match is not found.
     * <p>
     * There are two problems that are encountered while writing this method.
     * The issues are not errors but are comparably critical. When a match is
     * not found, the table will be empty. When a letter or a number does not
     * exist in the list, the table will be empty.
     * <p>
     * Feedback from the method is essential for a smoother user experience. The
     * aforementioned issues are tackled using INFORMATION alerts. The tables
     * are also repopulated when the search method does not find a match.
     *
     * @param event when the return key is actuated
     */
    @FXML
    void productSearch(ActionEvent event)
    {
        String StrSearch;
        int intSearch;

        if (isInteger(productSearchTxt.getText())) // If searching for a matching id
        {
            intSearch = Integer.parseInt((productSearchTxt.getText()));
            lookupProduct(intSearch);
            productSearchTxt.clear(); //clears the text area after return is actuated
            if (lookupProduct(intSearch) == null)
            {
                tableViewProducts.setItems(getAllProducts()); // repopulates table
                InventoryAlerts.notFound(); // alert if no match is found
                return;
            }
            // repopulates the table with items that matched the search
            ObservableList<Product> temp = FXCollections.observableArrayList();
            temp.add(lookupProduct(intSearch));
            tableViewProducts.setItems(temp);
        }
        else // if searching for a matching name
        {
            StrSearch = productSearchTxt.getText();
            lookupProduct(StrSearch);
            productSearchTxt.clear(); //clears the text area after return is actuated
            if (lookupProduct(StrSearch) == null || lookupProduct(StrSearch).isEmpty())
            {
                tableViewProducts.setItems(getAllProducts()); // repopulates table
                InventoryAlerts.notFound(); // alert if no match is found
                return;
            }
            // repopulates the table with items that matched the search
            tableViewProducts.setItems(lookupProduct(StrSearch));
        }

    }

    /**
     * Directs the user to the Add Part screen.
     * <p>
     * The throws clause on the method header handles the IOException that is
     * highlighted by the editor at the FXMLLoader.load line.
     *
     * @param event when the user clicks on the Add button
     * @throws IOException if not caught or declared
     */
    @FXML
    void onActionAddPartScreen(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        /* The line below this comment becomes partially highlighed as an error
        if not declared */
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Directs the user to the Add Product screen.
     * <p>
     * The throws clause on the method header handles the IOException that is
     * highlighted by the editor at the FXMLLoader.load line.
     *
     * @param event when the user clicks on the Add button
     * @throws IOException if not caught or declared
     */
    @FXML
    void onActionAddProductScreen(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        /* The line below this comment becomes partially highlighed as an error
        if not declared */
        scene = FXMLLoader.load(getClass().getResource("/view/AddProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Directs the user to the Modify Part screen while ensuring that the
     * selected part carries over.
     * <p>
     * The throws clause on the method header handles the IOException that is
     * highlighted by the editor at the FXMLLoader.load line.
     * <p>
     * There is an error that can occur if not handled properly. If the user
     * clicks on the Modify button to be directed to the modify screen, an error
     * message caused by IndexOutOfBoundsException, LoadException and
     * InvocationTargetException will be received. The user will not be able to
     * move on to the Modify screen if this is not handled. A simple if
     * statement containing an alert takes care of this problem. If the user
     * does not select an item from the table to be modified, an ERROR dialog
     * prompting the user to select an item will execute.
     * <p>
     * An improved solution for this method is possible. Just for an example,
     * Some type of visual cue can be used to inform the user when the Modify
     * button can be clicked or not. This reduces the steps necessary to address
     * such an issue. An alert is not needed if this can be implemented. Even
     * something a simple as changing the color or the opacity of the Modify
     * button can work.
     *
     * @param event when the user clicks on the Modify button
     * @throws IOException if not caught or declared
     */
    @FXML
    void onActionModifyPartScreen(ActionEvent event) throws IOException
    {
        modPart = tableViewParts.getSelectionModel().getSelectedItem();

        if (modPart == null)
        {
            InventoryAlerts.errorDialog(9, null); // alert if no part is selected
            return;
        }

        partIndex = getAllParts().indexOf(modPart);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        /* The line below this comment becomes partially highlighed as an error
        if not declared */
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Directs the user to the Modify Product screen while ensuring that the
     * selected product carries over.
     * <p>
     * The throws clause on the method header handles the IOException that is
     * highlighted by the editor at the FXMLLoader.load line.
     * <p>
     * There is an error that can occur if not handled properly. If the user
     * clicks on the Modify button to be directed to the modify screen, an error
     * message caused by IndexOutOfBoundsException, LoadException and
     * InvocationTargetException will be received. The user will not be able to
     * move on to the Modify screen if this is not handled. A simple if
     * statement containing an alert takes care of this problem. If the user
     * does not select an item from the table to be modified, an ERROR dialog
     * prompting the user to select an item will execute.
     * <p>
     * An improved solution for this method is possible. Just for an example,
     * Some type of visual cue can be used to inform the user when the Modify
     * button can be clicked or not. This reduces the steps necessary to address
     * such an issue. An alert is not needed if this can be implemented. Even
     * something a simple as changing the color or the opacity of the Modify
     * button can work.
     *
     * @param event when the user clicks on the Modify button
     * @throws IOException if not caught or declared
     */
    @FXML
    void onActionModifyProductScreen(ActionEvent event) throws IOException
    {
        modProduct = tableViewProducts.getSelectionModel().getSelectedItem();

        if (modProduct == null)
        {
            InventoryAlerts.errorDialog(12, null); // alert if no product is selected
            return;
        }
        productIndex = getAllProducts().indexOf(modProduct);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        /* The line below this comment becomes partially highlighed as an error
        if not declared */
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Deletes a part in the Parts table.
     * <p>
     * An alert is needed to inform the user that a part needs to be selected
     * from the table before deleting. No feedback is provided if this not
     * implemented otherwise.
     * <p>
     * An alternative or perhaps an additional layer can be added to improve
     * user experience. The delete button could be modified to change color
     * depending on whether a part is selected or not. The user can avoid the
     * extra actions involved in receiving a CONFIRMATION dialog with an easily
     * identifiable indication that a selection must occur before the delete
     * button becomes available.
     *
     * @param event when the user clicks the Delete button
     */
    @FXML
    void onActionDeletePart(ActionEvent event)
    {
        Part toDelete = tableViewParts.getSelectionModel().getSelectedItem();
        if (toDelete == null)
        {
            InventoryAlerts.errorDialog(9, null); // alert if no part is selected 
            return;
        }
        if (toDelete != null)
        {
            // confirmation dialog when the user clicks on delete
            boolean delete = InventoryAlerts.deleteConfirm(toDelete.getName());
            if (delete)
            {
                deletePart(toDelete);
            }
        }
    }

    /**
     * Deletes a part in the Products table.
     * <p>
     * Appropriate measures are taken to ensure that products with associated
     * parts are not deleted. similar to the Parts table, the Product table will
     * just delete a product if no feedback is provided. The user is alerted if
     * a product is not selected. If a selected product have associated part/s,
     * the user will be alerted and the delete will not execute.
     * <p>
     * Visual cues can be used for an improved version of this method. A product
     * that has associated parts can be highlighted with a different color, or
     * perhaps the font can be changed so to be easily identified. An alert may
     * still be needed, but being able to visually catch a potential error can
     * improve efficiency.
     *
     * @param event when the user clicks the Delete button
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event)
    {
        Product toDelete = tableViewProducts.getSelectionModel().getSelectedItem();
        if (toDelete == null)
        {
            InventoryAlerts.errorDialog(12, null); // alert if no part is selected 
            return;
        }
        int i = toDelete.getAllAssociatedParts().size();
        if (toDelete != null || i == 0)
        {
            if (i > 0)
            {
                InventoryAlerts.errorDialog(11, null);
                return;
            }
            // confirmation dialog when the user clicks on delete
            boolean delete = InventoryAlerts.deleteConfirm(toDelete.getName());
            if (delete)
            {
                deleteProduct(toDelete);
            }
        }
    }

    /**
     * Exits the inventory.
     *
     * @param event when the user clicks on the Exit button
     */
    @FXML
    void onActionExitMain(ActionEvent event)
    {
        System.exit(0);
    }

    /**
     * Checks if input is an integer.
     *
     * @param input the input
     * @return returns true if integer, and false if not an integer
     */
    private boolean isInteger(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    /**
     * This is a getter for partIndex.
     *
     * @return the partIndex
     */
    public static int getPartIndex()
    {
        return partIndex;
    }

    /**
     * This is a getter for productIndex.
     *
     * @return the productIndex
     */
    public static int getProductIndex()
    {
        return productIndex;
    }

    /**
     * Initializes the table fields of the main screen.
     *
     * @param url pointer to the resource
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Prepare part columns
        tableViewParts.setItems(getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Prepare product columns
        tableViewProducts.setItems(getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }
}
