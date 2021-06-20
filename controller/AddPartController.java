package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

/**
 * This is the FXML controller class for Add Part screen.
 *
 * @author Audrey Hababag
 */
public class AddPartController implements Initializable
{

    Stage stage;
    Parent scene;
    int partNum;

    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField addPartIdTxt;
    @FXML
    private TextField addPartNameTxt;
    @FXML
    private TextField addPartInventoryTxt;
    @FXML
    private TextField addPartCostTxt;
    @FXML
    private TextField addPartMaxTxt;
    @FXML
    private TextField addPartMinTxt;
    @FXML
    private Label machineIdLabel;
    @FXML
    private TextField addPartMachineIdTxt;

    /**
     * Changes the label and prompt text to Machine ID.
     *
     * @param event when In-House radio button is selected
     */
    @FXML
    void inHouseRadioButton(ActionEvent event)
    {
        machineIdLabel.setText("Machine ID");
        addPartMachineIdTxt.setPromptText("MachineID");
    }

    /**
     * Changes the label and prompt text to Company Name.
     *
     * @param event when Outsourced radio button is selected
     */
    @FXML
    void outsourcedRadioButton(ActionEvent event)
    {
        machineIdLabel.setText("Company Name");
        addPartMachineIdTxt.setPromptText("Company Name");
    }

    /**
     * Saves the fields on the Add Part screen.
     * <p>
     * For an undetermined reason, the name field is the only field that can be
     * left blank without receiving any errors when saving. To fix this, the
     * method checks for an empty name field and calls an alert with an ERROR
     * dialog. This can be replicated by commenting out the if statement between
     * lines 131 and 135, running the program, going to add part screen filling
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
    void onActionAddPartSave(ActionEvent event) throws IOException
    {
        try
        {
            String name = addPartNameTxt.getText();
            double price = Double.parseDouble(addPartCostTxt.getText());
            int inv = Integer.parseInt(addPartInventoryTxt.getText());
            int min = Integer.parseInt(addPartMinTxt.getText());
            int max = Integer.parseInt(addPartMaxTxt.getText());
            String machID = addPartMachineIdTxt.getText();

            /* The line sends an alert if machine id is not an integer while 
            in-house radio button is selected. A separate method that checks the 
            integer is used. */
            if (inHouseRadioButton.isSelected() && !isInteger(addPartMachineIdTxt.getText()))
            {
                InventoryAlerts.errorDialog(8, addPartMachineIdTxt);
                return;
            }
            /* The line alerts if name field is empty or null. This has to be 
            done because for some reason, a blank name field is accepted when 
            saved. All the other fields get an error message on the output
            screen when left empty. */
            if (name == null || name.isEmpty())
            {
                InventoryAlerts.errorDialog(1, addPartNameTxt);
                return;
            }
            // Sends an alert if min is greater than max
            if (min > max)
            {
                InventoryAlerts.errorDialog(3, addPartMinTxt);
                return;
            }
            // Sends an alert if price is equals to or less than zero
            if (price <= 0)
            {
                InventoryAlerts.errorDialog(2, addPartCostTxt);
                return;
            }
            // Sends an alert if inventory is less than one
            if (inv < 1)
            {
                InventoryAlerts.errorDialog(6, addPartInventoryTxt);
                return;
            }
            // Sends an elrt if inventory is less than Min or greater than Max
            if (inv < min || inv > max)
            {
                InventoryAlerts.errorDialog(5, addPartInventoryTxt);
                return;
            }
            /* This line creates a new Inhouse Part object if the In-Housoe radio
            button is selected. */
            else if (inHouseRadioButton.isSelected())
            {
                Part addParts = new InHouse(
                  partNum, name, price, inv, min, max, Integer.parseInt(machID));
                Inventory.addPart(addParts);
            }
            // Creates a new part if Outsourced radio button is selected
            else if (outsourcedRadioButton.isSelected())
            {
                Part addParts = new Outsourced(
                  partNum, name, price, inv, min, max, machID);
                Inventory.addPart(addParts);
            }

            /* The line below this comment becomes partially highlighed as an error
            if not declared */
            onActionReturnToMain(event);
        }
        /* The following lines catch remaining NFEs. An alert is sent if either a 
        field is left blank or if a field entry is an invalid format. An alternative 
        to this is to check every single field and add each line in the if else 
        above. I have done it this way to make the code shorter. */
        catch (NumberFormatException e)
        {
            InventoryAlerts.errorDialog(1, addPartNameTxt);
        }
    }

    /**
     * Returns the user to the main screen.
     * <p>
     * The throws clause on the method header handles the IOException that is
     * highlighted by the editor at the FXMLLoader.load line.
     *
     * @param event request to return to main screen
     * @throws IOException if not caught or declared
     */
    @FXML
    void onActionReturnToMain(ActionEvent event) throws IOException
    {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        /* The line below this comment becomes partially highlighed as an error
        if not declared */
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
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
     * Initializes the part index.
     *
     * @param url pointer to the resource
     * @param rb the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        //Increment product ID by 1 
        partNum = Inventory.getPartNumber() + 1;
        addPartIdTxt.setPromptText("" + partNum);
    }
}
