import java.time.LocalDate;
import java.util.Objects;

/**
 * Patrón Strategy — ESTRATEGIA CONCRETA: porcentaje válido solo dentro de una
 * vigencia (fechas inclusivas). Fuera de la vigencia, o sin fecha, no hay descuento.
 * Sirve para campañas como el aniversario y para promociones temporales.
 */
public class PromocionTemporal implements PoliticaDescuento {

    private final String codigo;
    private final double porcentaje;
    private final LocalDate desde;
    private final LocalDate hasta;

    public PromocionTemporal(String codigo, double porcentaje, LocalDate desde, LocalDate hasta) {
        this.codigo = Validaciones.codigo(codigo);
        this.porcentaje = Validaciones.porcentaje(porcentaje);
        this.desde = Objects.requireNonNull(desde, "La fecha inicial es obligatoria");
        this.hasta = Objects.requireNonNull(hasta, "La fecha final es obligatoria");
        if (hasta.isBefore(desde)) {
            throw new IllegalArgumentException("La vigencia termina antes de empezar");
        }
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public double calcular(Compra compra) {
        Objects.requireNonNull(compra, "La compra es obligatoria");
        LocalDate fecha = compra.fecha();
        if (fecha == null || fecha.isBefore(desde) || fecha.isAfter(hasta)) {
            return 0;
        }
        return compra.valor() * porcentaje;
    }
}
