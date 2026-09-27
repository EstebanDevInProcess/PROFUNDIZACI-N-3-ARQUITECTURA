package Integracion_con_proveedores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * NUEVO REQUISITO DE CAMBIO: integrar tres operadores con interfaces incompatibles.
 * Cada uno presenta un tipo distinto de incompatibilidad (unidades, formato de
 * respuesta, excepciones) y se integra sin modificar LogisticaServiceAjustado.
 */
@DisplayName("Nuevo requisito: tres operadores adicionales")
class NuevosOperadoresTest {

    private final LogisticaServiceAjustado servicio =
        ConfiguracionLogistica.crearServicio(new ServicioEnvioFalso(0), new RapidExpressAPI());

    @Test
    @DisplayName("EnviosAndes: kg→libras y centavos→pesos (10 kg ≈ 22,05 lb → 3307 centavos → 33,07)")
    void enviosAndes() {
        assertEquals(33.07, servicio.cotizar("ANDES", "Fusagasugá", "Bogotá", 10.0), 1e-9);
    }

    @Test
    @DisplayName("CargaExpress: construye el mapa de parámetros e interpreta 'COP:<valor>'")
    void cargaExpress() {
        assertEquals(7.5, servicio.cotizar("CARGA", "Fusagasugá", "Bogotá", 2.5), 1e-9);
    }

    @Test
    @DisplayName("CargaExpress: una respuesta con formato inesperado se informa como error del proveedor")
    void cargaExpressRespuestaInvalida() {
        assertThrows(ProveedorLogisticoException.class, () -> AdaptadorCargaExpress.interpretar("USD 10"));
        assertThrows(ProveedorLogisticoException.class, () -> AdaptadorCargaExpress.interpretar("COP:abc"));
    }

    @Test
    @DisplayName("MotoEnvios: cotiza dentro de la capacidad (4 + 1 por kg)")
    void motoEnvios() {
        assertEquals(9.0, servicio.cotizar("MOTO", "Fusagasugá", "Silvania", 5.0), 1e-9);
    }

    @Test
    @DisplayName("MotoEnvios: la excepción chequeada se traduce a ProveedorLogisticoException")
    void motoEnviosTraduceExcepcion() {
        ProveedorLogisticoException e = assertThrows(ProveedorLogisticoException.class,
            () -> servicio.cotizar("MOTO", "A", "B", 25.0));
        assertInstanceOf(MotoEnviosException.class, e.getCause());
    }

    @Test
    @DisplayName("La configuración expone los cinco proveedores")
    void cincoProveedores() {
        assertTrue(servicio.proveedoresDisponibles()
            .containsAll(List.of("LOCAL", "RAPID", "ANDES", "CARGA", "MOTO")));
    }
}
