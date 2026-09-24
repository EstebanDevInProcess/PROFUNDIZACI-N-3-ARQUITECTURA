package Integracion_con_proveedores;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Raíz de composición: ÚNICO lugar que conoce las APIs externas y las envuelve
 * en su adaptador. Integrar un operador nuevo = crear su adaptador y agregar
 * una línea aquí. LogisticaServiceAjustado no se modifica.
 */
public final class ConfiguracionLogistica {

    public static final String LOCAL = "LOCAL";
    public static final String RAPID = "RAPID";
    public static final String ANDES = "ANDES";
    public static final String CARGA = "CARGA";
    public static final String MOTO = "MOTO";

    private ConfiguracionLogistica() {
        // Clase utilitaria: no se instancia.
    }

    /**
     * @param proveedorLocal proveedor original, que ya implementa ServicioEnvio
     * @param rapidExpress   librería externa de RapidExpress
     */
    public static LogisticaServiceAjustado crearServicio(
            ServicioEnvio proveedorLocal,
            RapidExpressAPI rapidExpress) {

        Map<String, ServicioEnvio> proveedores = new LinkedHashMap<>();
        proveedores.put(LOCAL, proveedorLocal);
        proveedores.put(RAPID, new AdaptadorRapidExpress(rapidExpress));
        proveedores.put(ANDES, new AdaptadorEnviosAndes(new EnviosAndesAPI()));
        proveedores.put(CARGA, new AdaptadorCargaExpress(new CargaExpressClient()));
        proveedores.put(MOTO, new AdaptadorMotoEnvios(new MotoEnviosService()));
        return new LogisticaServiceAjustado(proveedores);
    }
}
