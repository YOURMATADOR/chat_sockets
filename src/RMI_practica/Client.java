package RMI_practica;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry();
        Servicios testRemote = (Servicios) registry.lookup("Test");
        System.out.println(testRemote.sayHello("A todos"));
        System.out.println(testRemote.eco("saludos"));
        String palabra = "hola";
        String palabra_infija = "(((5+9)*2)+(6*5))";
        if (testRemote.palindromo(palabra)) {
            System.out.println("la palabra " + palabra + " es palindromo");

        } else {
            System.out.println(palabra + " NO es un palindromo, intenta con otra palabra!");

        }

        System.err.println("Palabra infija: " + palabra_infija);
        System.out.println("Palabra postfija/sufija : " + testRemote.infijo_a_sufijo(palabra_infija));

    }

}
