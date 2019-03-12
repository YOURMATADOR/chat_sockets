/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package actividad2;

/**
 *
 * @author EDUARDO
 */
public class servidor_2 {

    private String nombre;
// Constructor, getter y setter

    public servidor_2(String nombre) {
        this.nombre = nombre;
    }

    public void procesarCompra(cliente_2 cliente, long timeStamp) {
        System.out.println("La cajera " + this.nombre
                + " COMIENZA A PROCESAR LA COMPRA DEL CLIENTE "
                + cliente.getNombre()
                + " EN EL TIEMPO: "
                + (System.currentTimeMillis() - timeStamp) / 1000
                + "seg");

        for (int i = 0; i < cliente.getCarroCompra().length; i++) {
            this.esperarXsegundos(cliente.getCarroCompra()[i]);

            System.out.println("Procesado el producto "
                    + (i + 1)
                    + " ->Tiempo: " + (System.currentTimeMillis()
                    - timeStamp) / 1000
                    + "seg");

        }
        System.out.println("La cajera " + this.nombre + " HA TERMINADODE PROCESAR " + cliente.getNombre() + " EN EL TIEMPO: " + (System.currentTimeMillis() - timeStamp) / 1000 + "seg");
    }

    private void esperarXsegundos(int segundos) {
        try {
            Thread.sleep(segundos * 1000);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}
