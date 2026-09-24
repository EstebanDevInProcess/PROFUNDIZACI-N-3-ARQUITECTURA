package Integracion_con_proveedores;

/**
 * Excepción DE LA APLICACIÓN para fallos de un proveedor logístico.
 * Los adaptadores traducen a ella las excepciones propias de cada API externa,
 * de modo que el cliente no depende de las excepciones de ningún proveedor.
 */
public class ProveedorLogisticoException extends RuntimeException {

    public ProveedorLogisticoException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
