package sistemas_distribuidos;

import java.io.*;
import java.net.*;

public class Servidor {

    private final int PUERTO = 1234; //Puerto para la conexi�n
    private final String HOST = "10.226.162.219"; //Host para la conexi�n
    private String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor    
    sv_interfaz interfaz = null;

    public void setConversasion(String conversasion) {
        System.out.println(conversasion);
        this.interfaz.actualiza_conversasion(conversasion);

    }

    private ServerSocket ss; //Socket del servidor
    private Socket cs; //Socket del cliente

    public void set_interfaz(sv_interfaz inter) {
        this.interfaz = inter;
    }

    public void enviar_mensaje(String mensaje) throws IOException {
        BufferedWriter salidaServidor; //Flujo de datos de salida

        OutputStream out;

        try {
            out = cs.getOutputStream();
            //Flujo de datos hacia el servidor 
            salidaServidor = new BufferedWriter(new OutputStreamWriter(out));
            salidaServidor.write(mensaje);
            salidaServidor.newLine();
            salidaServidor.flush();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Servidor() throws IOException {
        ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234
        cs = new Socket(); //Socket para el cliente

        System.out.println("Iniciando servidor\n");

    }

  

    public void startServer() {   //M�todo para iniciar el servidor   
        try {
            System.out.println("Esperando..."); //Esperando conexi�n

            cs = ss.accept(); //Accept comienza el socket y espera una conexi�n desde un cliente
            System.out.println("Cliente en l�nea");
        this.setConversasion("Cliente conectado!");
            //Se obtiene el flujo entrante desde el cliente
            BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));

            while ((mensajeServidor = entrada.readLine()) != null) { //Mientras haya mensajes desde el cliente            
                //Se muestra por pantalla el mensaje recibido
                this.setConversasion("Cliente: " + mensajeServidor);
                System.out.println(mensajeServidor);
            }

            System.out.println("Fin de la conexi�n");

            ss.close();//Se finaliza la conexi�n con el cliente
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
