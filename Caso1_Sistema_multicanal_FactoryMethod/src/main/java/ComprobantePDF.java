/**
 * Patrón Factory Method — rol PRODUCTO CONCRETO (formato PDF).
 */
public class ComprobantePDF implements Comprobante {

    @Override
    public void generar(String contenido) {
        System.out.println(
            "Generando comprobante PDF: " + contenido
        );
    }
}
