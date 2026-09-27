package Integracion_con_proveedores;

import java.util.ArrayList;
import java.util.List;

/** Doble de prueba del proveedor LOCAL: registra las llamadas y devuelve un valor fijo. */
final class ServicioEnvioFalso implements ServicioEnvio {

    final List<String> llamadas = new ArrayList<>();
    private final double costo;

    ServicioEnvioFalso(double costo) {
        this.costo = costo;
    }

    @Override
    public double calcularCosto(String origen, String destino, double peso) {
        llamadas.add(origen + "|" + destino + "|" + peso);
        return costo;
    }
}
