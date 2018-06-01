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
public class Usuario {
    private String rut; 
    private String email; 
    private String nombre;

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    

    public static Usuario StringToClass(String value){
        Usuario us = new Usuario();
        if (value == null) {
            return null;
        }
        
        value = value.replace("{", "");
        value = value.replace("}", "");
        value = value.replace("[", "");
        value = value.replace("]", "");
        String[] data = value.split(",");
        
        us.setRut(data[0].split(":")[1].replace("\"", ""));
        us.setEmail(data[2].split(":")[1].replace("\"", ""));
        us.setNombre(data[1].split(":")[1].replace("\"", ""));
        return  us; 
    }

    @Override
    public String toString() {
        return "Usuario{" + "rut=" + rut + ", email=" + email + ", nombre=" + nombre + '}';
    }
    
    
}
