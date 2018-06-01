/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Store;

import Store.Queue;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author Gustavo
 */
public class Serialize {

    public static void serializar(String args[]) {
        Queue theQueue;

        theQueue = new Queue();
        
        for (String object : args) {
            theQueue.put(object);
        }

        System.out.println(theQueue.toString());

        // serialize the Queue
        System.out.println("serializing theQueue");
        try {
            FileOutputStream fout = new FileOutputStream("thequeue.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(theQueue);
            oos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
