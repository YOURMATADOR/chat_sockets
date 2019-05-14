package relojes;

import sistemas_distribuidos.*;
import java.util.*;
import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;

public class Cliente {

    private final int PUERTO = 1234; //Puerto para la conexi�n
    private final String HOST = "localhost"; //Host para la conexi�n
    private String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor
    cl_interfaz interfaz;
    Long hora_actual;

    public String getMensajeServidor() {
        return mensajeServidor;
    }

    public void setMensajeServidor(String mensajeServidor) {
        this.mensajeServidor = mensajeServidor;
    }

    public void set_interfaz(cl_interfaz cl) {
        this.interfaz = cl;
    }

    public Socket getCs() {
        return cs;
    }

    public void setCs(Socket cs) {
        this.cs = cs;
    }

    public BufferedWriter getSalidaServidor() {
        return salidaServidor;
    }

    public void setSalidaServidor(BufferedWriter salidaServidor) {
        this.salidaServidor = salidaServidor;
    }

    public DataOutputStream getdOut() {
        return dOut;
    }

    public void setdOut(DataOutputStream dOut) {
        this.dOut = dOut;
    }
    private Socket cs; //Socket del cliente
    private BufferedWriter salidaServidor; //Flujo de datos de salida
    DataOutputStream dOut;

    public int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }

    public Cliente() throws IOException {
        cs = new Socket(HOST, PUERTO); //Socket para el cliente en localhost en puerto 1234
        System.out.println("Iniciando cliente\n");
        dOut = new DataOutputStream(cs.getOutputStream());

        this.hora_actual = (System.currentTimeMillis() + createRandomIntBetween(-1000 * -600000 * -100000, 1000 * 100000 * 600000));

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                cambiarHora();
            }
        }, 0, 1000);
    }

    public void cambiarHora() {
        this.hora_actual += 1000;
    }

    public void setConversasion(String conversasion) {
        System.out.println(conversasion);
        this.interfaz.actualiza_conversasion(conversasion);

    }

    public void enviar_mensaje(String mensaje) throws IOException {

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

    public void actualizar_hora(String hora) {
        String horaSplit = hora.split(">")[2];
        this.hora_actual = Long.parseLong(horaSplit);
    }

    public void startClient() { //M�todo para iniciar el cliente
        String message;

        while (true) {
            //If there are new messages
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader((cs.getInputStream())));
                if ((message = br.readLine()) != null) {
                    //Show them
                    String mensaje = message.split(">").length > 1 ? message.split(">")[1] : "";
                    System.out.println(mensaje);

                    switch (mensaje) {
                        case "SINCRONIZAR":
                            this.setConversasion("Servidor: " + "SINCRONIZANDO");
                            this.enviar_mensaje("MIHORA," + this.hora_actual);
                            break;
                        case "ESTABLECER":
                            this.setConversasion("Servidor: " + message);
                            this.actualizar_hora(message);
                            break;
                        default:
                            this.setConversasion("Servidor: " + message);
                            break;
                    }
                    System.out.println(message);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}
