package Integracion_con_proveedores;
public class LogisticaServiceAjustado {

    private ServicioEnvio proveedorLocal;
    private RapidExpressAPI rapidExpress;

    public double cotizar(
            String proveedor,
            String origen,
            String destino,
            double pesoKg) {

        if (proveedor.equals("LOCAL")) {

            return proveedorLocal.calcularCosto(
                origen,
                destino,
                pesoKg
            );

        }

        if (proveedor.equals("RAPID")) {

            return new AdaptadorRapidExpress(rapidExpress).calcularCosto(
                origen,
                destino,
                pesoKg
            );
        }

        throw new IllegalArgumentException();
    }
}