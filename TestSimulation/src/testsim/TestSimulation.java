/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;

import java.util.logging.Level;
import java.util.logging.Logger;
import sim.Generator;

/**
 *
 * @author Willi
 */
public class TestSimulation 
{
    private static final long t_end = 10*60*60; 
    private static final int maxZamestnancov = 13;
    private static final int tellers = 6;
    private static final int[] intervalPrichoduZakaznikov = {8*60, 5*60, 3*60, 2*60, 60};
    private static final int[] intervalPrvejObsluhy = {5*60,6*60};
    private static final int[] intervalDruhejObsluhy = {7*60,9*60}; 
    private static final long zmenaPrevadzky = 2*60*60;
    // nastavenie debugovacich vypisov v poradi Zakaznik, Manazer, GeneratorZakaznikov
    private static final boolean[] debug = {false,false,false};
    
    private static final int pocetReplikaci = 100;
      
            
    public static void main(String[] args) 
    {
        
        try {
            SimulatorObchodu sim = new SimulatorObchodu(0, t_end, maxZamestnancov, tellers, intervalPrichoduZakaznikov , 
                    intervalPrvejObsluhy, intervalDruhejObsluhy, zmenaPrevadzky, debug , pocetReplikaci);
            
            System.out.println("Hlavna Simulacia Banky\n" + sim.toString() + "\n");
            sim.run();
            System.out.println(sim.getStatistika().toString());
            
        } catch (Exception ex) {
            Logger.getLogger(TestSimulation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        

    }
}

