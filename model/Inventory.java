package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory Class.
 *
 * @author Audrey Hababag
 */
public class Inventory
{

    private static ObservableList<Part> allParts
      = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts
      = FXCollections.observableArrayList();
    private static int partNum = 0;
    private static int productNum = 0;

    /**
     * This method adds a new part to the list.
     *
     * @param newPart the part to add
     */
    public static void addPart(Part newPart)
    {
        allParts.add(newPart);
    }

    /**
     * This method adds a new product to the list.
     *
     * @param newProduct the product to add
     */
    public static void addProduct(Product newProduct)
    {
        allProducts.add(newProduct);
    }

    /**
     * Searches for Part using integer part id.
     *
     * @param partId id of the part to search
     * @return returns id of searched part
     */
    public static Part lookupPart(int partId)
    {
        for (Part look : getAllParts())
        {
            if (look.getId() == partId)
            {
                return look;
            }
        }
        return null;
    }

    /**
     * Searches for Product using integer product id.
     *
     * @param productId id of item to search
     * @return returns id of searched product
     */
    public static Product lookupProduct(int productId)
    {
        for (Product look : getAllProducts())
        {
            if (look.getId() == productId)
            {
                return look;
            }
        }
        return null;
    }

    /**
     * Searches for a part using String part name.
     *
     * @param partName the part name
     * @return returns a list of parts if found
     */
    public static ObservableList<Part> lookupPart(String partName)
    {
        ObservableList tempList = FXCollections.observableArrayList();
        for (Part look : getAllParts())
        {
            if (look.getName().toLowerCase().contains(partName.toLowerCase()))
            {
                tempList.add(look);
            }

        }
        return tempList;
    }

    /**
     * Searches for a product using String product name.
     *
     * @param productName the product name
     * @return returns the a list of products if found
     */
    public static ObservableList<Product> lookupProduct(String productName)
    {
        ObservableList tempList = FXCollections.observableArrayList();
        for (Product look : getAllProducts())
        {
            if (look.getName().toLowerCase().contains(productName.toLowerCase()))
            {
                tempList.add(look);
            }
        }
        return tempList;
    }

    /**
     * Updates a part in the list.
     *
     * @param index index of the part to update
     * @param selectedPart returns method that updates the list
     */
    public static void updatePart(int index, Part selectedPart)
    {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product in the list.
     *
     * @param index index of the product to update
     * @param newProduct returns a method that updates the list
     */
    public static void updateProduct(int index, Product newProduct)
    {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a single item on the list.
     *
     * @param selectedPart the part to delete
     * @return returns a method that deletes one part
     */
    public static boolean deletePart(Part selectedPart)
    {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a single item on the list.
     *
     * @param selectedProduct the product to delete
     * @return returns a method that deletes one product
     */
    public static boolean deleteProduct(Product selectedProduct)
    {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Getter for allParts.
     *
     * @return returns allParts
     */
    public static ObservableList<Part> getAllParts()
    {
        return allParts;
    }

    /**
     * Getter for allProducts.
     *
     * @return returns allProducts
     */
    public static ObservableList<Product> getAllProducts()
    {
        return allProducts;
    }

    /**
     * This method counts the number of parts in a list.
     *
     * @return the size of parts list
     */
    public static int getPartNumber()
    {
        partNum = getAllParts().size();
        return partNum;
    }

    /**
     * This method counts the number of products in a list.
     *
     * @return the size of products list
     */
    public static int getProductNumber()
    {
        productNum = getAllProducts().size();
        return productNum;
    }

}
