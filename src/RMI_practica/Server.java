package RMI_practica;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Stack;

public class Server {

    public static int pref(String op) {
        int prf = 99;
        if (op.equals("^")) {
            prf = 5;
        }
        if (op.equals("*") || op.equals("/")) {
            prf = 4;
        }
        if (op.equals("+") || op.equals("-")) {
            prf = 3;
        }
        if (op.equals(")")) {
            prf = 2;
        }
        if (op.equals("(")) {
            prf = 1;
        }
        return prf;
    }

    private static String depurar(String s) {
        s = s.replaceAll("\\s+", ""); //Elimina espacios en blanco
        s = "(" + s + ")";
        String simbologia = "+-*/()";
        String str = "";

        //Deja espacios entre operadores
        for (int i = 0; i < s.length(); i++) {
            if (simbologia.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else {
                str += s.charAt(i);
            }
        }
        return str.replaceAll("\\s+", " ").trim();
    }

    public static void main(String[] args) throws RemoteException, AlreadyBoundException {

        Remote stub = UnicastRemoteObject.exportObject(new Servicios() {
            @Override
            public String sayHello(String name) throws RemoteException {
                return "Hello, " + name;
            }

            public String eco(String c) throws RemoteException {
                return c.toUpperCase();
            }

            public boolean palindromo(String c) throws RemoteException {
                int inc = 0;
                int des = c.length() - 1;
                boolean bError = false;
                while ((inc < des) && (!bError)) {

                    if (c.charAt(inc) == c.charAt(des)) {
                        inc++;
                        des--;
                    } else {
                        bError = true;
                    }
                }
                if (!bError) {
                    return true;
                }

                return false;
            }

            public String infijo_a_sufijo(String c) throws RemoteException {
                String depurado = depurar(c);
                String[] arrayInfix = depurado.split(" ");

                //Declaración de las pilas
                Stack< String> Entrada = new Stack< String>(); //Pila entrada
                Stack< String> Pila = new Stack< String>(); //Pila temporal para operadores
                Stack< String> Salida = new Stack< String>(); //Pila salida
                for (int i = arrayInfix.length - 1; i >= 0; i--) { // *registra la cadena de manera inversa para no alterar el orden de la cadena
                    Entrada.push(arrayInfix[i]);
                }
                while (!Entrada.isEmpty()) {
                    switch (pref(Entrada.peek())) {
                        case 1: // *similar al el caso 2 so el valor es ( se ignora la entrada 
                            Pila.push(Entrada.pop());
                            break;
                        case 2: //* el valor de la pila es un ) 
                            while (!Pila.peek().equals("(")) { //* en caso de ( se ignrona la entrada o el simbolo por eso se pasa un valor de la pila cuando esta esta "vacia"
                                Salida.push(Pila.pop());
                            }
                            Pila.pop();
                            Entrada.pop();
                            break;
                        case 3:// * caso 3 y 4 hacen lo mismo
                        case 4:
                            while (pref(Pila.peek()) >= pref(Entrada.peek())) { // * si la prioridad del simbolo de la pila es mayor que algun otro simbolo de la entrada, se agrega el simbolo de la pila
                                Salida.push(Pila.pop());
                            }
                            Pila.push(Entrada.pop()); // * agrega el simbolo con menor prioridad de la entrada y lo agrega a la pila para la siguiente ronda
                            break;

                        default:
                            Salida.push(Entrada.pop()); // * si no es ningun caso anterior, se trata de un numero y se agrega a la salida directamente
                    }
                }

                String postfix = Salida.toString().replaceAll("[\\]\\[,]", ""); // * expresion regular que elimina los \ y las , "comas"

                return postfix;

            }

        }, 0);

        Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);

        registry.bind(
                "Test", stub);

    }

}
