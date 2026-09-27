package Integracion_con_proveedores;

import java.util.ArrayList;
import java.util.List;

/**
 * Espía de la librería externa: NO la modifica; la extiende solo en pruebas para
 * registrar los argumentos exactos que recibe y delega el cálculo real.
 */
final class RapidExpressEspia extends RapidExpressAPI {

    final List<String> rutas = new ArrayList<>();
    final List<Integer> gramos = new ArrayList<>();

    @Override
    public double getShippingPrice(String route, int weightInGrams) {
        rutas.add(route);
        gramos.add(weightInGrams);
        return super.getShippingPrice(route, weightInGrams);
    }
}
