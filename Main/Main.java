package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

/**
 * This is the main class.
 *
 * @author Audrey Hababag
 */
public class Main extends Application
{

    /**
     * This is the main method.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        launch(args);

    }

    /**
     * Starts the main menu of the inventory.
     *
     * @param stage the stage
     * @throws Exception if unable to load the fxml file
     */
    @Override
    public void start(Stage stage) throws Exception

    {
        testData(); // Calls test data

        // Load the FXML file. 
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));

        // Build the Scene and display 
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Inventory Menu");
        stage.show();
    }

    /**
     * This method provides sample data. The user can choose to comment out
     * the method call if an empty inventory is desired.
     */
    void testData()
    {

        // Add Parts In-house and Outsourced *
        Part p1 = new InHouse(1, "Spring", 1.00, 150, 1, 999, 501);
        Part p2 = new InHouse(2, "Omron Switch", .15, 150, 1, 999, 502);
        Part p3 = new InHouse(3, "Outemu Black", .99, 500, 100, 2500, 503);
        Part p4 = new Outsourced(4, "Outemu Blue", .99, 500, 100, 2500, "Outemu");
        Part p5 = new Outsourced(5, "Aluminum Case 75", 65.99, 50, 50, 100, "KBDfans");
        Part p6 = new Outsourced(6, "Alum Key Puller", 3.50, 80, 50, 120, "KRepublic");

        Inventory.addPart(p1);
        Inventory.addPart(p2);
        Inventory.addPart(p3);
        Inventory.addPart(p4);
        Inventory.addPart(p5);
        Inventory.addPart(p6);

        // Add Products and Associated Parts 
        Product pr1 = new Product(1, "L Mouse", 69.00, 20, 100, 150);
        pr1.addAssociatedPart(p2);
        Product pr2 = new Product(2, "H Mouse", 79.00, 120, 100, 150);
        pr2.addAssociatedPart(p2);
        Product pr3 = new Product(3, "MechKB DK61", 59.00, 20, 10, 50);
        pr3.addAssociatedPart(p4);
        pr3.addAssociatedPart(p5);
        pr3.addAssociatedPart(p6);
        Product pr4 = new Product(4, "Key Tester", 20.99, 40, 25, 80);
        pr4.addAssociatedPart(p1);
        Product pr5 = new Product(5, "Touchmaster Five Typewriter", 499.00, 1, 1, 1);

        Inventory.addProduct(pr1);
        Inventory.addProduct(pr2);
        Inventory.addProduct(pr3);
        Inventory.addProduct(pr4);
        Inventory.addProduct(pr5);

    }

}
