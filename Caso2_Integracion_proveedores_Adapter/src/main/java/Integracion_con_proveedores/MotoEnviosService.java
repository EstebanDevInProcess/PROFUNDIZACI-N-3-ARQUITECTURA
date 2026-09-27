package Integracion_con_proveedores;

/**
 * LIBRERÍA EXTERNA SIMULADA (no modificable) — operador MotoEnvios.
 * Incompatibilidad: lanza una excepción CHEQUEADA propia cuando no puede cotizar.
 */
public class MotoEnviosService {

    public static final double CAPACIDAD_MAXIMA_KG = 20.0;

    public double precio(String origen, String destino, double pesoKg) throws MotoEnviosException {
        if (pesoKg > CAPACIDAD_MAXIMA_KG) {
            throw new MotoEnviosException("Excede la capacidad de carga de la moto");
        }
        // Simulación: tarifa fija de 4 más 1 por kg
        return 4.0 + pesoKg;
    }
}
