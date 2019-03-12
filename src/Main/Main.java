/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import actividad2.cliente_2;
import actividad2.servidor_2;

/**
 *
 * @author EDUARDO
 */
public class Main {

    public static void main(String[] args) {
        cliente_2 cliente1 = new cliente_2("Cliente 1", new int[]{2, 2, 1, 5, 2,
            3});
        cliente_2 cliente2 = new cliente_2("Cliente 2", new int[]{1, 3, 5, 1, 1});
        servidor_2 cajera1 = new servidor_2("Cajera 1");
        servidor_2 cajera2 = new servidor_2("Cajera 2");
// Tiempo inicial de referencia
        long initialTime = System.currentTimeMillis();
        cajera1.procesarCompra(cliente1, initialTime);
        cajera2.procesarCompra(cliente2, initialTime);
    }
}
