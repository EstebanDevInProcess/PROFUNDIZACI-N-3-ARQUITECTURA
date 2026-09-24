public class GeneradorComprobante {

    public void generar(String tipo, String contenido) {

        Comprobante comprobante;

        if (tipo.equalsIgnoreCase("PDF")) {
            comprobante = new ComprobantePDF();
        } else if (tipo.equalsIgnoreCase("HTML")) {
            comprobante = new ComprobanteHTML();
        } else {
            throw new IllegalArgumentException(
                "Tipo de comprobante no soportado"
            );
        }

        comprobante.generar(contenido);
    }
}
