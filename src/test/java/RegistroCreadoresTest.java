import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("RegistroCreadores: selección del creador por tipo")
class RegistroCreadoresTest {

    private final RegistroCreadores registro = new RegistroCreadores()
        .registrar(new CreadorPDF())
        .registrar(new CreadorHTML());

    @Test
    @DisplayName("Encuentra el creador sin distinguir mayúsculas")
    void buscaSinDistinguirMayusculas() {
        assertInstanceOf(CreadorPDF.class, registro.buscar("pdf").orElseThrow());
        assertInstanceOf(CreadorHTML.class, registro.buscar("Html").orElseThrow());
    }

    @Test
    @DisplayName("Un tipo no registrado devuelve Optional vacío")
    void tipoNoRegistrado() {
        assertFalse(registro.buscar("TXT").isPresent());
    }

    @Test
    @DisplayName("No permite registrar dos creadores para el mismo tipo")
    void rechazaDuplicados() {
        assertThrows(IllegalStateException.class, () -> registro.registrar(new CreadorPDF()));
    }

    @Test
    @DisplayName("Buscar con tipo null lanza NullPointerException")
    void tipoNulo() {
        assertThrows(NullPointerException.class, () -> registro.buscar(null));
    }

    @Test
    @DisplayName("Informa los tipos soportados en orden alfabético")
    void tiposSoportados() {
        assertEquals(List.of("HTML", "PDF"), List.copyOf(registro.tiposSoportados()));
    }
}
