package Integracion_con_proveedores;

import java.lang.reflect.Field;

/**
 * Único punto donde se ENSAMBLA el objeto bajo prueba de la línea base.
 *
 * Versión inicial: LogisticaServiceAjustado no tiene constructor ni setters para
 * sus dependencias, así que se inyectan por reflexión (técnica de caracterización
 * de código legado). Tras la refactorización solo cambia este archivo; las
 * pruebas de CaracterizacionLogisticaTest permanecen idénticas.
 */
final class FabricaServicioBajoPrueba {

    private FabricaServicioBajoPrueba() {
    }

    static LogisticaServiceAjustado crear(ServicioEnvio proveedorLocal, RapidExpressAPI rapidExpress) {
        try {
            LogisticaServiceAjustado servicio = new LogisticaServiceAjustado();
            asignar(servicio, "proveedorLocal", proveedorLocal);
            asignar(servicio, "rapidExpress", rapidExpress);
            return servicio;
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("No se pudo ensamblar el servicio bajo prueba", e);
        }
    }

    private static void asignar(Object objetivo, String campo, Object valor)
            throws ReflectiveOperationException {
        Field f = objetivo.getClass().getDeclaredField(campo);
        f.setAccessible(true);
        f.set(objetivo, valor);
    }
}
