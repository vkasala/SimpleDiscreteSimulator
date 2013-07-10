/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testsim;


import java.io.Serializable;
import sim.Statistics;
import sim.Time;

/**
 *
 * @author Willi
 */
public class StatistikySimulacie extends Statistics implements Serializable
{
    public StatistikySimulacie(String name) {
        super(name);
    }
  
    @Override
    public String toString() {
        return "Statistiky " + super.getName() + "\n MinTime: \t" + Time.convertTime(super.getMinWait()) + "\n MaxTime: \t" + Time.convertTime(super.getMaxWait()) + "\n AvgTime: \t" + Time.convertTime((long)super.getAvgWait()) + "\nPocet obsluzenych zakaznikov: \t" + super.getCount();
    }
}
