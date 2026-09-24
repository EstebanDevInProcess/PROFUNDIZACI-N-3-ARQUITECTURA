package Integracion_con_proveedores;
public class LogisticaServiceAjustado {

    private ServicioEnvio proveedorLocal;
    private RapidExpressAPI rapidExpress;
    private EnviosAndesAPI enviosAndes;

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

            String ruta =
                origen + "-" + destino;

            int gramos =
                (int) (pesoKg * 1000);

            return rapidExpress.getShippingPrice(
                ruta,
                gramos
            );
        }

        if (proveedor.equals("ANDES")) {

            double libras =
                pesoKg * 2.20462262185;

            long centavos = enviosAndes.consultarTarifa(
                origen,
                destino,
                libras
            );

            return centavos / 100.0;
        }

        throw new IllegalArgumentException();
    }
}