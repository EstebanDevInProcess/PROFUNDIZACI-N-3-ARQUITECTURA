import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Pruebas del coordinador AISLADO con un doble de prueba.
 * En el diseño original esto era imposible: el coordinador instanciaba
 * directamente ComprobantePDF/ComprobanteHTML y solo se podía verificar
 * capturando System.out.
 */
@DisplayName("GeneradorComprobante aislado con un creador falso (doble de prueba)")
class GeneradorComprobanteAisladoTest {

    /** Doble de prueba: registra los contenidos en memoria en lugar de imprimir. */
    private static final class CreadorFalso extends CreadorComprobante {
        private final List<String> emitidos = new ArrayList<>();

        CreadorFalso(String tipo) {
            super(tipo);
        }

        @Override
        protected Comprobante crearComprobante() {
            return emitidos::add;
        }
    }

    @Test
    @DisplayName("Delega la emisión en el creador registrado para el tipo")
    void delegaEnElCreador() {
        CreadorFalso falso = new CreadorFalso("FALSO");
        GeneradorComprobante generador =
            new GeneradorComprobante(new RegistroCreadores().registrar(falso));

        generador.generar("falso", "Compra #2001");

        assertEquals(List.of("Compra #2001"), falso.emitidos);
    }

    @Test
    @DisplayName("Solo se invoca el creador del tipo solicitado")
    void soloInvocaElCreadorSolicitado() {
        CreadorFalso a = new CreadorFalso("A");
        CreadorFalso b = new CreadorFalso("B");
        GeneradorComprobante generador =
            new GeneradorComprobante(new RegistroCreadores().registrar(a).registrar(b));

        generador.generar("B", "x");

        assertTrue(a.emitidos.isEmpty());
        assertEquals(List.of("x"), b.emitidos);
    }

    @Test
    @DisplayName("Tipo no registrado: mismo error que el diseño original")
    void tipoNoRegistrado() {
        GeneradorComprobante generador = new GeneradorComprobante(new RegistroCreadores());
        IllegalArgumentException error = assertThrows(
            IllegalArgumentException.class, () -> generador.generar("PDF", "x"));
        assertEquals("Tipo de comprobante no soportado", error.getMessage());
    }

    @Test
    @DisplayName("No acepta un registro null")
    void registroObligatorio() {
        assertThrows(NullPointerException.class, () -> new GeneradorComprobante(null));
    }
}
