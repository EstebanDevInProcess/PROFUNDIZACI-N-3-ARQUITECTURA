import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * LÍNEA BASE (pruebas de caracterización).
 *
 * Documentan el comportamiento observable del código ORIGINAL, sin juzgar si es
 * "correcto". Se escriben ANTES de refactorizar y se ejecutan SIN CAMBIOS
 * después de cada paso de la refactorización: si alguna falla, la
 * refactorización alteró el comportamiento.
 *
 * Única API pública que se usa: new GeneradorComprobante().generar(tipo, contenido)
 */
@DisplayName("Línea base: comportamiento observable de GeneradorComprobante")
class CaracterizacionGeneradorComprobanteTest {

    private final PrintStream salidaOriginal = System.out;
    private ByteArrayOutputStream salidaCapturada;
    private GeneradorComprobante generador;

    @BeforeEach
    void capturarSalidaEstandar() {
        salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada, true, StandardCharsets.UTF_8));
        generador = new GeneradorComprobante();
    }

    @AfterEach
    void restaurarSalidaEstandar() {
        System.setOut(salidaOriginal);
    }

    private String salida() {
        return salidaCapturada.toString(StandardCharsets.UTF_8);
    }

    private static String linea(String texto) {
        return texto + System.lineSeparator();
    }

    // ---------- Casos felices ----------

    @Test
    @DisplayName("PDF: imprime exactamente 'Generando comprobante PDF: <contenido>'")
    void generaComprobantePdf() {
        generador.generar("PDF", "Compra #1001");
        assertEquals(linea("Generando comprobante PDF: Compra #1001"), salida());
    }

    @Test
    @DisplayName("HTML: imprime exactamente 'Generando comprobante HTML: <contenido>'")
    void generaComprobanteHtml() {
        generador.generar("HTML", "Compra #1002");
        assertEquals(linea("Generando comprobante HTML: Compra #1002"), salida());
    }

    @Test
    @DisplayName("El tipo no distingue mayúsculas/minúsculas (equalsIgnoreCase)")
    void tipoNoDistingueMayusculas() {
        generador.generar("pdf", "A");
        generador.generar("HtMl", "B");
        assertEquals(
            linea("Generando comprobante PDF: A") + linea("Generando comprobante HTML: B"),
            salida());
    }

    @Test
    @DisplayName("Varias generaciones consecutivas se procesan en orden e independientes")
    void variasGeneracionesConsecutivas() {
        generador.generar("HTML", "1");
        generador.generar("PDF", "2");
        generador.generar("HTML", "3");
        assertEquals(
            linea("Generando comprobante HTML: 1")
                + linea("Generando comprobante PDF: 2")
                + linea("Generando comprobante HTML: 3"),
            salida());
    }

    // ---------- Bordes del contenido ----------

    @Test
    @DisplayName("Contenido vacío: se genera igual, con el texto vacío")
    void contenidoVacio() {
        generador.generar("HTML", "");
        assertEquals(linea("Generando comprobante HTML: "), salida());
    }

    @Test
    @DisplayName("Contenido null: no falla, imprime la palabra 'null'")
    void contenidoNulo() {
        generador.generar("PDF", null);
        assertEquals(linea("Generando comprobante PDF: null"), salida());
    }

    // ---------- Tipos no soportados ----------

    @Test
    @DisplayName("Tipo no soportado: IllegalArgumentException con mensaje exacto y sin salida")
    void tipoNoSoportado() {
        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class,
            () -> generador.generar("TXT", "Compra #1003"));
        assertEquals("Tipo de comprobante no soportado", error.getMessage());
        assertEquals("", salida());
    }

    @Test
    @DisplayName("Tipo vacío: se trata como no soportado")
    void tipoVacio() {
        assertThrows(IllegalArgumentException.class, () -> generador.generar("", "x"));
        assertEquals("", salida());
    }

    @Test
    @DisplayName("Tipo con espacios: NO se recorta (' PDF ' no es soportado)")
    void tipoConEspaciosNoSeRecorta() {
        assertThrows(IllegalArgumentException.class, () -> generador.generar(" PDF ", "x"));
        assertEquals("", salida());
    }

    @Test
    @DisplayName("Tipo null: lanza NullPointerException (comportamiento actual documentado)")
    void tipoNulo() {
        assertThrows(NullPointerException.class, () -> generador.generar(null, "x"));
        assertEquals("", salida());
    }
}
