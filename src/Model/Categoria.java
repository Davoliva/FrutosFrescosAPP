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
public class Categoria {

    private int id;
    private String nombre;
    private int tipoProducto;

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

    public int getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(int tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    @Override
    public String toString() {
        return "Categoria{" + "id=" + id + ", nombre=" + nombre + ", tipoProducto=" + tipoProducto + '}';
    }

    public Categoria stringToCategoria(String value) {
        try {
            Categoria c = new Categoria();
            if (value == null) {
                return null;
            }
            value = value.replace("{", "");
            value = value.replace("}", "");
            value = value.replace("[", "");
            value = value.replace("]", "");
            value = value.replace("\"", "");
            String[] data = value.split(",");
            c.id = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(data[0].split(":")[1]))));
            c.nombre = data[1].split(":")[1];
            c.tipoProducto = Integer.parseInt(String.valueOf(Math.round(Double.parseDouble(data[2].split(":")[1]))));
            return c;
        } catch (Exception ex) {
            return null;
        }
    }
}
