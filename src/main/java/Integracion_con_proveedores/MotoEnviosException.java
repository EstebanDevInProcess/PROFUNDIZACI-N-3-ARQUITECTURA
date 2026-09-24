package Integracion_con_proveedores;

/** Excepción CHEQUEADA propia de la librería externa MotoEnvios (no modificable). */
public class MotoEnviosException extends Exception {

    public MotoEnviosException(String mensaje) {
        super(mensaje);
    }
}
