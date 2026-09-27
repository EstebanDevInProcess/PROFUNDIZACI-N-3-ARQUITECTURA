import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CatalogoPoliticas: incorporar, reemplazar, activar y desactivar")
class CatalogoPoliticasTest {

    private final PoliticaFija a = new PoliticaFija("A", 1);
    private final CatalogoPoliticas catalogo = new CatalogoPoliticas().registrar(a);

    @Test
    @DisplayName("Una política registrada queda activa")
    void registrar() {
        assertSame(a, catalogo.buscarActiva("A").orElseThrow());
    }

    @Test
    @DisplayName("No permite registrar dos veces el mismo código (se debe usar reemplazar)")
    void duplicado() {
        assertThrows(IllegalStateException.class, () -> catalogo.registrar(new PoliticaFija("A", 2)));
    }

    @Test
    @DisplayName("Reemplazar cambia el algoritmo de una política existente")
    void reemplazar() {
        PoliticaFija nueva = new PoliticaFija("A", 2);
        catalogo.reemplazar(nueva);
        assertSame(nueva, catalogo.buscarActiva("A").orElseThrow());
    }

    @Test
    @DisplayName("No se puede reemplazar ni desactivar una política inexistente")
    void inexistente() {
        assertThrows(IllegalStateException.class, () -> catalogo.reemplazar(new PoliticaFija("Z", 1)));
        assertThrows(IllegalStateException.class, () -> catalogo.desactivar("Z"));
    }

    @Test
    @DisplayName("Desactivar oculta la política y activar la restablece")
    void desactivarYActivar() {
        catalogo.desactivar("A");
        assertFalse(catalogo.buscarActiva("A").isPresent());
        catalogo.activar("A");
        assertSame(a, catalogo.buscarActiva("A").orElseThrow());
    }

    @Test
    @DisplayName("Reemplazar una política inactiva la mantiene inactiva")
    void reemplazarConservaEstado() {
        catalogo.desactivar("A").reemplazar(new PoliticaFija("A", 9));
        assertFalse(catalogo.buscarActiva("A").isPresent());
    }

    @Test
    @DisplayName("Lista solo los códigos activos, en orden alfabético")
    void codigosActivos() {
        catalogo.registrar(new PoliticaFija("C", 1)).registrar(new PoliticaFija("B", 1)).desactivar("C");
        assertEquals(List.of("A", "B"), List.copyOf(catalogo.codigosActivos()));
    }

    @Test
    @DisplayName("Buscar con código null lanza NullPointerException")
    void codigoNulo() {
        assertThrows(NullPointerException.class, () -> catalogo.buscarActiva(null));
    }
}
