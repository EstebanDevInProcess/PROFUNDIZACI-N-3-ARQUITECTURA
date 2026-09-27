/**
 * Patrón Factory Method — rol PRODUCTO CONCRETO (formato XML).
 * Nuevo requisito: se agrega sin modificar GeneradorComprobante.
 */
public class ComprobanteXML implements Comprobante {

    @Override
    public void generar(String contenido) {
        System.out.println(
            "Generando comprobante XML: " + contenido
        );
    }
}
