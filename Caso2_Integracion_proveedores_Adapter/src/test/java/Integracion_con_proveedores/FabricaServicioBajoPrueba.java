package Integracion_con_proveedores;

/**
 * Único punto donde se ENSAMBLA el objeto bajo prueba de la línea base.
 *
 * Antes de la refactorización las dependencias se inyectaban por reflexión
 * (ver historial de Git). Ahora se usa la raíz de composición real; las
 * pruebas de CaracterizacionLogisticaTest permanecen idénticas.
 */
final class FabricaServicioBajoPrueba {

    private FabricaServicioBajoPrueba() {
    }

    static LogisticaServiceAjustado crear(ServicioEnvio proveedorLocal, RapidExpressAPI rapidExpress) {
        return ConfiguracionLogistica.crearServicio(proveedorLocal, rapidExpress);
    }
}
