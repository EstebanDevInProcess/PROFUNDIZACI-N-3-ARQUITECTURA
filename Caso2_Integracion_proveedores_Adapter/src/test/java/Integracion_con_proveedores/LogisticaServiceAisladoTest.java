package Integracion_con_proveedores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * El cliente se prueba con dobles de ServicioEnvio: no necesita ninguna API externa.
 */
@DisplayName("LogisticaServiceAjustado aislado con dobles de ServicioEnvio")
class LogisticaServiceAisladoTest {

    @Test
    @DisplayName("Delega en el servicio registrado para el código solicitado")
    void delegaEnElProveedorSolicitado() {
        ServicioEnvioFalso a = new ServicioEnvioFalso(10.0);
        ServicioEnvioFalso b = new ServicioEnvioFalso(20.0);
        LogisticaServiceAjustado servicio = new LogisticaServiceAjustado(Map.of("A", a, "B", b));

        assertEquals(20.0, servicio.cotizar("B", "X", "Y", 1.5), 1e-9);
        assertEquals(List.of("X|Y|1.5"), b.llamadas);
        assertTrue(a.llamadas.isEmpty());
    }

    @Test
    @DisplayName("El error de proveedor no soportado indica el código recibido")
    void mensajeDeProveedorNoSoportado() {
        LogisticaServiceAjustado servicio = new LogisticaServiceAjustado(Map.of());
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> servicio.cotizar("DHL", "A", "B", 1.0));
        assertEquals("Proveedor logístico no soportado: DHL", e.getMessage());
    }

    @Test
    @DisplayName("Copia defensiva: cambios posteriores al mapa no afectan al servicio")
    void copiaDefensiva() {
        Map<String, ServicioEnvio> mapa = new HashMap<>();
        mapa.put("A", new ServicioEnvioFalso(1.0));
        LogisticaServiceAjustado servicio = new LogisticaServiceAjustado(mapa);
        mapa.put("B", new ServicioEnvioFalso(2.0));

        assertEquals(java.util.Set.of("A"), servicio.proveedoresDisponibles());
    }

    @Test
    @DisplayName("Rechaza un mapa null o un proveedor sin implementación")
    void validaDependencias() {
        assertThrows(NullPointerException.class, () -> new LogisticaServiceAjustado(null));
        Map<String, ServicioEnvio> conNulo = new HashMap<>();
        conNulo.put("X", null);
        assertThrows(NullPointerException.class, () -> new LogisticaServiceAjustado(conNulo));
    }

    @Test
    @DisplayName("La configuración por defecto expone LOCAL y RAPID")
    void configuracionPorDefecto() {
        LogisticaServiceAjustado servicio =
            ConfiguracionLogistica.crearServicio(new ServicioEnvioFalso(0), new RapidExpressAPI());
        assertTrue(servicio.proveedoresDisponibles().containsAll(List.of("LOCAL", "RAPID")));
    }
}
