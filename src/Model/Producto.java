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
public class Producto {

    private String id;
    private String precio;
    private String cantidad;
    private String categoria;
    private String tipo;
    private String nombre;
    private String descripcions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcions() {
        return descripcions;
    }

    public void setDescripcions(String descripcions) {
        this.descripcions = descripcions;
    }
    
    

    public static Producto stringToProducto(String value) {
        Producto p = new Producto();
        if (value == null) {
            return null;
        }
        value = value.replace("{", "");
        value = value.replace("}", "");
        value = value.replace("[", "");
        value = value.replace("]", "");
        value = value.replace("\"", "");
        String[] data = value.split(",");
        p.id = data[0].split(":")[1].replace(".0","");
        p.nombre = data[1].split(":")[1];
        p.cantidad = data[2].split(":")[1].replace(".0","");
        p.tipo = data[4].split(":")[1].replace(".0","");
        p.categoria = data[6].split(":")[1].replace(".0","");
        p.precio = data[7].split(":")[1].replace(".0","");
        p.descripcions = data[9].split(":")[1];
        
        return p;
    }
    
    
    public static Producto stringToProducto2(String value) {
        Producto p = new Producto();
        if (value == null) {
            return null;
        }
        value = value.replace("{", "");
        value = value.replace("}", "");
        value = value.replace("[", "");
        value = value.replace("]", "");
        value = value.replace("\"", "");
        String[] data = value.split(",");
        p.id = data[0].split(":")[1].replace(".0","");
        p.nombre = data[1].split(":")[1];
        p.cantidad = data[2].split(":")[1].replace(".0","");
        p.tipo = data[5].split(":")[1].replace(".0","");
        p.categoria = data[7].split(":")[1].replace(".0","");
        p.precio = data[4].split(":")[1].replace(".0","");
        p.descripcions = data[3].split(":")[1];
        
        return p;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", precio=" + precio + ", cantidad=" + cantidad + ", categoria=" + categoria + ", tipo=" + tipo + ", nombre=" + nombre + ", descripcions=" + descripcions + '}';
    }

 
    
}
