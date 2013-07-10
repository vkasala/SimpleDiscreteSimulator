/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Willi
 */
public class Device implements Serializable
{
    private int _maxCapacity;
    private int _capacity;
    private int _takenCapacity;
    private String _name;
    private Simulator _simulator;

    private ArrayList<Item> _storeQueue;
    private Statistics _statistics;
            
    public Device(String name, Simulator simulator, int capacity)
    {
        _name = name;
        _simulator = simulator;
        _maxCapacity = capacity;
        _capacity = capacity;
        _takenCapacity = 0;
        _storeQueue = new ArrayList<Item>();
        _statistics = new Statistics(_name);
    }
    
    public boolean isBusy()
    {
        return _takenCapacity == _capacity;
    }
    
    protected void add(Event ev, int priority, int capacity)
    {
        // Ak sa nepodarilo naplanovat, potom pridame do fronty
        if (!sheduleToSimulator(ev, capacity))
        {
            Item item = new Item(priority, ev, capacity, _simulator.getSimTime());

            // Najde index vo fronte pre vlozenie eventu podla priority
            int idx = 0;
            for (idx = 0; idx < _storeQueue.size(); idx++) {
                Item currentItem = _storeQueue.get(idx);
                if (item._priority <= currentItem._priority)
                    break;
            }

            _storeQueue.add(idx, item);
        }
        else
            getStatistics().addWaitingTime(0);
    }
    
    /**
     * Naplanauje udalost v simulatore.
     * @param ev udalost
     * @param capacity kapacita
     * @return true, ak sa podarilo naplanovat a false, ak sa nepodarilo.
     */
    protected boolean sheduleToSimulator(Event ev, int capacity)
    {
        if (_takenCapacity + capacity <= _capacity)
        {             
            _takenCapacity += capacity;
            _simulator.scheduleAt(ev);
            return true;
        }
        
        return false;
    }
    
    protected void remove(int capacity) throws Exception
    {
        _takenCapacity -= capacity;
        if (_takenCapacity < 0)
            _takenCapacity = 0;
        
        if (!_storeQueue.isEmpty())
        {
            Item item = _storeQueue.get(_storeQueue.size() - 1);
            if (sheduleToSimulator(item.getEvent(), item.getCapacity()))
            {
                _storeQueue.remove(_storeQueue.size() - 1);
                getStatistics().addWaitingTime(_simulator.getSimTime() - item.getTimeArrival());
            }
        }
    }
    
    protected void changeCapacity(int newCapacity) throws Exception
    {
        // Kontrola novej kapacity, nemoze prekrocit maximalnu moznu a ani klesnut alebo sa rovnat 0 
        if(newCapacity <= 0 || newCapacity > _maxCapacity)
            throw new Exception("Neplatna nova kapacita!");
        
        int diff = newCapacity - _capacity;
         _capacity = newCapacity;
         
        //Ak je nova kapacita vacsia ako predchadzajuca nastavena, tak mozu zakaznici pristupit k obsluhe
        if(diff > 0)
        {   
            if (!_storeQueue.isEmpty())
            {    
                Item item = _storeQueue.get(_storeQueue.size() - 1);
                // Je k dispozici prvok z fronty
                while (item != null)
                {
                    // Zmenil som kapacitu storu, mozem vybrat s vnutornej fronty nejake prvky, cim simulujem zabratie urcitej kapacity
                    // Zabere sa urcity pocet
                    if (sheduleToSimulator(item.getEvent(), item.getCapacity()))
                    {
                        _storeQueue.remove(_storeQueue.size() - 1);
                        getStatistics().addWaitingTime(_simulator.getSimTime() - item.getTimeArrival());
                    }
                    else 
                        break;
                    item = _storeQueue.size() > 0 
                        ? _storeQueue.get(_storeQueue.size() - 1) 
                            : null;
                            
                }
            }
        }
        //_capacity = newCapacity;
    }

    /**
     * @return the _statistics
     */
    public Statistics getStatistics() {
        return _statistics;
    }
       
    // Pomocna trieda reprezentujuca polozku v zozname
    private static class Item implements Serializable
    {
        private int _priority;
        private Event _event;
        private int _capacity;
        private long _timeArrival;
        
        public int getPriority() { return _priority; }
        public Event getEvent() { return _event; }
        public int getCapacity() { return _capacity; }
        public long getTimeArrival() { return _timeArrival; }
        
        public Item(int priority, Event event, int capacity, long timeArrival)
        {
            this._priority = priority;
            this._event = event;
            this._capacity = capacity;
            this._timeArrival = timeArrival;
        }       
    }
}
