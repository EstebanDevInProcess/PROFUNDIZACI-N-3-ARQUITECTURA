package Integracion_con_proveedores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Línea base: LogisticaService original (un solo proveedor)")
class LogisticaServiceOriginalTest {

    @Test
    @DisplayName("cotizar delega en el ServicioEnvio inyectado sin transformar argumentos")
    void delegaEnServicioEnvio() {
        ServicioEnvioFalso proveedor = new ServicioEnvioFalso(99.0);
        LogisticaService servicio = new LogisticaService(proveedor);

        assertEquals(99.0, servicio.cotizar("Girardot", "Soacha", 3.0), 1e-9);
        assertEquals(List.of("Girardot|Soacha|3.0"), proveedor.llamadas);
    }
}
