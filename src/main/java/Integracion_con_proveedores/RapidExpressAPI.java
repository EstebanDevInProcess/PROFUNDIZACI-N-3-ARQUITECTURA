package Integracion_con_proveedores;
public class RapidExpressAPI {

    public double getShippingPrice(
            String route,
            int weightInGrams) {

        // Simulación de API externa
        return weightInGrams * 0.0025;
    }
}