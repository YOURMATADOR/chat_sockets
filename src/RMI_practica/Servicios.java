package RMI_practica;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Servicios extends Remote {

    String sayHello(String name) throws RemoteException;

    String eco(String c) throws RemoteException;

    boolean palindromo(String c) throws RemoteException;

    String infijo_a_sufijo(String c) throws RemoteException;
}
