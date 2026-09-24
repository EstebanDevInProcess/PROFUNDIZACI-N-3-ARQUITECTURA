import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Nuevo requisito: formatos particulares de aliados comerciales")
class FormatosAliadosTest {

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
    @DisplayName("Cada aliado usa su propio formato con el mismo creador parametrizable")
    void dosAliadosConUnSoloCreador() {
        GeneradorComprobante generador = new GeneradorComprobante();
        generador.generar("ALIADO_ANDINO", "Compra #4001");
        generador.generar("aliado_sabana", "Compra #4002");

        assertEquals(
            "Generando comprobante para aliado Aliado Andino: Compra #4001" + System.lineSeparator()
                + "Generando comprobante para aliado Aliado Sabana: Compra #4002" + System.lineSeparator(),
            salidaCapturada.toString(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("El creador de aliado se registra con el tipo indicado")
    void tipoDelCreadorAliado() {
        assertEquals("ALIADO_NUEVO", new CreadorAliado("aliado_nuevo", "Nuevo").getTipo());
    }
}
