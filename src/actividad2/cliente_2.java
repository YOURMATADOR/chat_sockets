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
public class cliente_2 {

    private String nombre;
    private int[] carroCompra;
// Constructor, getter y setter

    public cliente_2(String miNombre, int[] carro) {
        this.nombre = miNombre;
        this.carroCompra = carro;
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
     * @return the carroCompra
     */
    public int[] getCarroCompra() {
        return carroCompra;
    }

    /**
     * @param carroCompra the carroCompra to set
     */
    public void setCarroCompra(int[] carroCompra) {
        this.carroCompra = carroCompra;
    }
}
