/**
 * Patrón Factory Method — rol CREADOR CONCRETO para XML.
 */
public class CreadorXML extends CreadorComprobante {

    public CreadorXML() {
        super("XML");
    }

    @Override
    protected Comprobante crearComprobante() {
        return new ComprobanteXML();
    }
}
