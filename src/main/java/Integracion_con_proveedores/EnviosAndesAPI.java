package Integracion_con_proveedores;

/**
 * LIBRERÍA EXTERNA SIMULADA (no modificable) — operador EnviosAndes.
 * Incompatibilidades: nombre del método, peso en LIBRAS y tarifa en CENTAVOS (long).
 */
public class EnviosAndesAPI {

    public long consultarTarifa(String ciudadOrigen, String ciudadDestino, double pesoLibras) {
        // Simulación: 150 centavos por libra
        return Math.round(pesoLibras * 150);
    }
}
