/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.io.Serializable;
import java.util.PriorityQueue;

/**
 *
 * @author Willi
 */
class Calendar implements Serializable
{
    private PriorityQueue<ActiveRecord> queue;
    
    public Calendar()
    {
        queue = new PriorityQueue<ActiveRecord>();
    }
    
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }
    
    public void addActiveRecord(ActiveRecord activeRecord)
    {
        queue.add(activeRecord);
    }
   
    // vrati zaznam
    public ActiveRecord getFirstActiveRecord()
    {
        return queue.peek();
    }
    // odstrani zaznam
    public void removeFirstActiveRecord()
    {
        queue.poll();
    }
}
