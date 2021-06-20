package controller;

import static controller.MainFormController.getProductIndex;
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
import model.Inventory;
import model.Part;
import model.Product;
import static model.Inventory.getAllParts;
import static model.Inventory.getAllProducts;
import static model.Inventory.lookupPart;

/**
 * This is the FXML controller class for the Modify Product screen.
 *
 * @author Audrey Hababag
 */
public class ModifyProductController implements Initializable
{

    Stage stage;
    Parent scene;
    int productNum;
    Product assocPart;
    Part currentPart;
    Inventory stock;
    private final int productIndex = getProductIndex();
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

    @FXML
    private TextField modifyProductIdTxt;
    @FXML
    private TextField modifyNameTxt;
    @FXML
    private TextField modifyInventoryTxt;
    @FXML
    private TextField modifyCostTxt;
    @FXML
    private TextField modifyMaxTxt;
    @FXML
    private TextField modifyMinTxt;
    @FXML
    private TextField searchPartTxt;
    @FXML
    private TableView<Part> topAddTableView;
    @FXML
    private TableColumn<Part, Integer> addTableColumnId;
    @FXML
    private TableColumn<Part, String> addTableColumnName;
    @FXML
    private TableColumn<Part, Integer> addTableColumnInventory;
    @FXML
    private TableColumn<Part, Double> addTableColumnPrice;
    @FXML
    private TableView<Part> bottomRemoveTableView;
    @FXML
    private TableColumn<Part, Integer> removeTableColumnId;
    @FXML
    private TableColumn<Part, String> removeTableColumnName;
    @FXML
    private TableColumn<Part, Integer> removeTableColumnInventory;
    @FXML
    private TableColumn<Part, Double> removeTableColumnPrice;

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
    void onActionSearchParts(ActionEvent event)
    {
        String StrSearch;
        int intSearch;

        if (isInteger(searchPartTxt.getText())) // If searching for a matching id
        {
            intSearch = Integer.parseInt((searchPartTxt.getText()));
            lookupPart(intSearch);
            searchPartTxt.clear(); //clears the text area after return is actuated
            if (lookupPart(intSearch) == null)
            {
                topAddTableView.setItems(getAllParts()); // repopulates table
                InventoryAlerts.notFound(); // alert if no match is found
                return;
            }
            // repopulates the table with items that matched the search
            ObservableList<Part> temp = FXCollections.observableArrayList();
            temp.add(lookupPart(intSearch));
            topAddTableView.setItems(temp);
        }

        else  // if searching for a matching name
        {
            StrSearch = searchPartTxt.getText();
            lookupPart(StrSearch);
            searchPartTxt.clear(); //clears the text area after return is actuated
            if (lookupPart(StrSearch) == null || lookupPart(StrSearch).isEmpty())
            {
                topAddTableView.setItems(getAllParts()); // repopulates table
                InventoryAlerts.notFound(); // alert if no match is found
                return;
            }
            // repopulates the table with items that matched the search
            topAddTableView.setItems(lookupPart(StrSearch));
        }

    }

    /**
     * Associates a part to a product.
     * <p>
     * One important thing to note is that associating a part to a product can
     * only happen once. This can cause major logistical issues if left
     * unaddressed. This project is very light on arithmetic, but it is not
     * difficult to see the types of problems that can occur if more than one
     * instance of a part is introduced to the table of a product. For example,
     * each part has a price. This can make for imprecise accounting efforts.
     * The method catches repeat entries using an if statement within a for
     * loop. The user is also alerted if a part is clicked more than once.
     *
     * @param event when the user clicks on the Add button
     */
    @FXML
    void onActionAddAssociatedPart(ActionEvent event)
    {

        currentPart = topAddTableView.getSelectionModel().getSelectedItem();
        boolean repeat = false;

        // Checks if a part is selected
        if (currentPart == null)
        {
            // sends an alert if no part is selected and user clicks on add
            InventoryAlerts.errorDialog(9, null);
        }
        if (currentPart != null)
        {
            // Checks for repeated part and sends an alert 
            int compareId = currentPart.getId();
            for (int i = 0; i < assocParts.size(); i++)

            {
                if (assocParts.get(i).getId() == compareId)
                {

                    InventoryAlerts.errorDialog(10, null);
                    repeat = true;
                }
            }
            if (!repeat)
            {
                // assosiates a part to product
                assocPart.addAssociatedPart(currentPart);
            }
        }
    }

    /**
     * Removes an associated part.
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
     * @param event when the user clicks on Remove Associated Part button
     */
    @FXML
    void onActionRemoveAssociatedPart(ActionEvent event)
    {
        currentPart = bottomRemoveTableView.getSelectionModel().getSelectedItem();

        // checks if a part is selected on the table
        if (currentPart == null)
        {
            // sends an alert if no part is selected and user clicks on delete
            InventoryAlerts.errorDialog(9, null);

        }

        if (currentPart != null)
        {
            // sends a confirmation dialog when the user clicks delete
            boolean delete = InventoryAlerts.deleteConfirm(currentPart.getName());
            if (delete)
            {
                assocPart.deleteAssociatedPart(currentPart);
            }
        }
        return;
    }

    /**
     * Returns the user to the main screen.
     * <p>
     * The throws clause on the method header handles the IOException that is
     * highlighted by the editor at the FXMLLoader.load line.
     *
     * @param event when request to return to main is received
     * @throws IOException if not catched or declared
     */
    @FXML
    void onActionReturntoMain(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        /* The line below this comment becomes partially highlighed as an error
        if not declared */
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Saves the product entered by the user.
     * <p>
     * For an undetermined reason, the name field is the only field that can be
     * left blank without receiving any errors when saving. To fix this, the
     * method checks for an empty name field and calls an alert with an ERROR
     * dialog. This can be replicated by commenting out the if statement between
     * lines 261 and 265, running the program, going to add part screen filling
     * out all the fields except for the name field, and clicking on save.
     * <p>
     * Leaving any of the other fields blank or filling them with an invalid
     * format, also causes an error. The catch clause deals with the exception
     * of these remaining NumberFormatException by executing an alert which
     * informs the user that a field is either empty or contains an invalid
     * format.
     * <p>
     * The throws clause at the method header handles the error which is
     * highlighted at the end of this method.
     * <p>
     * If an update is to be considered, these errors will be handled
     * differently. An if statement that checks each field will be a more
     * suitable solution to ensure that they are not left empty or that the
     * format for each entry are valid. If done so, a try statement will become
     * unnecessary.
     *
     * @param event when the user clicks on the Save button
     * @throws IOException if not caught or declared
     */
    @FXML
    void onActionSaveProduct(ActionEvent event) throws IOException
    {
        try
        {
            String name = modifyNameTxt.getText();
            double price = Double.parseDouble(modifyCostTxt.getText());
            int inv = Integer.parseInt(modifyInventoryTxt.getText());
            int min = Integer.parseInt(modifyMinTxt.getText());
            int max = Integer.parseInt(modifyMaxTxt.getText());

            // Total cost of new product's associated parts
            double partsSum = 0.00;
            for (int i = 0; i < assocParts.size(); i++)
            {
                partsSum = partsSum + assocParts.get(i).getPrice();
            }

            // alert if total price of parts are greater than product price
            if (partsSum > price)
            {
                InventoryAlerts.errorDialog(7, modifyCostTxt);
                return;
            }
            /* The line alerts if name field is empty or null. This has to be 
            done because for some reason, a blank name field is accepted when saved */
            if (name == null || name.isEmpty())
            {
                InventoryAlerts.errorDialog(1, modifyNameTxt);
                return;
            }
            // alert if m,in is greater than max
            if (min > max)
            {
                InventoryAlerts.errorDialog(3, modifyMinTxt);
                return;
            }
            // alert if price is equal to or less than zero
            if (price <= 0)
            {
                InventoryAlerts.errorDialog(2, modifyCostTxt);
                return;
            }
            // alert if inventory is less than one
            if (inv < 1)
            {
                InventoryAlerts.errorDialog(6, modifyInventoryTxt);
                return;
            }
            // alert if inventory is less than min or greater than max
            if (inv < min || inv > max)
            {
                InventoryAlerts.errorDialog(5, modifyInventoryTxt);
                return;
            }
            else
            {
                // create new product
                Product addProduct = new Product(
                  productNum, name, price, inv, min, max);

                // Associate parts to new product 
                for (int i = 0; i < assocParts.size(); i++)
                {
                    addProduct.addAssociatedPart(assocParts.get(i));
                }

                // Add product to inventory 
                Inventory.addProduct(addProduct);
            }
            /* The line below this comment becomes partially highlighed as an error
            if not declared */
            onActionReturntoMain(event);
        }
        /* The following lines catch remaining NFEs. An alert is sent if either a 
        field is left blank or if a field entry is an invalid format. An alternative 
        to this is to check every single field and add each line in the if else 
        above. I have done it this way to make the code shorter. */
        catch (NullPointerException | NumberFormatException e)
        {
            InventoryAlerts.errorDialog(1, modifyNameTxt);
        }
    }

    /**
     * Checks if the input is an integer.
     *
     * @param input the text being checked
     * @return returns true or false
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
     * Initializes the Modify Product screen.
     *
     * @param url pointer to the resource
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        // Set items of product selected from main screen to appropriate text areas.
        assocPart = getAllProducts().get(productIndex);
        productNum = getAllProducts().get(productIndex).getId();
        modifyProductIdTxt.setText(Integer.toString(assocPart.getId()));
        modifyNameTxt.setText(assocPart.getName());
        modifyCostTxt.setText(Double.toString(assocPart.getPrice()));
        modifyMinTxt.setText(Integer.toString(assocPart.getMin()));
        modifyMaxTxt.setText(Integer.toString(assocPart.getMax()));
        modifyInventoryTxt.setText(Integer.toString(assocPart.getStock()));

        // Set top table 
        addTableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        addTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        addTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Set bottom table and fill with associated products
        removeTableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        removeTableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        removeTableColumnInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        removeTableColumnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Populate tables 
        topAddTableView.setItems(getAllParts());
        assocParts = assocPart.getAllAssociatedParts();
        bottomRemoveTableView.setItems(assocParts);
    }
}
