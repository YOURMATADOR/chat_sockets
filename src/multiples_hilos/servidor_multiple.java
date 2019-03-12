/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package multiples_hilos;

/**
 *
 * @author EDUARDO
 */
public class servidor_multiple extends Thread {

    private String nombre;
    private cliente_multiple cliente;
    private long initialTime;
// Constructor, getter & setter

    public servidor_multiple(String nombre, cliente_multiple cliente, long initial) {
        this.nombre = nombre;
        this.cliente = cliente;
        this.initialTime = initial;
    }

    @Override
    public void run() {
        System.out.println("La cajera " + this.getNombre() + " COMIENZA A PROCESARLA COMPRA DEL CLIENTE " + this.getCliente().getNombre() + " EN ELTIEMPO: " + (System.currentTimeMillis() - this.getInitialTime()) / 1000 + "seg");

        for (int i = 0; i < this.getCliente().getCarroCompra().length; i++) {

            this.esperarXsegundos(getCliente().getCarroCompra()[i]);

            System.out.println("Procesado el producto " + (i + 1)
                    + " del cliente " + this.getCliente().getNombre() + "->Tiempo: "
                    + (System.currentTimeMillis() - this.getInitialTime()) / 1000 + "seg");

        }

        System.out.println("La cajera " + this.getNombre() + " HA TERMINADO DE PROCESAR "
                + this.getCliente().getNombre() + " EN EL TIEMPO: " + (System.currentTimeMillis()
                - this.getInitialTime()) / 1000 + "seg");
    }

    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the cliente
     */
    public cliente_multiple getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(cliente_multiple cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the initialTime
     */
    public long getInitialTime() {
        return initialTime;
    }

    /**
     * @param initialTime the initialTime to set
     */
    public void setInitialTime(long initialTime) {
        this.initialTime = initialTime;
    }

}
