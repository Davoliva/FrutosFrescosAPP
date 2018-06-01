/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Gustavo
 */
public class Comuna {

    private String id;
    private String nombre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static Comuna stringToProducto(String value) {
        Comuna c = new Comuna();
        if (value == null) {
            return null;
        }

        value = value.replace("{", "");
        value = value.replace("}", "");
        value = value.replace("[", "");
        value = value.replace("]", "");
        value = value.replace("\"", "");
        String[] data = value.split(",");
        c.id = data[0].split(":")[1].replace(".0", "");
        c.nombre = data[1].split(":")[1];

        return c;
    }
}
