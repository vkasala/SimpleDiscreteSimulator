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
public class Store extends Device implements Serializable
{
    private final int defaultStorePriority = 0;
    
    public Store(String name, Simulator simulator, int capacity)
    {
        super(name, simulator, capacity);                 
    }    
   
    public void enter(Event ev, int capacity)
    {
        this.add(ev, defaultStorePriority, capacity);
    }
    
    public void leave(int capacity) throws Exception
    {
        this.remove(capacity);
    } 
    public void setCapacity(int capacity) throws Exception
    {
        this.changeCapacity(capacity);
    }
    
}
