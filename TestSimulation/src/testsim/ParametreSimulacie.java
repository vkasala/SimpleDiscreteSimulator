/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;

import java.io.Serializable;

/**
 *
 * @author Willi
 */
public class ParametreSimulacie implements Serializable
{
    private int _maxPocetZamestnancov;                 // maximalny pocet predavacov
    private int _intervalPrichoduZakaznikov[];         // intervali prichodu zakaznikov - slaba, silna, stredna
    private int _intervalObsluhy1[];                   // interval trvania 1. obsluhy
    private int _intervalObsluhy2[];                   // interval trvania 2. obsluhy
    private boolean _debugManazer;
    private boolean _debugZakaznik;
    private boolean _debugGenerator;
    private int _tellers;                              // pocet zamestnancov obsluhujuci 1 frontu
    private int _cashiers;                             // pocet zamestnancov obsluhujuci 2 frontu
    
    private long _zmenaPrichodu;
    private int _pocetReplikaci;
    
    public ParametreSimulacie(int pocetPredavacov, int intervalZakaznikov[], int intervalPrvejObsluhy[], int intervalDruhejObsluhy[], 
            long zmenaPrichodu, boolean debug[], int pocetReplikaci) 
    {
        this._maxPocetZamestnancov = pocetPredavacov;
        this._intervalPrichoduZakaznikov = new int[intervalZakaznikov.length];
        this._intervalObsluhy1 = new int[intervalPrvejObsluhy.length];
        this._intervalObsluhy2 = new int[intervalDruhejObsluhy.length];
        
        System.arraycopy(intervalZakaznikov, 0, _intervalPrichoduZakaznikov, 0, _intervalPrichoduZakaznikov.length);
        System.arraycopy(intervalPrvejObsluhy, 0, _intervalObsluhy1, 0, _intervalObsluhy1.length);        
        System.arraycopy(intervalDruhejObsluhy, 0, _intervalObsluhy2, 0, _intervalObsluhy2.length);
        
       this._zmenaPrichodu = zmenaPrichodu;
        _debugZakaznik = debug[0];
        _debugManazer = debug[1];
        _debugGenerator = debug[2];
        
        _pocetReplikaci = pocetReplikaci;
    }
    
    public void nastavObsluhy(int obsluha1)
    {
        setTellers(obsluha1);
        setCashiers(getMaxPocetZamestnancov() - getTellers());
    }

    /**
     * @return the _maxPocetZamestnancov
     */
    public int getMaxPocetZamestnancov() {
        return _maxPocetZamestnancov;
    }

    /**
     * @return the _intervalPrichoduZakaznikov
     */
    public int[] getIntervalPrichoduZakaznikov() {
        return _intervalPrichoduZakaznikov;
    }

    /**
     * @return the _intervalObsluhy1
     */
    public int[] getIntervalObsluhy1() {
        return _intervalObsluhy1;
    }

    /**
     * @return the _intervalObsluhy2
     */
    public int[] getIntervalObsluhy2() {
        return _intervalObsluhy2;
    }

    /**
     * @return the _tellers
     */
    public int getTellers() {
        return _tellers;
    }

    /**
     * @param tellers the _tellers to set
     */
    public void setTellers(int tellers) {
        this._tellers = tellers;
    }

    /**
     * @return the _cashiers
     */
    public int getCashiers() {
        return _cashiers;
    }

    /**
     * @param cashiers the _cashiers to set
     */
    public void setCashiers(int cashiers) {
        this._cashiers = cashiers;
    }

    /**
     * @return the zmenaPrichodu
     */
    public long getZmenaPrichodu() {
        return _zmenaPrichodu;
    }

    /**
     * @return the _debugManazer
     */
    public boolean isDebugManazer() {
        return _debugManazer;
    }

    /**
     * @return the _debugZakaznik
     */
    public boolean isDebugZakaznik() {
        return _debugZakaznik;
    }

    /**
     * @return the _debugGenerator
     */
    public boolean isDebugGenerator() {
        return _debugGenerator;
    }

    /**
     * @return the _pocetReplikaci
     */
    public int getPocetReplikaci() {
        return _pocetReplikaci;
    }
}
