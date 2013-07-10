/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.io.Serializable;

/**
 *
 * @author Willi
 */
public class Facility extends Device implements Serializable
{
    private final int defaultFacilityPriority = 0;
    private final int defaultFacilityCapacity = 1;
    
    public Facility(String name, Simulator simulator)
    {
        super(name, simulator, 1);                 
    }
    
    public void seize(Event ev, int priority)
    {
        this.add(ev, priority, defaultFacilityCapacity);
    }
    
    public void seize(Event ev)
    {
        this.seize(ev, defaultFacilityPriority);
    }
    
    public void release() throws Exception
    {
        this.remove(defaultFacilityCapacity);
    }   
    
}
