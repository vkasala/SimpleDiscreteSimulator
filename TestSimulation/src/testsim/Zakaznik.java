/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;

import sim.Time;
import java.io.Serializable;
import sim.Event;
import sim.Generator;
import sim.Simulator;

/**
 *
 * @author Willi
 */
public class Zakaznik extends Event implements Serializable
{
    private int _id;
    private boolean _debug;
    private long _casPrichodu;
    
    Zakaznik(Simulator simulator, int id)
    {
        super(simulator);
        _id = id;
        _debug = ((SimulatorObchodu)getSimulator()).getParametre().isDebugZakaznik();
    }
    
    private void prichod()
    {
        if(_debug)
            System.out.println("Zakaznik: " + _id + "\tPRISIEL ZAKAZNIK \tTime: " + Time.convertTime(getSimulator().getSimTime()));
        // ulozim si cas prichodu
        _casPrichodu = getSimulator().getSimTime();
        
        setContext(1);
        ((SimulatorObchodu)getSimulator()).getObsluha1().enter(this,1);
    }
    
    private void obsluhaPriPrvejPrepazke()
    {
        if(_debug)
            System.out.println("Zakaznik: " + _id + "\tOBSLUHA 1 PREPAZKE \tTime: " + Time.convertTime(getSimulator().getSimTime()));
        
        setContext(2);
        // Naplanuj  na cas dany v parametroch simulatora
        getSimulator().scheduleAt(this, getSimulator().getSimTime() + 
                Generator.IRandom( ((SimulatorObchodu) getSimulator()).getParametre().getIntervalObsluhy1()[0], ((SimulatorObchodu) getSimulator()).getParametre().getIntervalObsluhy1()[1]));
    
    }
    
    private void uvolnuje1ObsluhuIdeKDruhej()
    {
        if(_debug)
            System.out.println("Zakaznik: " + _id + "\tUVOLNUJE 1 OBSLUHU \tTime: " + Time.convertTime(getSimulator().getSimTime()));
        try {
            // Uvolnenie Prepazky
            ((SimulatorObchodu)getSimulator()).getObsluha1().leave(1);
        }
        catch (Exception exp)
        {
            System.out.println("Nastala chyba pri 1.obsluhe:" + exp.getMessage());
        }
        
        setContext(3);
        // Zabranie 2 obsluhy
        ((SimulatorObchodu)getSimulator()).getObsluha2().enter(this,1);
    }
    
    private void obsluhaPriDruhejPrepazke()
    {
       if(_debug)
            System.out.println("Zakaznik: " + _id + "\tZABERA 2 OBSLUHU \tTime: " + Time.convertTime(getSimulator().getSimTime())); 
       
       
       setContext(4);
       getSimulator().scheduleAt(this, getSimulator().getSimTime() + 
                Generator.IRandom( ((SimulatorObchodu) getSimulator()).getParametre().getIntervalObsluhy2()[0], ((SimulatorObchodu) getSimulator()).getParametre().getIntervalObsluhy2()[1]));
        
    }
    
    private void uvolnuje2Obsluhu() 
    {
        if(_debug)
            System.out.println("Zakaznik: " + _id + "\tUVOLNUJE 2 OBSLUHU \tTime: " + Time.convertTime(getSimulator().getSimTime())); 
        try {
            ((SimulatorObchodu)getSimulator()).getObsluha2().leave(1);
        } catch (Exception ex) {
            System.out.println("Nastala chyba pri 2.obsluhe:" + ex.getMessage());
        }
        opustaSystem(); 
    }
    
    private void opustaSystem()
    {
        if(_debug)
            System.out.println("Zakaznik: " + _id + "\tOPUSTA SYSTEM \t\tTime: " + Time.convertTime(getSimulator().getSimTime())); 
        
        // Ulozenie casu straveneho v systeme
        ((SimulatorObchodu)getSimulator()).getStatistika().addWaitingTime(getSimulator().getSimTime() - _casPrichodu);
    }
    
    @Override
    public void behavior() {
        switch(getContext())
        {
            case 0:
                prichod();
                break;
            case 1:
                obsluhaPriPrvejPrepazke();
                break;
            case 2:
                uvolnuje1ObsluhuIdeKDruhej();
                break;
            case 3:
                obsluhaPriDruhejPrepazke();
                break;
            case 4:
                uvolnuje2Obsluhu();
                break;
        }
            
    }
    
}
