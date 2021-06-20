package controller;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * This class holds all the alert methods.
 * 
 * @author Audrey Hababag
 */
public class InventoryAlerts
{

    /**
     * This method alerts the user when deleting a product or a part.
     * 
     * @param delete the product or part to delete
     * @return a confirmation dialog window
     */
    public static boolean deleteConfirm(String delete)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Delete " + delete + "?");
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    /**
     * This method alerts the user if a field is empty.
     */
    public static void emptyString()
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setContentText("Check for empty fields.");
        alert.showAndWait();
    }

    /**
     * This method alerts the user when no match is found using the search
     * field.
     */
    public static void notFound()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText("No match found");
        alert.showAndWait();
    }

    /**
     * This method alerts the user of a number of errors. While adding and
     * modifying items in the inventory the user will receive alerts according to
     * the requirements of the inventory fields.
     * 
     * @param num the case number
     * @param text the TextField
     */
    public static void errorDialog(int num, TextField text)
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        switch (num)
        {
            case 1:
            {
                alert.setContentText("Field is either empty or the format is invalid");
                break;
            }
            case 2:
            {
                alert.setContentText("The price cannot be negative or $0.");
                break;
            }
            case 3:
            {
                alert.setContentText("The MIN must be less than or equal to MAX.");
                break;
            }
            case 4:
            {
                alert.setContentText("Wrong format.");
                break;
            }
            case 5:
            {
                alert.setContentText("The stock value must be between MIN and MAX");
                break;
            }
            case 6:
            {
                alert.setContentText("The minimum stock is 1.");
                break;
            }
            case 7:
            {
                alert.setContentText("Price must be greater than the overall cost of associated parts.");
                break;
            }
            case 8:
            {
                alert.setContentText("Machine ID field must contain number/s.");
                break;
            }
            case 9:
            {
                alert.setContentText("Select a part");
                break;
            }
            case 10:
            {
                alert.setContentText("This part is already associated");
                break;
            }
            case 11:
            {
                alert.setContentText("Cannot delete products with associated parts.");
                break;
            }
            case 12:
            {
                alert.setContentText("Select a product.");
                break;
            }
            default:
            {
                alert.setContentText("Unknown error.");
                break;
            }
        }
        alert.showAndWait();
    }
}
