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
public class TipoProducto {

    private int id;
    private String nombre;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoProducto{" + "id=" + id + ", nombre=" + nombre + '}';
    }

    public TipoProducto stringToTipoProducto(String value) {
        try {
            TipoProducto tp = new TipoProducto();
            if (value == null) {
                return null;
            }
            value = value.replace("{", "");
            value = value.replace("}", "");
            value = value.replace("[", "");
            value = value.replace("]", "");
            value = value.replace("\"", "");
            String[] data = value.split(",");
            tp.id = Integer.parseInt(data[0].split(":")[1].replace(".0",""));
            tp.nombre = data[1].split(":")[1];

            return tp;
        } catch (Exception ex) {
            return null;
        }
    }
}
