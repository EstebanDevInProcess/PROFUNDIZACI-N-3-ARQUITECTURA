package Integracion_con_proveedores;

import java.util.Map;
import java.util.Objects;

/**
 * Patrón Adapter — adapta CargaExpressClient a ServicioEnvio.
 * Traducciones: parámetros → mapa con claves "from"/"to"/"kg" y respuesta
 * "COP:<valor>" → double. Un formato inesperado se informa como error del proveedor.
 */
public class AdaptadorCargaExpress implements ServicioEnvio {

    private static final String PREFIJO = "COP:";

    private final CargaExpressClient cliente;

    public AdaptadorCargaExpress(CargaExpressClient cliente) {
        this.cliente = Objects.requireNonNull(cliente, "El cliente de CargaExpress es obligatorio");
    }

    @Override
    public double calcularCosto(String origen, String destino, double peso) {
        String respuesta = cliente.solicitarCotizacion(Map.of(
            "from", origen,
            "to", destino,
            "kg", Double.toString(peso)));
        return interpretar(respuesta);
    }

    static double interpretar(String respuesta) {
        if (respuesta == null || !respuesta.startsWith(PREFIJO)) {
            throw new ProveedorLogisticoException(
                "Respuesta inesperada de CargaExpress: " + respuesta, null);
        }
        try {
            return Double.parseDouble(respuesta.substring(PREFIJO.length()));
        } catch (NumberFormatException e) {
            throw new ProveedorLogisticoException(
                "Valor inválido en la respuesta de CargaExpress: " + respuesta, e);
        }
    }
}
