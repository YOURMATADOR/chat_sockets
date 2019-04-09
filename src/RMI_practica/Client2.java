package RMI_practica;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client2 {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        String c1, c2;
        Scanner ent = new Scanner(System.in);
        Registry registry = LocateRegistry.getRegistry();
        Servicios testRemote = (Servicios) registry.lookup("Test");
        System.out.println("Nombre a saludar?");
        c1 = ent.nextLine();
        System.out.println(testRemote.sayHello(c1));
        System.out.println("cadena a convertir?");
        c2 = ent.nextLine();
        System.out.println(testRemote.eco(c2));
    }

}
