/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import Store.Serialize;
import Store.Unserialize;
import java.io.File;
import java.io.IOException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

/**
 *
 * @author Gustavo
 */
public class main {

    public static void main(String[] args) throws IOException{
        String data = Unserialize.get();
        if (data.length() != 0) {
            new Home().setVisible(true);  
        } else{
            new Login().setVisible(true);
        }
    }

}
