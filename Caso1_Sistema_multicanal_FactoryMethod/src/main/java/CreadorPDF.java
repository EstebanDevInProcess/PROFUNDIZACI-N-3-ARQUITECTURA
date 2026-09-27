/**
 * Patrón Factory Method — rol CREADOR CONCRETO para PDF.
 */
public class CreadorPDF extends CreadorComprobante {

    public CreadorPDF() {
        super("PDF");
    }

    @Override
    protected Comprobante crearComprobante() {
        return new ComprobantePDF();
    }
}
