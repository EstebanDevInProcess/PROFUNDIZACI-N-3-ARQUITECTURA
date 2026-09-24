package Integracion_con_proveedores;

import java.util.Objects;

/**
 * Patrón Adapter — adapta EnviosAndesAPI a ServicioEnvio.
 * Traducciones: kg → libras y centavos → pesos.
 */
public class AdaptadorEnviosAndes implements ServicioEnvio {

    static final double LIBRAS_POR_KG = 2.20462262185;

    private final EnviosAndesAPI api;

    public AdaptadorEnviosAndes(EnviosAndesAPI api) {
        this.api = Objects.requireNonNull(api, "La API de EnviosAndes es obligatoria");
    }

    @Override
    public double calcularCosto(String origen, String destino, double peso) {
        long centavos = api.consultarTarifa(origen, destino, peso * LIBRAS_POR_KG);
        return centavos / 100.0;
    }
}
