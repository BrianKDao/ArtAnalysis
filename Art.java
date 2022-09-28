/****************************************************************************
 * ArtAnalysis
 ****************************************************************************
 * Analyzes art
 *_____________________________________________________
 * Brian Dao
 * 5/7/2021
 * CMSC-255-003-SP2021
 ****************************************************************************/

package Projects.Project07;

public class Art
{
    /**
     * Initializes the variables
     */
    private String name;
    private String creator;
    private double value;
    private String location;

    public Art()
    {
        /**
         * Sets default values
         */
        this.name = null;
        this.creator = null;
        this.value = 0.0;
        this.location = null;
    }

    public Art(String name, String creator, double value, String location)
    {
        /**
         * Sets the inputted values to the Object
         */
        this.name = name;
        this.creator = creator;
        this.value = value;
        this.location = location;
    }

    public String getName()
    {
        /**
         * Returns name
         */

        return this.name;
    }

    public void setName(String name)
    {
        /**
         * Sets name
         */

        this.name = name;
    }

    public String getCreator()
    {
        /**
         * Returns creator
         */

        return this.creator;
    }

    public void setCreator(String creator)
    {
        /**
         * Sets Creator
         */

        this.creator = creator;
    }

    public double getValue()
    {
        /**
         * Returns value
         */

        return this.value;
    }

    public void setValue(double value)
    {
        /**
         * Sets Value
         */

        this.value = value;
    }

    public String getLocation()
    {
        /**
         * Returns location
         */

        return this.location;
    }

    public void setLocation(String location)
    {
        /**
         * Sets Location
         */

        this.location = location;
    }

    public String toString()
    {
        /**
         * Returns a formatted String of the Object
         */

        return this.name + " " + this.creator + " " + String.format("%.2f", this.value) + " " + this.location;
    }

    public boolean equals(Object art)
    {
        /**
         * Uses and if statement that checks if the there's an instance of an artwork by a given creator
         */

        if(art instanceof Art)
        {

            if(((Art)art).getName().equals(this.getName()) && ((Art)art).getCreator().equals(this.getCreator()))
            {
                return true;
            }
        }
        return false;
    }
}
