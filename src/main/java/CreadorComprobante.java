import java.util.Locale;

/**
 * Patrón Factory Method — rol CREADOR.
 *
 * Declara el método fábrica {@link #crearComprobante()} y la operación
 * {@link #emitir(String)} que usa el producto sin conocer su clase concreta.
 * Cada formato nuevo se incorpora con una subclase (creador concreto), sin
 * modificar esta clase ni el coordinador (principio abierto/cerrado).
 */
public abstract class CreadorComprobante {

    private final String tipo;

    protected CreadorComprobante(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException(
                "El tipo del creador de comprobantes es obligatorio"
            );
        }
        this.tipo = tipo.toUpperCase(Locale.ROOT);
    }

    /** Identificador del formato con el que el creador se registra (p. ej. "PDF"). */
    public final String getTipo() {
        return tipo;
    }

    /**
     * FACTORY METHOD: cada creador concreto decide qué producto instancia.
     * Debe devolver una instancia nueva en cada invocación (como el código original).
     */
    protected abstract Comprobante crearComprobante();

    /**
     * Operación del creador: obtiene el producto mediante el método fábrica
     * y lo usa a través de la interfaz {@link Comprobante}.
     */
    public final void emitir(String contenido) {
        Comprobante comprobante = crearComprobante();
        comprobante.generar(contenido);
    }
}
