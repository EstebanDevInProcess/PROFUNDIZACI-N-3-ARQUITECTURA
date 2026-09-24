import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * NUEVO REQUISITO DE CAMBIO: incorporar comprobantes XML y JSON.
 * Evidencia de que el formato nuevo funciona usando la MISMA API pública
 * y sin modificar GeneradorComprobante.
 */
@DisplayName("Nuevo requisito: comprobantes XML y JSON")
class NuevosFormatosXmlJsonTest {

    private final PrintStream salidaOriginal = System.out;
    private ByteArrayOutputStream salidaCapturada;

    @BeforeEach
    void capturar() {
        salidaCapturada = new ByteArrayOutputStream();
        System.setOut(new PrintStream(salidaCapturada, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void restaurar() {
        System.setOut(salidaOriginal);
    }

    @Test
    @DisplayName("XML se genera a través del coordinador existente")
    void generaXml() {
        new GeneradorComprobante().generar("xml", "Compra #3001");
        assertEquals("Generando comprobante XML: Compra #3001" + System.lineSeparator(),
            salidaCapturada.toString(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("JSON se genera a través del coordinador existente")
    void generaJson() {
        new GeneradorComprobante().generar("JSON", "Compra #3002");
        assertEquals("Generando comprobante JSON: Compra #3002" + System.lineSeparator(),
            salidaCapturada.toString(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("Los creadores XML y JSON producen su comprobante concreto")
    void creadoresNuevos() {
        assertInstanceOf(ComprobanteXML.class, new CreadorXML().crearComprobante());
        assertInstanceOf(ComprobanteJSON.class, new CreadorJSON().crearComprobante());
    }

    @Test
    @DisplayName("La configuración por defecto incluye los formatos originales y los nuevos")
    void configuracionPorDefecto() {
        assertTrue(ConfiguracionComprobantes.registroPorDefecto().tiposSoportados()
            .containsAll(List.of("PDF", "HTML", "XML", "JSON")));
    }
}
