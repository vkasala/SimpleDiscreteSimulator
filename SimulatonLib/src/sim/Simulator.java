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
public class Simulator implements Serializable
{
    private Calendar _calendar;
    private long _timeStart;
    private long _timeEnd;
    private long _simTime;
 
    public Simulator(long t_start, long t_end)
    {
        _timeStart = t_start;
        _timeEnd = t_end;
        _simTime = t_start;
        _calendar = new Calendar();
    }
    
    public void run(long end)
    {
        while(!_calendar.isEmpty())
        {
            ActiveRecord record = _calendar.getFirstActiveRecord();
            if(record.getActiveTime() > end || record.getActiveTime() > getTimeEnd() )
                break;
            
            Event ev = record.getEvent();
            _calendar.removeFirstActiveRecord();
            // Aktualizujem simulacny cas
            _simTime = record.getActiveTime();
            // vykonam metodu udalosti
            ev.behavior();
        }
    }
    // Simulacia prebehne do konca 
    public void run ()
    {
        run(getTimeEnd());
    }
    
    public void scheduleAt(Event event)
    {
        scheduleAt(event, _simTime);
    }
    
    public void scheduleAt(Event event, long time)
    {
        _calendar.addActiveRecord(new ActiveRecord(event, time));
    }
    
    public long getSimTime()
    {
        return _simTime;
    }
    
    public Simulator createCopy()
    {
        return (Simulator) DeepCopy.copy(this);
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "\nStart Simulacie cas: " + this._timeStart + "\t Koniec simulacie v case: " + Time.convertTime(getTimeEnd());
    }

    /**
     * @return the _timeEnd
     */
    public long getTimeEnd() {
        return _timeEnd;
    }
    
}
