package relojes;

import java.awt.List;
import sistemas_distribuidos.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class Servidor {

    private final int PUERTO = 1234; //Puerto para la conexi�n
    private final String HOST = "localhost"; //Host para la conexi�n
    static Vector<Client_despachador> clientes = new Vector<Client_despachador>();
    ArrayList<Long> horas = new ArrayList<Long>();
    Long promedio;
    int contador = 0;
    String mensajeServidor; //Mensajes entrantes (recibidos) en el servidor    
    sv_interfaz interfaz = null;
    Long suma;
    String menu = "* SINCRONIZAR :Se recolectan las horas de todos los clientes conectados\n* ESTABLECER: Se envia y establece a cada uno de los clientes un promedio de las horas obtenidas con el comando SINCRONIZAR\n* CLEAR reincia los valores de horas recolectadas en sincronizaciones anteriores\n* REINICIAR: Se establece una hora aleratoria para cada uno de los clientes\n* HELP: Muestra todos los comandos disponibles, con una pequeña descripcion";

    public void setConversasion(String conversasion) {
        System.out.println(conversasion);
        this.interfaz.actualiza_conversasion(conversasion);

    }

    private ServerSocket ss; //Socket del servidor
    private Socket cs; //Socket del cliente

    public void set_interfaz(sv_interfaz inter) {
        this.interfaz = inter;
    }

    public void agregarHora(int numero, String hora) {
        System.out.println(numero + hora);
        this.horas.add(Long.parseLong(hora));

    }

    public void reiniciarContadores() {
        this.promedio = new Long(0);
        this.suma = new Long(0);
        this.horas.clear();
    }

    public void promediarHora() {
        System.out.println("Promedio" + this.horas);
        for (int index = 0; index < this.horas.size(); index++) {
            this.suma = this.suma + new Long(this.horas.get(index));
            System.out.println("suma" + this.suma);

        }
        this.promedio = new Long(this.suma) / new Long(this.horas.size());
    }

    public void enviar_mensaje(String mensaje) throws IOException {
        BufferedWriter salidaServidor; //Flujo de datos de salida

        OutputStream out;
        System.out.println("MENSAEJ" + mensaje);

        try {
            switch (mensaje + "") {
                case ">ESTABLECER":
                    System.out.println("Establecer");
                    //loop through it
                    this.promediarHora();
                    System.out.println("PROMEDIO" + this.promedio);

                    for (int index = 0; index < clientes.size(); index++) {
                        out = clientes.get(index).s.getOutputStream();
                        //Flujo de datos hacia el servidor 
                        salidaServidor = new BufferedWriter(new OutputStreamWriter(out));
                        salidaServidor.write(">ESTABLECER>" + this.promedio + "");
                        salidaServidor.newLine();
                        salidaServidor.flush();
                    }
                    break;
                case ">CLEAR":
                    System.out.println("help");
                    this.reiniciarContadores();
                    this.setConversasion("valores restaurados");
                    break;
                case ">HELP":
                    System.out.println("help");
                    //loop through it
                    this.setConversasion(this.menu);

                    break;

                default:
                    for (int index = 0; index < clientes.size(); index++) {
                        out = clientes.get(index).s.getOutputStream();
                        //Flujo de datos hacia el servidor 
                        salidaServidor = new BufferedWriter(new OutputStreamWriter(out));
                        salidaServidor.write(mensaje);
                        salidaServidor.newLine();
                        salidaServidor.flush();
                    }
                    break;
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public Servidor() throws IOException {
        ss = new ServerSocket(PUERTO);//Se crea el socket para el servidor en puerto 1234        
        System.out.println("Iniciando servidor\n");
        this.suma = new Long(0);
        this.promedio = new Long(0);
    }

    public void startServer() throws IOException {   //M�todo para iniciar el servidor   

        try {
            while (true) {
                cs = ss.accept(); //Socket para el cliente
                System.out.println("Esperando..."); //Esperando conexi�n
                System.out.println("Cliente en l�nea");
                this.setConversasion("Cliente conectado!");
                //Se obtiene el flujo entrante desde el cliente
                BufferedReader entrada = new BufferedReader(new InputStreamReader(cs.getInputStream()));
                BufferedWriter salida = new BufferedWriter(new OutputStreamWriter(cs.getOutputStream()));
                String nombre = "Cliente #" + contador + " ";
                Client_despachador t = new Client_despachador(cs, entrada, salida, this, nombre, contador);
                contador++;
                t.start(); //* inicia un nuevo hilo por cada cliente que ingresa 
                clientes.add(t);
            }

        } catch (Exception e) {
            cs.close();//Se finaliza la conexi�n con el cliente
            System.out.println(e.getMessage());
            System.out.println("Fin de la conexi�n");

        }
    }

}
