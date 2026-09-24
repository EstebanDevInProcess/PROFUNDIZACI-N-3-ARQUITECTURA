import java.util.ArrayList;
import java.util.List;

/** Doble de prueba: política que devuelve un valor fijo y registra las compras recibidas. */
final class PoliticaFija implements PoliticaDescuento {

    final List<Compra> recibidas = new ArrayList<>();
    private final String codigo;
    private final double descuento;

    PoliticaFija(String codigo, double descuento) {
        this.codigo = codigo;
        this.descuento = descuento;
    }

    @Override
    public String codigo() {
        return codigo;
    }

    @Override
    public double calcular(Compra compra) {
        recibidas.add(compra);
        return descuento;
    }
}
