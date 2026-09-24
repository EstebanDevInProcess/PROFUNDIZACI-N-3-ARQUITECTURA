/**
 * Patrón Strategy — rol ESTRATEGIA.
 *
 * Familia de algoritmos intercambiables de descuento. Cada política conoce su
 * código y calcula el VALOR del descuento para una compra.
 */
public interface PoliticaDescuento {

    /** Código con el que se selecciona la política (p. ej. "FRECUENTE"). */
    String codigo();

    /** Valor del descuento (no el precio final). */
    double calcular(Compra compra);
}
