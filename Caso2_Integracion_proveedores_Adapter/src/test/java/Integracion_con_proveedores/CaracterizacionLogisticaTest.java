package Integracion_con_proveedores;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * LÍNEA BASE (pruebas de caracterización) de la cotización multiproveedor.
 * Se escriben ANTES de refactorizar y este archivo NO cambia después.
 * El ensamblaje del objeto bajo prueba está aislado en FabricaServicioBajoPrueba.
 */
@DisplayName("Línea base: cotización con proveedor LOCAL y RAPID")
class CaracterizacionLogisticaTest {

    private static final double COSTO_LOCAL = 12345.0;
    private static final double DELTA = 1e-9;

    private ServicioEnvioFalso local;
    private RapidExpressEspia rapid;
    private LogisticaServiceAjustado servicio;

    @BeforeEach
    void preparar() {
        local = new ServicioEnvioFalso(COSTO_LOCAL);
        rapid = new RapidExpressEspia();
        servicio = FabricaServicioBajoPrueba.crear(local, rapid);
    }

    // ---------- Proveedor LOCAL ----------

    @Test
    @DisplayName("LOCAL delega con los mismos argumentos y devuelve el costo del proveedor")
    void localDelegaSinTransformar() {
        double costo = servicio.cotizar("LOCAL", "Fusagasugá", "Bogotá", 2.5);
        assertEquals(COSTO_LOCAL, costo, DELTA);
        assertEquals(List.of("Fusagasugá|Bogotá|2.5"), local.llamadas);
        assertTrue(rapid.rutas.isEmpty(), "No debe invocar a RapidExpress");
    }

    // ---------- Proveedor RAPID: traducción de la interfaz ----------

    @Test
    @DisplayName("RAPID: ruta 'origen-destino', kg→gramos y precio = gramos × 0,0025")
    void rapidTraduceRutaYPeso() {
        double costo = servicio.cotizar("RAPID", "Fusagasugá", "Bogotá", 2.0);
        assertEquals(5.0, costo, DELTA);
        assertEquals(List.of("Fusagasugá-Bogotá"), rapid.rutas);
        assertEquals(List.of(2000), rapid.gramos);
        assertTrue(local.llamadas.isEmpty(), "No debe invocar al proveedor local");
    }

    @Test
    @DisplayName("RAPID: medio kilo = 500 g = 1,25")
    void rapidMedioKilo() {
        assertEquals(1.25, servicio.cotizar("RAPID", "A", "B", 0.5), DELTA);
        assertEquals(List.of(500), rapid.gramos);
    }

    @Test
    @DisplayName("RAPID: las fracciones de gramo se TRUNCAN (1,2345 kg → 1234 g)")
    void rapidTruncaFracciones() {
        servicio.cotizar("RAPID", "A", "B", 1.2345);
        assertEquals(List.of(1234), rapid.gramos);
    }

    @Test
    @DisplayName("RAPID: artefacto de punto flotante documentado (1,005 kg → 1004 g)")
    void rapidArtefactoPuntoFlotante() {
        servicio.cotizar("RAPID", "A", "B", 1.005);
        assertEquals(List.of(1004), rapid.gramos);
    }

    @Test
    @DisplayName("RAPID: no valida pesos negativos (−1 kg → −1000 g → −2,5)")
    void rapidPesoNegativo() {
        assertEquals(-2.5, servicio.cotizar("RAPID", "A", "B", -1.0), DELTA);
        assertEquals(List.of(-1000), rapid.gramos);
    }

    @Test
    @DisplayName("RAPID: pesos enormes saturan el int (10.000.000 kg → Integer.MAX_VALUE g)")
    void rapidDesbordamiento() {
        servicio.cotizar("RAPID", "A", "B", 1e7);
        assertEquals(List.of(Integer.MAX_VALUE), rapid.gramos);
    }

    // ---------- Selección del proveedor ----------

    @Test
    @DisplayName("El código de proveedor distingue mayúsculas ('local' no es válido)")
    void proveedorDistingueMayusculas() {
        assertThrows(IllegalArgumentException.class,
            () -> servicio.cotizar("local", "A", "B", 1.0));
        assertTrue(local.llamadas.isEmpty());
    }

    @Test
    @DisplayName("Proveedor desconocido: IllegalArgumentException y ninguna API invocada")
    void proveedorDesconocido() {
        assertThrows(IllegalArgumentException.class,
            () -> servicio.cotizar("DESCONOCIDO", "A", "B", 1.0));
        assertTrue(local.llamadas.isEmpty());
        assertTrue(rapid.rutas.isEmpty());
    }

    @Test
    @DisplayName("Proveedor null: NullPointerException")
    void proveedorNulo() {
        assertThrows(NullPointerException.class,
            () -> servicio.cotizar(null, "A", "B", 1.0));
    }
}
