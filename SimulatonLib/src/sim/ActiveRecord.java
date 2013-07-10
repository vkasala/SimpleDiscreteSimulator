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
class ActiveRecord implements Comparable<ActiveRecord>, Serializable
{
    private long activeTime;
    //private int priority;
    private Event event;
    
    public ActiveRecord(Event e, long t)
    {
        event = e;
        activeTime = t;
    }
    
    public long getActiveTime()
    {
        return activeTime;
    }
    
    public Event getEvent()
    {
        return event;
    }

    @Override
    public int compareTo(ActiveRecord o) {
        if (this.activeTime > o.activeTime)
            return 1;
        else if (this.activeTime < o.activeTime) 
            return -1;
        else 
            return 0;
    }
}
