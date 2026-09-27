import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Factory Method: creadores concretos")
class CreadoresComprobanteTest {

    @Test
    @DisplayName("CreadorPDF crea un ComprobantePDF y se identifica como 'PDF'")
    void creadorPdf() {
        CreadorPDF creador = new CreadorPDF();
        assertInstanceOf(ComprobantePDF.class, creador.crearComprobante());
        assertEquals("PDF", creador.getTipo());
    }

    @Test
    @DisplayName("CreadorHTML crea un ComprobanteHTML y se identifica como 'HTML'")
    void creadorHtml() {
        CreadorHTML creador = new CreadorHTML();
        assertInstanceOf(ComprobanteHTML.class, creador.crearComprobante());
        assertEquals("HTML", creador.getTipo());
    }

    @Test
    @DisplayName("El método fábrica entrega una instancia nueva en cada llamada (igual que el original)")
    void instanciaNuevaEnCadaLlamada() {
        CreadorPDF creador = new CreadorPDF();
        assertNotSame(creador.crearComprobante(), creador.crearComprobante());
    }

    @Test
    @DisplayName("El tipo del creador se normaliza a mayúsculas")
    void tipoNormalizado() {
        CreadorComprobante creador = new CreadorComprobante("xml") {
            @Override
            protected Comprobante crearComprobante() {
                return contenido -> { };
            }
        };
        assertEquals("XML", creador.getTipo());
    }

    @Test
    @DisplayName("Un creador sin tipo o con tipo en blanco es rechazado")
    void tipoObligatorio() {
        assertThrows(IllegalArgumentException.class, () -> new CreadorComprobante("  ") {
            @Override
            protected Comprobante crearComprobante() {
                return contenido -> { };
            }
        });
    }
}
