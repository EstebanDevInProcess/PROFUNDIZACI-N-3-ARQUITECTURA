/**
 * Patrón Factory Method — rol CREADOR CONCRETO para HTML.
 */
public class CreadorHTML extends CreadorComprobante {

    public CreadorHTML() {
        super("HTML");
    }

    @Override
    protected Comprobante crearComprobante() {
        return new ComprobanteHTML();
    }
}
