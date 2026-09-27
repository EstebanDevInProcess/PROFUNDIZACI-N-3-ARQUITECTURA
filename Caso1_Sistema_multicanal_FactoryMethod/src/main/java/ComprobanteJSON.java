/**
 * Patrón Factory Method — rol PRODUCTO CONCRETO (formato JSON).
 * Nuevo requisito: se agrega sin modificar GeneradorComprobante.
 */
public class ComprobanteJSON implements Comprobante {

    @Override
    public void generar(String contenido) {
        System.out.println(
            "Generando comprobante JSON: " + contenido
        );
    }
}
