/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;

import java.io.Serializable;
import sim.Event;
import sim.Generator;
import sim.Simulator;
import sim.Time;

/**
 *
 * @author Willi
 */
public class GeneratorZakaznikov extends Event implements Serializable
{
    private static int _idZakaznika;
    private int _interval;
    private boolean _debug;
    private long _zmenaCasu;
    private boolean _debugFlag;
    
    
    public GeneratorZakaznikov(Simulator s, int interval,long cas) {
        super(s);
        _idZakaznika = 0;
        _debug = ((SimulatorObchodu)getSimulator()).getParametre().isDebugGenerator();
        _interval = interval;
        _zmenaCasu = cas;
        _debugFlag = (_debug == true ? true : false);
        
    }

    public void generuj()
    {
        if(_debug)
        {
            if(_debugFlag)    
                System.out.println("Prichod zakaznikov s intervalom " + Time.convertTime(_interval) + " v case: " + Time.convertTime(getSimulator().getSimTime()));
                _debugFlag = false;
        }
        
        long pom = Generator.exponential(_interval);
        // Generovanie zakaznikov iba do urciteho casu, potom su naplanovany dalsi zakaznici s inym intervalom
        if( (getSimulator().getSimTime() + pom) <= _zmenaCasu) 
        {
            // planovanie udalosti         
            getSimulator().scheduleAt(new Zakaznik(getSimulator(), ++_idZakaznika));
            getSimulator().scheduleAt(this, getSimulator().getSimTime() + pom);
        }
        else
        {
           if(_debug)    
                System.out.println("Koniec generovania zakaznikov s intervalom " + Time.convertTime(_interval) + " v case: " + Time.convertTime(getSimulator().getSimTime()));
        }
        
    }
    
    @Override
    public void behavior() 
    {
        generuj();
    }
}
