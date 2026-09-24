import java.util.Objects;

/**
 * Patrón Strategy — ESTRATEGIA CONCRETA: porcentaje con un valor máximo de
 * descuento. Modela las alianzas con cajas de compensación, cuyo beneficio
 * suele tener un tope por compra.
 */
public class DescuentoConTope implements PoliticaDescuento {

    private final String codigo;
    private final double porcentaje;
    private final double tope;

    public DescuentoConTope(String codigo, double porcentaje, double tope) {
        this.codigo = Validaciones.codigo(codigo);
        this.porcentaje = Validaciones.porcentaje(porcentaje);
        if (tope < 0) {
            throw new IllegalArgumentException("El tope no puede ser negativo");
        }
        this.tope = tope;
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public double calcular(Compra compra) {
        Objects.requireNonNull(compra, "La compra es obligatoria");
        return Math.min(compra.valor() * porcentaje, tope);
    }
}
