import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

/**
 * Registro de creadores disponibles, indexados por tipo de comprobante.
 *
 * No es un participante GoF: complementa al Factory Method resolviendo la
 * SELECCIÓN del creador en tiempo de ejecución a partir del texto "tipo",
 * reemplazando la cadena if/else del diseño original.
 *
 * La búsqueda usa String.CASE_INSENSITIVE_ORDER, que compara igual que
 * equalsIgnoreCase: se conserva exactamente el comportamiento original.
 */
public class RegistroCreadores {

    private final Map<String, CreadorComprobante> creadores =
        new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    /**
     * Registra un creador. Devuelve el propio registro para encadenar llamadas.
     *
     * @throws IllegalStateException si ya existe un creador para ese tipo
     *                               (evita reemplazos silenciosos)
     */
    public RegistroCreadores registrar(CreadorComprobante creador) {
        Objects.requireNonNull(creador, "El creador es obligatorio");
        if (creadores.containsKey(creador.getTipo())) {
            throw new IllegalStateException(
                "Ya existe un creador registrado para el tipo " + creador.getTipo()
            );
        }
        creadores.put(creador.getTipo(), creador);
        return this;
    }

    /**
     * Busca el creador del tipo indicado, sin distinguir mayúsculas.
     *
     * @throws NullPointerException si el tipo es null (igual que el código original)
     */
    public Optional<CreadorComprobante> buscar(String tipo) {
        Objects.requireNonNull(tipo, "El tipo de comprobante es obligatorio");
        return Optional.ofNullable(creadores.get(tipo));
    }

    /** Tipos registrados, en orden alfabético (solo lectura). */
    public Set<String> tiposSoportados() {
        return Collections.unmodifiableSet(creadores.keySet());
    }
}
