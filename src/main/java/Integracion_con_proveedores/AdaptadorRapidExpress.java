package Integracion_con_proveedores;

import java.util.Objects;

/**
 * Patrón Adapter (de objetos) — rol ADAPTADOR.
 *
 * Objetivo (Target): {@link ServicioEnvio}, la interfaz que entiende la aplicación.
 * Adaptado (Adaptee): {@link RapidExpressAPI}, librería externa NO modificable.
 *
 * Concentra TODAS las particularidades de RapidExpress:
 *  - la ruta se envía como un solo texto "origen-destino";
 *  - el peso se envía en gramos enteros (se trunca, como en el código original).
 */
public class AdaptadorRapidExpress implements ServicioEnvio {

    private final RapidExpressAPI api;

    public AdaptadorRapidExpress(RapidExpressAPI api) {
        this.api = Objects.requireNonNull(api, "La API de RapidExpress es obligatoria");
    }

    /**
     * @param peso peso en kilogramos (unidad de la interfaz de la aplicación)
     */
    @Override
    public double calcularCosto(String origen, String destino, double peso) {
        String ruta = origen + "-" + destino;
        int gramos = (int) (peso * 1000);
        return api.getShippingPrice(ruta, gramos);
    }
}
