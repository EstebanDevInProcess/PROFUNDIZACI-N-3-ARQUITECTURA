import java.util.Objects;

/**
 * Patrón Strategy — ESTRATEGIA CONCRETA: porcentaje fijo sobre el valor.
 *
 * El porcentaje es un DATO, no un algoritmo distinto: cliente frecuente,
 * temporada baja y convenio son instancias de esta misma estrategia, en lugar
 * de tres clases casi idénticas.
 */
public class DescuentoPorcentual implements PoliticaDescuento {

    private final String codigo;
    private final double porcentaje;

    /**
     * @param porcentaje fracción entre 0 y 1 (0.10 = 10 %)
     */
    public DescuentoPorcentual(String codigo, double porcentaje) {
        this.codigo = Validaciones.codigo(codigo);
        this.porcentaje = Validaciones.porcentaje(porcentaje);
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public double calcular(Compra compra) {
        Objects.requireNonNull(compra, "La compra es obligatoria");
        // Misma expresión que el código original: valor * porcentaje
        return compra.valor() * porcentaje;
    }
}
