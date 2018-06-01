/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Store;

/**
 *
 * @author Gustavo
 */
import java.util.Vector;
import java.io.*;

public class Queue extends Vector {

    /*
  ** FIFO, first in first out
     */
    Queue() {
        super();
    }

    void put(Object o) {
        addElement(o);
    }

    Object get() {
        if (isEmpty()) {
            return null;
        }
        Object o = firstElement();
        removeElement(o);
        return o;
    }

    Object peek() {
        if (isEmpty()) {
            return null;
        }
        return firstElement();
    }
}
