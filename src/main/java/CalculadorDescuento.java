public class CalculadorDescuento {

    public double calcular(
            String tipo,
            double valorCompra) {

        if (tipo.equals("FRECUENTE")) {

            return valorCompra * 0.10;

        } else if (tipo.equals("TEMPORADA_BAJA")) {

            return valorCompra * 0.15;

        } else if (tipo.equals("CONVENIO")) {

            return valorCompra * 0.20;

        } else if (tipo.equals("ANIVERSARIO")) {

            return valorCompra * 0.25;

        }

        return 0;
    }
}
