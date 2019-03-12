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
public class Main_multiple {

    public static void main(String[] args) {
        cliente_multiple cliente1 = new cliente_multiple("Cliente 1", new int[]{2, 2, 1, 5, 2,
            3});
        cliente_multiple cliente2 = new cliente_multiple("Cliente 2", new int[]{1, 3, 5, 1, 1
        });

// Tiempo inicial de referencia
        long initialTime = System.currentTimeMillis();
        servidor_multiple cajera1 = new servidor_multiple("Cajera 1", cliente1, initialTime);
        servidor_multiple cajera2 = new servidor_multiple("Cajera 2", cliente2, initialTime);
        cajera1.start();
        cajera2.start();
    }
}
