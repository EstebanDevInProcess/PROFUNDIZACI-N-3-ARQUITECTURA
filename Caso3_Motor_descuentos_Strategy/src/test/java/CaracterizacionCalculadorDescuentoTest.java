import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * LÍNEA BASE (pruebas de caracterización) de CalculadorDescuento.
 * Se escriben ANTES de refactorizar y este archivo NO cambia después.
 * Nota: calcular() devuelve el VALOR DEL DESCUENTO, no el precio final.
 */
@DisplayName("Línea base: comportamiento observable de CalculadorDescuento")
class CaracterizacionCalculadorDescuentoTest {

    private static final double DELTA = 1e-9;

    private final CalculadorDescuento calculador = new CalculadorDescuento();

    // ---------- Modalidades vigentes ----------

    @Test
    @DisplayName("FRECUENTE: 10 % del valor de la compra")
    void clienteFrecuente() {
        assertEquals(10_000.0, calculador.calcular("FRECUENTE", 100_000), DELTA);
    }

    @Test
    @DisplayName("TEMPORADA_BAJA: 15 % del valor de la compra")
    void temporadaBaja() {
        assertEquals(15_000.0, calculador.calcular("TEMPORADA_BAJA", 100_000), DELTA);
    }

    @Test
    @DisplayName("CONVENIO: 20 % del valor de la compra")
    void convenioEmpresarial() {
        assertEquals(20_000.0, calculador.calcular("CONVENIO", 100_000), DELTA);
    }

    @Test
    @DisplayName("Aritmética idéntica al original, bit a bit (valor × 0,15 con decimales)")
    void aritmeticaExacta() {
        assertEquals(33.33 * 0.15, calculador.calcular("TEMPORADA_BAJA", 33.33));
        assertEquals(1234.56 * 0.10, calculador.calcular("FRECUENTE", 1234.56));
    }

    // ---------- Bordes del valor ----------

    @Test
    @DisplayName("Compra de valor cero: descuento cero")
    void valorCero() {
        assertEquals(0.0, calculador.calcular("CONVENIO", 0), DELTA);
    }

    @Test
    @DisplayName("No valida valores negativos: −1000 con CONVENIO → −200")
    void valorNegativo() {
        assertEquals(-200.0, calculador.calcular("CONVENIO", -1_000), DELTA);
    }

    // ---------- Tipos no reconocidos ----------

    @Test
    @DisplayName("Tipo desconocido: descuento 0 (no lanza excepción)")
    void tipoDesconocido() {
        assertEquals(0.0, calculador.calcular("DESCONOCIDO", 100_000), DELTA);
    }

    @Test
    @DisplayName("Distingue mayúsculas: 'frecuente' no aplica descuento")
    void distingueMayusculas() {
        assertEquals(0.0, calculador.calcular("frecuente", 100_000), DELTA);
    }

    @Test
    @DisplayName("No recorta espacios: ' FRECUENTE' no aplica descuento")
    void noRecortaEspacios() {
        assertEquals(0.0, calculador.calcular(" FRECUENTE", 100_000), DELTA);
    }

    @Test
    @DisplayName("Tipo vacío: descuento 0")
    void tipoVacio() {
        assertEquals(0.0, calculador.calcular("", 100_000), DELTA);
    }

    @Test
    @DisplayName("Tipo null: NullPointerException")
    void tipoNulo() {
        assertThrows(NullPointerException.class, () -> calculador.calcular(null, 100_000));
    }
}
