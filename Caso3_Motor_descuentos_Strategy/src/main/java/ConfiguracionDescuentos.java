import java.time.LocalDate;
import java.util.Map;

/**
 * Raíz de composición: ÚNICO lugar donde se declaran las políticas vigentes.
 * Incorporar una política = una línea aquí (o una llamada a registrar() en
 * tiempo de ejecución). CalculadorDescuento no se modifica.
 */
public final class ConfiguracionDescuentos {

    private ConfiguracionDescuentos() {
        // Clase utilitaria: no se instancia.
    }

    public static CatalogoPoliticas catalogoPorDefecto() {
        return new CatalogoPoliticas()
            .registrar(new DescuentoPorcentual("FRECUENTE", 0.10))
            .registrar(new DescuentoPorcentual("TEMPORADA_BAJA", 0.15))
            .registrar(new DescuentoPorcentual("CONVENIO", 0.20))
            // Nuevas políticas de Mercadeo (porcentajes, fechas y topes de ejemplo)
            .registrar(new PromocionTemporal("ANIVERSARIO", 0.25,
                LocalDate.of(2026, 10, 1), LocalDate.of(2026, 10, 31)))
            .registrar(DescuentoPorMunicipio.region("PROMO_REGIONAL_SUMAPAZ", 0.12,
                "Fusagasugá", "Arbeláez", "Cabrera", "Granada", "Pandi",
                "Pasca", "San Bernardo", "Silvania", "Tibacuy", "Venecia"))
            .registrar(new DescuentoPorMunicipio("DESCUENTO_MUNICIPIO", Map.of(
                "Girardot", 0.08,
                "Zipaquirá", 0.10,
                "La Mesa", 0.07)))
            .registrar(new PromocionTemporal("VACACIONES_MITAD_ANIO", 0.18,
                LocalDate.of(2027, 6, 15), LocalDate.of(2027, 7, 15)))
            .registrar(new DescuentoConTope("CAJA_COMPENSACION", 0.30, 200_000));
    }
}
