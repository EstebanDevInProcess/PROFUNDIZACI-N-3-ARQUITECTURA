import java.time.LocalDate;

/**
 * Datos de la compra que una política de descuento puede necesitar.
 * (Refactorización "Introducir objeto parámetro": las nuevas políticas requieren
 * municipio y fecha, que no caben en la firma original tipo + valor.)
 *
 * @param valor     valor de la compra
 * @param municipio municipio del plan turístico (puede ser null si no se conoce)
 * @param fecha     fecha de la compra (puede ser null si no se conoce)
 */
public record Compra(double valor, String municipio, LocalDate fecha) {

    /** Compra de la que solo se conoce el valor (equivale a la firma original). */
    public static Compra deValor(double valor) {
        return new Compra(valor, null, null);
    }
}
