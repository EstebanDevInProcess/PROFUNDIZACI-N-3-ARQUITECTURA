/**
 * Raíz de composición del módulo de comprobantes.
 *
 * ÚNICO punto donde se declara qué formatos están activos. Incorporar un
 * formato nuevo implica crear su producto y su creador y agregar una línea
 * aquí; GeneradorComprobante no se modifica.
 */
public final class ConfiguracionComprobantes {

    private ConfiguracionComprobantes() {
        // Clase utilitaria: no se instancia.
    }

    public static RegistroCreadores registroPorDefecto() {
        return new RegistroCreadores()
            .registrar(new CreadorPDF())
            .registrar(new CreadorHTML())
            .registrar(new CreadorXML())
            .registrar(new CreadorJSON());
    }
}
