/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sim;

import java.io.Serializable;

/**
 *
 * @author Willi
 */
public class Time implements Serializable
{
    public static String convertTime(long time)
    {
       return String.format("%02d:%02d:%02d", time/3600, (time % 3600)/60, (time % 3600)%60);
    }
}
