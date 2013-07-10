/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;

import sim.*;

/**
 *
 * @author Willi
 */
public class SimulatorObchodu extends Simulator
{
    private Store _obsluha1;
    private Store _obsluha2;
    private StatistikySimulacie _statistikaSimulacie;
    private ParametreSimulacie _parametre;
    
    public SimulatorObchodu(long t_start, long t_end, int zamestnanci , 
            int tellers, int intervalZakaznikov[], int intervalPrvejObsluhy[],int intervalDruhejObsluhy[], 
            long zmenaPrichodu, boolean []debug, int pocetReplikaci) throws Exception 
    
    {
        super(t_start, t_end);
               
        _statistikaSimulacie = new StatistikySimulacie("Hlavna Simulacia");
        _parametre = new ParametreSimulacie(zamestnanci, intervalZakaznikov, intervalPrvejObsluhy, intervalDruhejObsluhy, zmenaPrichodu, debug, pocetReplikaci);
        _parametre.nastavObsluhy(tellers);
                
        _obsluha1 = new Store("Obsluha 1", this, _parametre.getMaxPocetZamestnancov() - 1);
        _obsluha2 = new Store("Obsluha 2", this, _parametre.getMaxPocetZamestnancov() - 1);
        
        _obsluha1.setCapacity(_parametre.getTellers());
        _obsluha2.setCapacity(_parametre.getCashiers());
        
        for (int i = 0; i < intervalZakaznikov.length; i++) 
        {
           this.scheduleAt(new GeneratorZakaznikov(this, _parametre.getIntervalPrichoduZakaznikov()[i], zmenaPrichodu*(i+1)), zmenaPrichodu*i); 
        }
        
//        this.scheduleAt(new GeneratorZakaznikov(this, _parametre.getIntervalPrichoduZakaznikov()[0], zmenaPrichodu));
//        this.scheduleAt(new GeneratorZakaznikov(this, _parametre.getIntervalPrichoduZakaznikov()[1], zmenaPrichodu*2),zmenaPrichodu);
//        this.scheduleAt(new GeneratorZakaznikov(this, _parametre.getIntervalPrichoduZakaznikov()[2], zmenaPrichodu*3),zmenaPrichodu*2);
        
        //TODO Generovanie Manazera
        this.scheduleAt(new Manazer(this, zmenaPrichodu, zmenaPrichodu));       
    }
    
    public Store getObsluha1()
    {
        return _obsluha1;
    }

    public void zmenPocetObsluhy(int tellers) throws Exception
    {
        this._parametre.nastavObsluhy(tellers);
        
        _obsluha1.setCapacity(_parametre.getTellers()); 
        _obsluha2.setCapacity(_parametre.getCashiers());
    }

    /**
     * @return the _parametre
     */
    public ParametreSimulacie getParametre() {
        return _parametre;
    }

    /**
     * @return the _statistikaSimulacie
     */
    public StatistikySimulacie getStatistika() {
        return _statistikaSimulacie;
    }

    /**
     * @return the _obsluha2
     */
    public Store getObsluha2() {
        return _obsluha2;
    }
}
