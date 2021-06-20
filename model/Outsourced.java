package model;

/**
 * The Outsourced class
 * @author Audrey Hababag
 */
public class Outsourced extends Part
{

    String companyName;

    /**
     * Class constructor specifying number of objects to create.
     *
     * @param id the id
     * @param name the name
     * @param price the price
     * @param stock the stock
     * @param min the Min
     * @param max the Max
     * @param companyName the company name
     */
    public Outsourced(int id, String name, double price, int stock, int min,
      int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * This is the setter for companyName.
     *
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    /**
     * This is the getter for companyName.
     *
     * @return returns the company name
     */
    public String getCompanyName()
    {
        return companyName;
    }

}
