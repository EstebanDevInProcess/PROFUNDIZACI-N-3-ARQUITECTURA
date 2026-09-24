package Integracion_con_proveedores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * DEFECTO LATENTE del código entregado: LogisticaServiceAjustado declara sus
 * dependencias pero nunca las inicializa (no hay constructor ni setters).
 * Tal como está, cualquier cotización válida falla con NullPointerException.
 *
 * Esta prueba documenta el defecto en la línea base. Deja de compilar cuando la
 * refactorización exige las dependencias en el constructor (el defecto se vuelve
 * imposible), y por eso se retira en ese commit.
 */
@DisplayName("Defecto documentado: dependencias no inicializadas")
class DefectoDependenciasNoInicializadasTest {

    @Test
    @DisplayName("Con el constructor por defecto, LOCAL falla con NullPointerException")
    void localFallaSinDependencias() {
        LogisticaServiceAjustado servicio = new LogisticaServiceAjustado();
        assertThrows(NullPointerException.class, () -> servicio.cotizar("LOCAL", "A", "B", 1.0));
    }

    @Test
    @DisplayName("Con el constructor por defecto, RAPID falla con NullPointerException")
    void rapidFallaSinDependencias() {
        LogisticaServiceAjustado servicio = new LogisticaServiceAjustado();
        assertThrows(NullPointerException.class, () -> servicio.cotizar("RAPID", "A", "B", 1.0));
    }
}
