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
public class Productor {
    private String rut;
    private String nombre;
    private String direccion;
    private String comuna;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public static Productor stringToProducto(String value) {
        Productor p = new Productor();
        if (value == null) {
            return null;
        }
        
        value = value.replace("{", "");
        value = value.replace("}", "");
        value = value.replace("[", "");
        value = value.replace("]", "");
        value = value.replace("\"", "");
        String[] data = value.split(",");
        p.rut = data[0].split(":")[1];
        p.nombre = data[1].split(":")[1];
        p.comuna = data[3].split(":")[1].replace(".0", "");
        p.direccion = data[5].split(":")[1];

        return p;
    }
}
