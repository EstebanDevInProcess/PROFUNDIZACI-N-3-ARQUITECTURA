package Integracion_con_proveedores;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Patrón Adapter — rol CLIENTE.
 *
 * Cotiza con el proveedor indicado trabajando SOLO con la interfaz
 * {@link ServicioEnvio}. No conoce ninguna API externa: las diferencias de cada
 * proveedor viven en su adaptador. Agregar un proveedor no modifica esta clase.
 */
public class LogisticaServiceAjustado {

    private final Map<String, ServicioEnvio> proveedores;

    /**
     * @param proveedores servicios de envío indexados por código de proveedor
     *                    (el código distingue mayúsculas, como en el diseño original)
     */
    public LogisticaServiceAjustado(Map<String, ServicioEnvio> proveedores) {
        Objects.requireNonNull(proveedores, "El mapa de proveedores es obligatorio");
        proveedores.forEach((codigo, servicio) -> {
            Objects.requireNonNull(codigo, "El código de proveedor no puede ser null");
            Objects.requireNonNull(servicio, "Proveedor sin implementación: " + codigo);
        });
        this.proveedores = Map.copyOf(proveedores);
    }

    public double cotizar(
            String proveedor,
            String origen,
            String destino,
            double pesoKg) {

        Objects.requireNonNull(proveedor, "El código de proveedor es obligatorio");

        ServicioEnvio servicio = proveedores.get(proveedor);
        if (servicio == null) {
            throw new IllegalArgumentException("Proveedor logístico no soportado: " + proveedor);
        }

        return servicio.calcularCosto(origen, destino, pesoKg);
    }

    /** Códigos de proveedor disponibles (solo lectura). */
    public Set<String> proveedoresDisponibles() {
        return proveedores.keySet();
    }
}
