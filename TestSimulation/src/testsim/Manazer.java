/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import sim.Event;
import sim.Simulator;
import sim.Time;



/**
 *
 * @author Willi
 */
public class Manazer extends Event implements Serializable
{
    private static int _pocitadlo = 1;      // Urcuje kedy sa ma vygenerovat novy manazer
    private static int _id = 0;             // Id manazera
    
    private long _koniecSimulacie;          // Ukazuje 
    private long _interval;                 // Interval urcujuci kedy sa ma vygenerovat novy manazer
        
    private boolean _debug;                // Indikuje ci sa maju vypisovat vypisy
        /** 
     * Atributy pre uchovanie statistik a pre vyber najvhodnejsieho poctu 
     * pracovnikov obsluhujucich jednotlive fronty.
     **/
    private long[] _pocetObsluzenychZakaznikov;
    private double[] _sumaAvgTime;
    private long[] _sumaMinTime;
    private long[] _sumaMaxTime;
    private int _pocetReplikaci;
    
    Manazer(Simulator simulator, long koniecSimulacie, long interval)
    {
        super(simulator);
        ++_id;
        _koniecSimulacie = koniecSimulacie;
        _interval = interval; 
        _debug = ((SimulatorObchodu)getSimulator()).getParametre().isDebugManazer();
        _pocetReplikaci = ((SimulatorObchodu)getSimulator()).getParametre().getPocetReplikaci(); 
                
        _pocetObsluzenychZakaznikov = new long[((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov() - 1];
        _sumaAvgTime = new double[((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov() - 1];
        _sumaMinTime = new long[((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov() - 1];
        _sumaMaxTime = new long[((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov() - 1];
    }
    
    protected void spustiManazera() throws Exception
    {
        
        System.out.println("Manazer c: "+ _id +" prisiel v case: " + Time.convertTime(getSimulator().getSimTime()));
        //System.out.println("Ziskal tieto vysledky: ");
        
        if(_debug) 
        {
            System.out.println("Vnorena Simulacia cislo: " + _id + " spustena v case: " + Time.convertTime(getSimulator().getSimTime()));
            System.out.println("Koniec simulacie je naplanovany na cas: " + Time.convertTime(_koniecSimulacie) + " interval je: " + Time.convertTime(_interval));
        }

        // Spusti urcity pocet replikacii a ziska priemerne statistiky podla ktorych sa rozhoduje
        for (int k = 0; k < _pocetReplikaci; k++ ) 
        {
            for (int i = 1; i < ((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov(); i++) 
            {
                SimulatorObchodu sim = (SimulatorObchodu) getSimulator().createCopy();

                sim.getStatistika().eraseStatistics();

                if(_debug) 
                    sim.getStatistika().setName("Vnorena simulacia c: " + _id);

                sim.zmenPocetObsluhy(i);

                if(_debug) 
                    System.out.println(" 1.frontu obsluhuje: " + sim.getParametre().getTellers() + " \t2.Frontu obsluhuje: " + sim.getParametre().getCashiers() + "\n");

                // simulacia len do urciteho casu
                sim.run(_koniecSimulacie - 1);

                if(_debug) 
                    System.out.println(sim.getStatistika().toString()+"\n");
                
                ulozPriemerneStatistiky(sim, i);
             }
        }
        
        System.out.println("Tellers \tCashiers\tAvgTimeSpentInSystem\tNumberOfServedCustomers\tMinTimeSpentInSystem\tMaxTimeSpentInSystem" );
        
        for (int i = 1; i < ((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov(); i++)
        {
            System.out.print( i + "\t\t" + (((SimulatorObchodu)getSimulator()).getParametre().getMaxPocetZamestnancov() - i));
            System.out.print("\t\t" + Time.convertTime((long)_sumaAvgTime[i-1]/_pocetReplikaci));
            System.out.print("\t\t" + _pocetObsluzenychZakaznikov[i-1]/_pocetReplikaci);
            System.out.print("\t\t\t" + Time.convertTime(_sumaMinTime[i-1]/_pocetReplikaci));
            System.out.print("\t\t" + Time.convertTime(_sumaMaxTime[i-1]/_pocetReplikaci));
            
            
//            System.out.print("\tAvgTimeSpentInSystem: "+ Time.convertTime((long)_sumaAvgTime[i-1]/_pocetReplikaci));
//            System.out.print("\tNumberOfServedCustomers: " + _pocetObsluzenychZakaznikov[i-1]/_pocetReplikaci);
//            System.out.print("\tMinTimeSpentInSystem: " + Time.convertTime((long)_sumaMinTime[i-1]/_pocetReplikaci));
//            System.out.print("\tMaxTimeSpentInSystem: " + Time.convertTime((long)_sumaMaxTime[i-1]/_pocetReplikaci));
            System.out.println();
        }
              
        //Scanner scan = new Scanner(System.in); 
        
        //System.out.println("Zadaj cislo");
        //((SimulatorObchodu)getSimulator()).zmenPocetObsluhy(scan.nextInt());
        
        naplanujManazera();
        if(_debug) 
            System.out.println("\n\n\n");
    }
    
    private void ulozPriemerneStatistiky(SimulatorObchodu sim , int i)
    {
        // Ulozenie pre celkove statistiky
        _sumaAvgTime[i-1] += sim.getStatistika().getAvgWait();
        _pocetObsluzenychZakaznikov[i-1] += sim.getStatistika().getCount();
        _sumaMinTime[i-1] += sim.getStatistika().getMinWait();
        _sumaMaxTime[i-1] += sim.getStatistika().getMaxWait();
    }
    
    protected void naplanujManazera()
    {
        // Ak je novy planovany cas manazera rovny konci simulacie, tak sa uz nenaplanuje
        if(_interval*(_pocitadlo++) != getSimulator().getTimeEnd()) {
            getSimulator().scheduleAt(new Manazer(this.getSimulator(), _koniecSimulacie + _interval, _interval), _interval*(_pocitadlo-1));
        }
    }
    
    @Override
    public void behavior() 
    {
        try {
            spustiManazera();
        } catch (Exception ex) {
            Logger.getLogger(Manazer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
