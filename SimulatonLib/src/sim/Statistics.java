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
public class Statistics implements Serializable {
    private String _name;
    private ArrayList<Long> _waitingTimeList;
    private long _minWait;
    private long _maxWait; 

    public Statistics(String name) 
    {
        _name = name;
        _minWait = Long.MAX_VALUE;
        _maxWait = 0;
        _waitingTimeList = new ArrayList<Long>();        
    }
    
    public void addWaitingTime(long time)
    {
        // najdenie minimalneho casu straveneho vo fronte
        if(_minWait > time)
            _minWait = time;
        // najdenie maximalneho casu straveneho vo fronte
        if(_maxWait < time)
            _maxWait = time;
        // pridanie casu straveneho vo fronte do zoznamu
        _waitingTimeList.add(time);        
    }
    
    /**
     * @return the minWait
     */
    public long getMinWait() {
        return _minWait;
    }

    /**
     * @return the maxWait
     */
    public long getMaxWait() {
        return _maxWait;
    }

    /**
     * @return the avgWait
     */
    public double getAvgWait() 
    {
        long suma = 0;
        for (Long time : _waitingTimeList) {
            suma += time;
        }
        return suma / (double)_waitingTimeList.size();
    }
    
    public int getCount()
    {
        return _waitingTimeList.size();
    }
    
    public void eraseStatistics()
    {
        _minWait = Long.MAX_VALUE;
        _maxWait = 0;
        _waitingTimeList.clear();   
    }
    
    @Override
    public String toString() {
        return "Statistiky " + getName() + "\n MinTime: \t" + _minWait + "\n MaxTime: \t" + _maxWait + "\n AvgTime: \t" + getAvgWait() + "\nPocet zakaznikov: \t" + getCount();
    }

    /**
     * @return the _name
     */
    public String getName() {
        return _name;
    }

    /**
     * @param name the _name to set
     */
    public void setName(String name) {
        this._name = name;
    }
    
    
}
