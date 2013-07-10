/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.io.Serializable;
import java.util.Random;

/**
 *
 * @author Willi
 */
public class Generator implements Serializable
{
    private static Random random = new Random();
    
    public static long exponential(double mean) 
    {
        // Vzorec prebraty zo skript
        return (long) Math.round(-mean*Math.log(1 - random.nextDouble()));
    }
    /**
     * Metoda generuje cislo medzi zadanymi hodnotami.
     * @param value_from urcuje zaciatok intervalu, od ktoreho ma byt generovane cislo
     * @param value_to urcuje zaciatok intervalu, do ktoreho ma byt generovane cislo
     * @return vracia hodnotu medzi zadanym intervalom
     */
    public static long IRandom(long value_from, long value_to)
    {
        return (long) Math.round((value_to-value_from)*random.nextDouble()+value_from);
    }
}
