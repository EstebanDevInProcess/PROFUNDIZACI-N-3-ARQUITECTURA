public class Main {
    public static void main(String[] args) {
        // 1. Instanciar el generador (carga por defecto PDF, HTML, XML, JSON y Aliados)
        GeneradorComprobante generador = new GeneradorComprobante();

        // 2. Generar comprobantes en distintos formatos
        System.out.println("--- Generando comprobantes ---");
        generador.generar("PDF", "Compra #1001 - Total: $150.000");
        generador.generar("JSON", "Compra #1002 - Total: $85.000");
        generador.generar("XML", "Compra #1003 - Total: $320.000");
        generador.generar("HTML", "Compra #1004 - Total: $45.000");
        generador.generar("ALIADO_ANDINO", "Compra #1005 - Total: $90.000");
    }
}