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
public abstract class Event implements Serializable
{
    private Simulator _simulator;
    private int _context;
    
    public Event(Simulator s)
    {
        _simulator = s;
        _context = 0;
    }
    
    public void setContext(int c)
    {
        _context = c;
    }
    
    public int getContext()
    {
        return _context;
    }
    
    public Simulator getSimulator()
    {
        return _simulator;
    }
    
    public abstract void behavior();    
}
