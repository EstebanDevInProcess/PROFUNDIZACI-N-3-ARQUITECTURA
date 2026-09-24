import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * NUEVO REQUISITO DE CAMBIO: las cinco políticas anunciadas por Mercadeo,
 * aplicadas a través del MISMO CalculadorDescuento, que no se modificó.
 */
@DisplayName("Nuevo requisito: aniversario, regional, municipio, temporales y cajas de compensación")
class NuevasPoliticasTest {

    private static final double DELTA = 1e-9;
    private final CalculadorDescuento calculador = new CalculadorDescuento();

    private static Compra compra(double valor, String municipio, LocalDate fecha) {
        return new Compra(valor, municipio, fecha);
    }

    @Test
    @DisplayName("Aniversario: 25 % solo en octubre de 2026 (vigencia inclusiva)")
    void aniversario() {
        assertEquals(25_000.0, calculador.calcular("ANIVERSARIO", compra(100_000, null, LocalDate.of(2026, 10, 1))), DELTA);
        assertEquals(25_000.0, calculador.calcular("ANIVERSARIO", compra(100_000, null, LocalDate.of(2026, 10, 31))), DELTA);
        assertEquals(0.0, calculador.calcular("ANIVERSARIO", compra(100_000, null, LocalDate.of(2026, 11, 1))), DELTA);
        assertEquals(0.0, calculador.calcular("ANIVERSARIO", 100_000), DELTA);
    }

    @Test
    @DisplayName("Promoción regional Sumapaz: 12 % en sus municipios, sin importar tildes ni mayúsculas")
    void promocionRegional() {
        assertEquals(12_000.0, calculador.calcular("PROMO_REGIONAL_SUMAPAZ", compra(100_000, "Fusagasugá", null)), DELTA);
        assertEquals(12_000.0, calculador.calcular("PROMO_REGIONAL_SUMAPAZ", compra(100_000, " fusagasuga ", null)), DELTA);
        assertEquals(0.0, calculador.calcular("PROMO_REGIONAL_SUMAPAZ", compra(100_000, "Girardot", null)), DELTA);
    }

    @Test
    @DisplayName("Descuento por municipio: porcentaje distinto por municipio")
    void descuentoPorMunicipio() {
        assertEquals(8_000.0, calculador.calcular("DESCUENTO_MUNICIPIO", compra(100_000, "Girardot", null)), DELTA);
        assertEquals(10_000.0, calculador.calcular("DESCUENTO_MUNICIPIO", compra(100_000, "Zipaquira", null)), DELTA);
        assertEquals(0.0, calculador.calcular("DESCUENTO_MUNICIPIO", compra(100_000, null, null)), DELTA);
    }

    @Test
    @DisplayName("Promoción temporal de mitad de año: 18 % entre el 15 de junio y el 15 de julio de 2027")
    void promocionTemporal() {
        assertEquals(18_000.0, calculador.calcular("VACACIONES_MITAD_ANIO", compra(100_000, null, LocalDate.of(2027, 7, 1))), DELTA);
        assertEquals(0.0, calculador.calcular("VACACIONES_MITAD_ANIO", compra(100_000, null, LocalDate.of(2027, 6, 14))), DELTA);
    }

    @Test
    @DisplayName("Caja de compensación: 30 % con tope de 200.000")
    void cajaCompensacion() {
        assertEquals(150_000.0, calculador.calcular("CAJA_COMPENSACION", 500_000), DELTA);
        assertEquals(200_000.0, calculador.calcular("CAJA_COMPENSACION", 2_000_000), DELTA);
    }

    @Test
    @DisplayName("Las políticas nuevas validan su configuración")
    void validaciones() {
        assertThrows(IllegalArgumentException.class, () ->
            new PromocionTemporal("X", 0.1, LocalDate.of(2027, 1, 2), LocalDate.of(2027, 1, 1)));
        assertThrows(IllegalArgumentException.class, () -> new DescuentoConTope("X", 0.1, -1));
    }

    @Test
    @DisplayName("El catálogo por defecto tiene las 3 políticas originales y las 5 nuevas")
    void catalogoCompleto() {
        assertTrue(ConfiguracionDescuentos.catalogoPorDefecto().codigosActivos().containsAll(List.of(
            "FRECUENTE", "TEMPORADA_BAJA", "CONVENIO", "ANIVERSARIO", "PROMO_REGIONAL_SUMAPAZ",
            "DESCUENTO_MUNICIPIO", "VACACIONES_MITAD_ANIO", "CAJA_COMPENSACION")));
    }
}
