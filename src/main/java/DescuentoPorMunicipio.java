import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Patrón Strategy — ESTRATEGIA CONCRETA: porcentaje según el municipio del plan.
 * Sirve para "descuentos por municipio" (porcentaje distinto por municipio) y
 * para "promociones regionales" (mismo porcentaje para los municipios de una región).
 *
 * El municipio se compara sin tildes, sin distinguir mayúsculas y sin espacios
 * en los extremos ("fusagasuga" = "Fusagasugá").
 */
public class DescuentoPorMunicipio implements PoliticaDescuento {

    private final String codigo;
    private final Map<String, Double> porcentajePorMunicipio = new HashMap<>();

    public DescuentoPorMunicipio(String codigo, Map<String, Double> porcentajePorMunicipio) {
        this.codigo = Validaciones.codigo(codigo);
        Objects.requireNonNull(porcentajePorMunicipio, "Los porcentajes por municipio son obligatorios");
        porcentajePorMunicipio.forEach((municipio, porcentaje) ->
            this.porcentajePorMunicipio.put(normalizar(municipio), Validaciones.porcentaje(porcentaje)));
    }

    /** Promoción regional: el mismo porcentaje para todos los municipios de la región. */
    public static DescuentoPorMunicipio region(String codigo, double porcentaje, String... municipios) {
        Map<String, Double> mapa = new HashMap<>();
        for (String municipio : municipios) {
            mapa.put(municipio, porcentaje);
        }
        return new DescuentoPorMunicipio(codigo, mapa);
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public double calcular(Compra compra) {
        Objects.requireNonNull(compra, "La compra es obligatoria");
        if (compra.municipio() == null) {
            return 0;
        }
        Double porcentaje = porcentajePorMunicipio.get(normalizar(compra.municipio()));
        return porcentaje == null ? 0 : compra.valor() * porcentaje;
    }

    static String normalizar(String municipio) {
        Objects.requireNonNull(municipio, "El municipio no puede ser null");
        String sinTildes = Normalizer.normalize(municipio.trim(), Normalizer.Form.NFD)
            .replaceAll("\\p{M}", "");
        return sinTildes.toUpperCase(Locale.ROOT);
    }
}
