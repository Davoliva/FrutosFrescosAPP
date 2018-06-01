/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Store;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *
 * @author Gustavo
 */
public class Unserialize {

    public static String get() {
        Queue theQueue;

        theQueue = new Queue();

        try {
            FileInputStream fin = new FileInputStream("thequeue.dat");
            ObjectInputStream ois = new ObjectInputStream(fin);
            theQueue = (Queue) ois.readObject();
            ois.close();
        } catch (Exception e) {
            return "";
        }

        return theQueue.toString();
    }
}
