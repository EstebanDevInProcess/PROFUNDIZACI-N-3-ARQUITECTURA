/**
 * Patrón Factory Method — rol PRODUCTO CONCRETO (formato HTML).
 */
public class ComprobanteHTML implements Comprobante {

    @Override
    public void generar(String contenido) {
        System.out.println(
            "Generando comprobante HTML: " + contenido
        );
    }
}
