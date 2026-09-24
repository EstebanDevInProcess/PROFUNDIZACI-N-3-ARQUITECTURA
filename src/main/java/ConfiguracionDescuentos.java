/**
 * Raíz de composición: ÚNICO lugar donde se declaran las políticas vigentes.
 * Incorporar una política = una línea aquí (o una llamada a registrar() en
 * tiempo de ejecución). CalculadorDescuento no se modifica.
 */
public final class ConfiguracionDescuentos {

    private ConfiguracionDescuentos() {
        // Clase utilitaria: no se instancia.
    }

    public static CatalogoPoliticas catalogoPorDefecto() {
        return new CatalogoPoliticas()
            .registrar(new DescuentoPorcentual("FRECUENTE", 0.10))
            .registrar(new DescuentoPorcentual("TEMPORADA_BAJA", 0.15))
            .registrar(new DescuentoPorcentual("CONVENIO", 0.20));
    }
}
