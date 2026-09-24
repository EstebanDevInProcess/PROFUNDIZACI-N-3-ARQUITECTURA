import java.util.Objects;

/**
 * Patrón Strategy — rol CONTEXTO.
 *
 * Es el componente que usa el proceso de compra. Selecciona la política activa
 * del catálogo y le delega el cálculo; no contiene ningún algoritmo de descuento
 * ni condicionales por tipo. Queda CERRADO a modificaciones cuando Mercadeo
 * incorpora, reemplaza, activa o desactiva políticas.
 */
public class CalculadorDescuento {

    private final CatalogoPoliticas catalogo;

    /** Constructor original: usa las políticas declaradas en la configuración. */
    public CalculadorDescuento() {
        this(ConfiguracionDescuentos.catalogoPorDefecto());
    }

    /** Inyección del catálogo: permite pruebas aisladas y cambios en caliente. */
    public CalculadorDescuento(CatalogoPoliticas catalogo) {
        this.catalogo = Objects.requireNonNull(catalogo, "El catálogo de políticas es obligatorio");
    }

    /** API original: descuento a partir del tipo y el valor de la compra. */
    public double calcular(
            String tipo,
            double valorCompra) {

        return calcular(tipo, Compra.deValor(valorCompra));
    }

    /**
     * Descuento con todos los datos de la compra (municipio, fecha).
     * Si no hay política activa para el tipo, el descuento es 0 (como el original).
     */
    public double calcular(String tipo, Compra compra) {
        return catalogo.buscarActiva(tipo)
            .map(politica -> politica.calcular(compra))
            .orElse(0.0);
    }
}
