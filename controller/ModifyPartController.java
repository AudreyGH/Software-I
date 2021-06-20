package controller;

import static controller.MainFormController.getPartIndex;
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
import static model.Inventory.getAllParts;
import static model.Inventory.updatePart;
import model.InHouse;
import model.Outsourced;
import model.Part;

/**
 * This is the FXML controller class for the Modify Part screen.
 *
 * @author Audrey Hababag
 */
public class ModifyPartController implements Initializable
{

    Stage stage;
    Parent scene;
    int partNum;
    private final int partIndex = getPartIndex();

    @FXML
    private RadioButton inHouseRadioButton;
    @FXML
    private RadioButton outsourcedRadioButton;
    @FXML
    private TextField modifyPartIdTxt;
    @FXML
    private TextField modifyPartNameTxt;
    @FXML
    private TextField modifyPartInventoryTxt;
    @FXML
    private TextField modifyPartCostTxt;
    @FXML
    private TextField modifyPartMaxTxt;
    @FXML
    private TextField modifyPartMinTxt;
    @FXML
    private Label machineIdLabel;
    @FXML
    private TextField modifyPartMachineIdTxt;

    /**
     * Changes the label and prompt text to Machine ID.
     *
     * @param event when In-House radio button is selected
     */
    @FXML
    void inHouseRadioButton(ActionEvent event)
    {
        machineIdLabel.setText("Machine ID");
        modifyPartMachineIdTxt.setPromptText("Machine ID");
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
        modifyPartMachineIdTxt.setPromptText("Company Name");
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
     * Saves the field entries on the Modify Part screen.
     * <p>
     * For an undetermined reason, the name field is the only field that can be
     * left blank without receiving any errors when saving. To fix this, the
     * method checks for an empty name field and calls an alert with an ERROR
     * dialog. This can be replicated by commenting out the if statement between
     * lines 143 and 147, running the program, going to add part screen filling
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
    void onActionSaveButton(ActionEvent event) throws IOException
    {
        try
        {
            String name = modifyPartNameTxt.getText();
            double price = Double.parseDouble(modifyPartCostTxt.getText());
            int inv = Integer.parseInt(modifyPartInventoryTxt.getText());
            int min = Integer.parseInt(modifyPartMinTxt.getText());
            int max = Integer.parseInt(modifyPartMaxTxt.getText());
            String machID = modifyPartMachineIdTxt.getText();

            /* The line sends an alert if machine id is not an integer while 
            in-house radio button is selected. A separate method that checks the 
            integer is used. */
            if (inHouseRadioButton.isSelected() && !isInteger(modifyPartMachineIdTxt.getText()))
            {
                InventoryAlerts.errorDialog(8, modifyPartMachineIdTxt);
                return;
            }
            /* The line alerts if name field is empty or null. This has to be 
            done because for some reason, a blank name field is accepted when saved */
            if (name == null || name.isEmpty())
            {
                InventoryAlerts.errorDialog(1, modifyPartNameTxt);
                return;
            }
            // Sends an alert if min is greater than max
            if (min > max)
            {
                InventoryAlerts.errorDialog(3, modifyPartMinTxt);
                return;
            }
            // Sends an alert if price is equals to or less than zero
            if (price <= 0)
            {
                InventoryAlerts.errorDialog(2, modifyPartCostTxt);
                return;
            }
            // Sends an alert if inventory is less than one
            if (inv < 1)
            {
                InventoryAlerts.errorDialog(6, modifyPartInventoryTxt);
                return;
            }
            // alert if inventory is less than min or greater than max
            if (inv < min || inv > max)
            {
                InventoryAlerts.errorDialog(5, modifyPartInventoryTxt);
                return;
            }
            /* This line creates a new Inhouse Part object if the In-Housoe radio
            button is selected. */
            else if (inHouseRadioButton.isSelected())
            {
                Part updateParts1 = new InHouse(
                  partNum, name, price, inv, min, max, Integer.parseInt(machID));

                updatePart(partIndex, updateParts1);
            }
            // Creates a new part if Outsourced radio button is selected
            else if (outsourcedRadioButton.isSelected())
            {
                Part updateParts2 = new Outsourced(
                  partNum, name, price, inv, min, max, machID);

                updatePart(partIndex, updateParts2);
            }
            /* The line below this comment becomes partially highlighed as an error
        if not declared */
            onActionReturntoMain(event);
        }
        /* The following lines catch remaining NFEs. An alert is sent if either a 
        field is left blank or if a field entry is an invalid format. An alternative 
        to this is to check every single field and add each line in the if else 
        above. I have done it this way to make the code shorter. */
        catch (NumberFormatException e)
        {
            InventoryAlerts.errorDialog(1, modifyPartNameTxt);
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
        // Set items of part selected from main screen to appropriate text areas. 
        Part tempPart = getAllParts().get(partIndex);
        partNum = getAllParts().get(partIndex).getId();
        modifyPartIdTxt.setText(Integer.toString(tempPart.getId()));
        modifyPartNameTxt.setText(tempPart.getName());
        modifyPartCostTxt.setText(Double.toString(tempPart.getPrice()));
        modifyPartMinTxt.setText(Integer.toString(tempPart.getMin()));
        modifyPartMaxTxt.setText(Integer.toString(tempPart.getMax()));
        modifyPartInventoryTxt.setText(Integer.toString(tempPart.getStock()));

        /* The label is changed to "machine id" if in-house, and "company name"
        if outsourced. */
        if (tempPart instanceof InHouse)
        {
            inHouseRadioButton.setSelected(true);
            machineIdLabel.setText("Machine ID");
            modifyPartMachineIdTxt.setText(Integer.toString(((InHouse) tempPart).getMachineId()));
        }
        if (tempPart instanceof Outsourced)
        {
            outsourcedRadioButton.setSelected(true);
            machineIdLabel.setText("Company Name");
            modifyPartMachineIdTxt.setText(((Outsourced) tempPart).getCompanyName());
        }
    }
}
