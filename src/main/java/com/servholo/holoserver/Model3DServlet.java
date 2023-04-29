/* SERVLET CHE RESTITUISCE IL MODELLO 3D (fil .obj) RICHIESTO DAL CLIENT (l'Hololens 2)*/
package com.servholo.holoserver;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;

@WebServlet(name = "Model3DServlet", value = "/Model3DServlet")
public class Model3DServlet extends HttpServlet {

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("\n\nFINESSSSSSS\n\n\n\n\n"); // Questo lo stampa

        File model3d = new File("ModelsOBJ/cerabarM_PMC51.obj"); // --> file System del Server (path relativo) *****Impossibile trovare il percorso specificato

        response.setContentType("application/obj"); //la risposta è un file .obj (modello 3D richiesto dall'Hololens)
        response.setContentLength((int) model3d.length());

        // Imposto i parametri della risposta HTTP
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", model3d.getName());
        response.setHeader(headerKey, headerValue);

        // Creo lo stream di output per inviare il file al client
        OutputStream outStream = response.getOutputStream();
        FileInputStream inStream = new FileInputStream(model3d); //legge i dati di model3d in flussi di byte

        // [LETTURA FILE E INVIO AL CLIENT]:
        byte[] buffer = new byte[4096]; // dimensione comune e conveniente
        int bytesRead = -1;


        /*
        * All'interno del ciclo, viene utilizzato il metodo outputStream.write(byteArray, 0, bytesRead)
          per scrivere bytesRead byte dall'array byteArray al flusso di output della risposta HTTP.
        * L'argomento 0 indica l'offset di inizio dell'array, mentre bytesRead indica il numero effettivo di byte letti dal file in ingresso.
        * Il ciclo continua finché il metodo inputStream.read(byteArray) restituisce un valore maggiore di zero, ovvero finché ci sono ancora
          byte da leggere dal file in ingresso.
        * */
        while ((bytesRead = inStream.read(buffer)) != -1) { //finisce quando non c'è più nulla da leggere
            outStream.write(buffer, 0, bytesRead);
        }


        inStream.close();
        outStream.flush();// il metodo outputStream.flush() per assicurarsi che tutti i byte siano stati scritti nel flusso di output della risposta HTTP.
        outStream.close(); //chiudiamo il flusso di output della risposta.

        System.out.println("\n\nFINESSSSSSS\n\n\n\n\n"); //Qui non ci arriva =(
    }

}
