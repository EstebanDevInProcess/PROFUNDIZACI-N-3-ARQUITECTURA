/**
 * Patrón Factory Method — rol PRODUCTO.
 *
 * Contrato común de todos los comprobantes. El coordinador y los creadores
 * dependen de esta abstracción y no de los formatos concretos (DIP).
 */
public interface Comprobante {

    /**
     * Genera el comprobante con el contenido recibido.
     *
     * @param contenido datos de la compra (puede ser vacío o null; se respeta
     *                  el comportamiento original)
     */
    void generar(String contenido);
}
