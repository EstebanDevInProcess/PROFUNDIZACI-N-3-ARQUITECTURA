public class CalculadorDescuento {

    private final PoliticaDescuento frecuente = new DescuentoPorcentual("FRECUENTE", 0.10);
    private final PoliticaDescuento temporadaBaja = new DescuentoPorcentual("TEMPORADA_BAJA", 0.15);
    private final PoliticaDescuento convenio = new DescuentoPorcentual("CONVENIO", 0.20);

    public double calcular(
            String tipo,
            double valorCompra) {

        PoliticaDescuento politica;

        if (tipo.equals("FRECUENTE")) {
            politica = frecuente;
        } else if (tipo.equals("TEMPORADA_BAJA")) {
            politica = temporadaBaja;
        } else if (tipo.equals("CONVENIO")) {
            politica = convenio;
        } else {
            return 0;
        }

        return politica.calcular(Compra.deValor(valorCompra));
    }
}
