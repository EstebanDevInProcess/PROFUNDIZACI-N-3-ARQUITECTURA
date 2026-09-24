/**
 * Patrón Factory Method — rol CREADOR CONCRETO para JSON.
 */
public class CreadorJSON extends CreadorComprobante {

    public CreadorJSON() {
        super("JSON");
    }

    @Override
    protected Comprobante crearComprobante() {
        return new ComprobanteJSON();
    }
}
