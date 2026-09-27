import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("CalculadorDescuento (Contexto) aislado y gestión de políticas en caliente")
class CalculadorDescuentoAisladoTest {

    @Test
    @DisplayName("Delega en la política activa y le entrega la compra completa")
    void delegaEnLaPolitica() {
        PoliticaFija fija = new PoliticaFija("PRUEBA", 777);
        CalculadorDescuento calculador = new CalculadorDescuento(new CatalogoPoliticas().registrar(fija));
        Compra compra = new Compra(1000, "Fusagasugá", LocalDate.of(2026, 10, 5));

        assertEquals(777.0, calculador.calcular("PRUEBA", compra), 1e-9);
        assertEquals(List.of(compra), fija.recibidas);
    }

    @Test
    @DisplayName("Mercadeo reemplaza un porcentaje sin modificar el calculador")
    void reemplazoEnCaliente() {
        CatalogoPoliticas catalogo = ConfiguracionDescuentos.catalogoPorDefecto();
        CalculadorDescuento calculador = new CalculadorDescuento(catalogo);

        catalogo.reemplazar(new DescuentoPorcentual("CONVENIO", 0.22));

        assertEquals(22_000.0, calculador.calcular("CONVENIO", 100_000), 1e-9);
    }

    @Test
    @DisplayName("Una política desactivada no aplica descuento hasta reactivarse")
    void desactivacionEnCaliente() {
        CatalogoPoliticas catalogo = ConfiguracionDescuentos.catalogoPorDefecto();
        CalculadorDescuento calculador = new CalculadorDescuento(catalogo);

        catalogo.desactivar("TEMPORADA_BAJA");
        assertEquals(0.0, calculador.calcular("TEMPORADA_BAJA", 100_000), 1e-9);

        catalogo.activar("TEMPORADA_BAJA");
        assertEquals(15_000.0, calculador.calcular("TEMPORADA_BAJA", 100_000), 1e-9);
    }

    @Test
    @DisplayName("No acepta un catálogo null")
    void catalogoObligatorio() {
        assertThrows(NullPointerException.class, () -> new CalculadorDescuento(null));
    }
}
