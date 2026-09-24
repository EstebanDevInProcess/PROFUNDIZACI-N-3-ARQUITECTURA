import java.util.Objects;

/**
 * Patrón Factory Method — rol CLIENTE (coordinador de la generación).
 *
 * Conserva la API pública original: generar(tipo, contenido). Ya no conoce
 * formatos concretos ni contiene condicionales por tipo: obtiene el creador
 * del registro y le delega la emisión. Queda CERRADO a modificaciones cuando
 * se agregan nuevos formatos.
 */
public class GeneradorComprobante {

    private final RegistroCreadores registro;

    /** Constructor original: usa los formatos declarados en la configuración. */
    public GeneradorComprobante() {
        this(ConfiguracionComprobantes.registroPorDefecto());
    }

    /** Inyección de dependencias: permite pruebas aisladas y configuraciones distintas. */
    public GeneradorComprobante(RegistroCreadores registro) {
        this.registro = Objects.requireNonNull(registro, "El registro de creadores es obligatorio");
    }

    public void generar(String tipo, String contenido) {
        CreadorComprobante creador = registro.buscar(tipo)
            .orElseThrow(() -> new IllegalArgumentException(
                "Tipo de comprobante no soportado"
            ));

        creador.emitir(contenido);
    }
}
