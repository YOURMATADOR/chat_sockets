/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relojes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tumatador
 */
public class Client_despachador extends Thread implements Runnable {

    final BufferedReader entrada;
    final BufferedWriter salida;
    final Socket s;
    final Servidor servidor;
    final String nombre;
    final int numero;

    public Client_despachador(Socket s, BufferedReader entrada, BufferedWriter salida, Servidor servidor, String nombre, int numero) throws IOException {
        this.entrada = entrada;
        this.salida = salida;
        this.s = s;
        this.servidor = servidor;
        this.nombre = nombre;
        this.numero = numero;
    }

    public void run() {
        try {

            while ((servidor.mensajeServidor = entrada.readLine()) != null) { //Mientras haya mensajes desde el cliente
                //Se muestra por pantalla el mensaje recibido
                String mensaje = servidor.mensajeServidor + "";

                switch (mensaje.split(",")[0]) {
                    case "MIHORA":
                        servidor.setConversasion(nombre + " La hora es " + mensaje.split(",")[1]);
                        servidor.agregarHora(this.numero, mensaje.split(",")[1]);
                        break;
                    default:
                        servidor.setConversasion(nombre + servidor.mensajeServidor);
                        break;
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Client_despachador.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            this.entrada.close();
            this.salida.close();

        } catch (IOException ex) {
            Logger.getLogger(Client_despachador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
