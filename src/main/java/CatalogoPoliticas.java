import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Catálogo de políticas de descuento disponibles.
 *
 * No es un participante GoF: complementa a Strategy resolviendo la SELECCIÓN de
 * la estrategia por código y permitiendo, en tiempo de ejecución, lo que exige
 * Mercadeo: INCORPORAR, REEMPLAZAR, ACTIVAR y DESACTIVAR políticas sin modificar
 * el componente que calcula el descuento.
 *
 * Los códigos distinguen mayúsculas (igual que String.equals en el original).
 * Es seguro para uso concurrente.
 */
public class CatalogoPoliticas {

    private final Map<String, PoliticaDescuento> politicas = new ConcurrentHashMap<>();
    private final Set<String> inactivas = ConcurrentHashMap.newKeySet();

    /** Incorpora una política nueva (activa). */
    public CatalogoPoliticas registrar(PoliticaDescuento politica) {
        Objects.requireNonNull(politica, "La política es obligatoria");
        if (politicas.putIfAbsent(politica.codigo(), politica) != null) {
            throw new IllegalStateException(
                "Ya existe una política con el código " + politica.codigo() + "; use reemplazar()");
        }
        return this;
    }

    /** Reemplaza el algoritmo de una política existente, conservando su estado de activación. */
    public CatalogoPoliticas reemplazar(PoliticaDescuento politica) {
        Objects.requireNonNull(politica, "La política es obligatoria");
        if (politicas.replace(politica.codigo(), politica) == null) {
            throw new IllegalStateException("No existe una política con el código " + politica.codigo());
        }
        return this;
    }

    public CatalogoPoliticas desactivar(String codigo) {
        inactivas.add(existente(codigo));
        return this;
    }

    public CatalogoPoliticas activar(String codigo) {
        inactivas.remove(existente(codigo));
        return this;
    }

    /**
     * Política ACTIVA para el código, si existe.
     *
     * @throws NullPointerException si el código es null (igual que el original)
     */
    public Optional<PoliticaDescuento> buscarActiva(String codigo) {
        Objects.requireNonNull(codigo, "El tipo de descuento es obligatorio");
        if (inactivas.contains(codigo)) {
            return Optional.empty();
        }
        return Optional.ofNullable(politicas.get(codigo));
    }

    /** Códigos de las políticas activas, en orden alfabético. */
    public Set<String> codigosActivos() {
        Set<String> activos = new TreeSet<>(politicas.keySet());
        activos.removeAll(inactivas);
        return Collections.unmodifiableSet(activos);
    }

    private String existente(String codigo) {
        Objects.requireNonNull(codigo, "El código es obligatorio");
        if (!politicas.containsKey(codigo)) {
            throw new IllegalStateException("No existe una política con el código " + codigo);
        }
        return codigo;
    }
}
