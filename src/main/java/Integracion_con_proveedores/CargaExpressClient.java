package Integracion_con_proveedores;

import java.util.Locale;
import java.util.Map;

/**
 * LIBRERÍA EXTERNA SIMULADA (no modificable) — operador CargaExpress.
 * Incompatibilidades: recibe un mapa de parámetros con claves en inglés y
 * responde un TEXTO con formato "COP:<valor>".
 */
public class CargaExpressClient {

    public String solicitarCotizacion(Map<String, String> parametros) {
        double kg = Double.parseDouble(parametros.get("kg"));
        // Simulación: 3 pesos por kg
        return "COP:" + String.format(Locale.ROOT, "%.2f", kg * 3.0);
    }
}
