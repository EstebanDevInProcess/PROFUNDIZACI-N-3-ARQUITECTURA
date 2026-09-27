import java.util.Objects;

/**
 * Patrón Factory Method — rol PRODUCTO CONCRETO parametrizable para los
 * formatos particulares de aliados comerciales.
 */
public class ComprobanteAliado implements Comprobante {

    private final String nombreAliado;

    public ComprobanteAliado(String nombreAliado) {
        this.nombreAliado = Objects.requireNonNull(nombreAliado, "El nombre del aliado es obligatorio");
    }

    @Override
    public void generar(String contenido) {
        System.out.println(
            "Generando comprobante para aliado " + nombreAliado + ": " + contenido
        );
    }
}
