public class GeneradorComprobante {

    public void generar(String tipo, String contenido) {

        CreadorComprobante creador;

        if (tipo.equalsIgnoreCase("PDF")) {
            creador = new CreadorPDF();
        } else if (tipo.equalsIgnoreCase("HTML")) {
            creador = new CreadorHTML();
        } else {
            throw new IllegalArgumentException(
                "Tipo de comprobante no soportado"
            );
        }

        creador.emitir(contenido);
    }
}
