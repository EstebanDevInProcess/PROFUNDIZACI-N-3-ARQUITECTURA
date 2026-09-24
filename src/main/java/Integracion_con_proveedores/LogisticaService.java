package Integracion_con_proveedores;
public class LogisticaService {

    private final ServicioEnvio servicioEnvio;

    public LogisticaService(
            ServicioEnvio servicioEnvio) {

        this.servicioEnvio = servicioEnvio;
    }

    public double cotizar(
            String origen,
            String destino,
            double peso) {

        return servicioEnvio.calcularCosto(
            origen,
            destino,
            peso
        );
    }
}