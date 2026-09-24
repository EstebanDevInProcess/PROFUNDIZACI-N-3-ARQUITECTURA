import java.util.Objects;

/**
 * Patrón Factory Method — rol CREADOR CONCRETO con configuración propia.
 *
 * Muestra por qué se eligió un objeto creador (y no una simple lambda): el
 * creador guarda los datos del convenio de cada aliado y un mismo creador
 * sirve para varios aliados sin crear una clase por cada uno.
 */
public class CreadorAliado extends CreadorComprobante {

    private final String nombreAliado;

    public CreadorAliado(String tipo, String nombreAliado) {
        super(tipo);
        this.nombreAliado = Objects.requireNonNull(nombreAliado, "El nombre del aliado es obligatorio");
    }

    @Override
    protected Comprobante crearComprobante() {
        return new ComprobanteAliado(nombreAliado);
    }
}
