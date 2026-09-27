package Integracion_con_proveedores;

import java.util.Objects;

/**
 * Patrón Adapter — adapta MotoEnviosService a ServicioEnvio.
 * Traducción: la excepción chequeada MotoEnviosException se convierte en
 * ProveedorLogisticoException (no chequeada, propia de la aplicación).
 */
public class AdaptadorMotoEnvios implements ServicioEnvio {

    private final MotoEnviosService servicio;

    public AdaptadorMotoEnvios(MotoEnviosService servicio) {
        this.servicio = Objects.requireNonNull(servicio, "El servicio de MotoEnvios es obligatorio");
    }

    @Override
    public double calcularCosto(String origen, String destino, double peso) {
        try {
            return servicio.precio(origen, destino, peso);
        } catch (MotoEnviosException e) {
            throw new ProveedorLogisticoException("MotoEnvios no pudo cotizar: " + e.getMessage(), e);
        }
    }
}
