package model;

/**
 * The Inhouse class.
 *
 * @author Audrey Hababag
 */
public class InHouse extends Part
{

    int machineId;

    /**
     * Class constructor specifying number of objects to create.
     *
     * @param id the id
     * @param name the name
     * @param price the price
     * @param stock the stock
     * @param min the Min
     * @param max the Max
     * @param machineId the machine id
     */
    public InHouse(int id, String name, double price, int stock, int min,
      int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    /**
     * This is the setter for machineId.
     * 
     * @param machineId the machine id to set
     */
    public void setMachineId(int machineId)
    {
        this.machineId = machineId;
    }

    /**
     * This is the getter for machineId.
     * 
     * @return returns the machine Id
     */
    public int getMachineId()
    {
        return machineId;
    }

}
