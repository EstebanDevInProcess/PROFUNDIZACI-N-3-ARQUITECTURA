package Integracion_con_proveedores;
public interface ServicioEnvio {

    double calcularCosto(
        String origen,
        String destino,
        double peso
    );

}