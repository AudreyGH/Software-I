package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Product Class.
 *
 * @author Audrey Hababag
 */
public class Product
{

    private ObservableList<Part> associatedParts
      = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Class constructor specifying number of objects to create.
     *
     * @param id the id
     * @param name the name
     * @param price the price
     * @param stock the stock
     * @param min the min
     * @param max the max
     */
    public Product(int id, String name, double price, int stock,
      int min, int max)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * This is the id setter.
     *
     * @param id the id to set
     */
    public void setId(int id)
    {
        this.id = id;
    }

    /**
     * This is the name setter.
     *
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * This is the price setter.
     *
     * @param price the price to set
     */
    public void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * This is the stock setter.
     *
     * @param stock the stock to set
     */
    public void setStock(int stock)
    {
        this.stock = stock;
    }

    /**
     * This is the min setter.
     *
     * @param min the minimum to set
     */
    public void setMin(int min)
    {
        this.min = min;
    }

    /**
     * This is the max setter.
     *
     * @param max the maximum to set
     */
    public void setMax(int max)
    {
        this.max = max;
    }

    /**
     * This is the id getter.
     *
     * @return returns the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * This is the name getter.
     *
     * @return returns the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * This is the price getter.
     *
     * @return returns the price
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * This is the stock getter.
     *
     * @return returns the stock
     */
    public int getStock()
    {
        return stock;
    }

    /**
     * This is the min getter.
     *
     * @return returns the min
     */
    public int getMin()
    {
        return min;
    }

    /**
     * This is the max getter.
     *
     * @return returns the max
     */
    public int getMax()
    {
        return max;
    }

    /**
     * This method adds associated parts of a product.
     *
     * @param part the part to add
     */
    public void addAssociatedPart(Part part)
    {
        associatedParts.add(part);
    }

    /**
     * This method deletes associated parts.
     *
     * @param selectedAssociatedpPart the part to delete
     * @return returns a method called to delete the selected part
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedpPart)
    {
        return associatedParts.remove(selectedAssociatedpPart);
    }

    /**
     * This method is a getter for all associated parts.
     *
     * @return returns associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts()
    {
        return associatedParts;
    }

    /**
     * This method searches for associated parts.
     *
     * @param part the part to search
     * @return return the matching part/s
     */
    public Part searchAssociatedPart(int part)
    {

        for (Part i : getAllAssociatedParts())
        {
            if (i.getId() == part)
            {
                return i;
            }
        }
        return null;
    }
}
