/** Validaciones comunes de las políticas de descuento. */
final class Validaciones {

    private Validaciones() {
    }

    static String codigo(String codigo) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código de la política es obligatorio");
        }
        return codigo;
    }

    static double porcentaje(double porcentaje) {
        if (Double.isNaN(porcentaje) || porcentaje < 0 || porcentaje > 1) {
            throw new IllegalArgumentException(
                "El porcentaje debe estar entre 0 y 1 (0.10 = 10 %): " + porcentaje);
        }
        return porcentaje;
    }
}
