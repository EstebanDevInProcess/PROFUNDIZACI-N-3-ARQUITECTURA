import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Strategy: DescuentoPorcentual")
class DescuentoPorcentualTest {

    @Test
    @DisplayName("Calcula valor × porcentaje y expone su código")
    void calcula() {
        DescuentoPorcentual politica = new DescuentoPorcentual("X", 0.10);
        assertEquals(5_000.0, politica.calcular(Compra.deValor(50_000)), 1e-9);
        assertEquals("X", politica.codigo());
    }

    @Test
    @DisplayName("Rechaza porcentajes fuera de [0, 1] (evita confundir 10 con 0.10)")
    void validaPorcentaje() {
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentual("X", 10));
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentual("X", -0.1));
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentual("X", Double.NaN));
    }

    @Test
    @DisplayName("Rechaza un código vacío")
    void validaCodigo() {
        assertThrows(IllegalArgumentException.class, () -> new DescuentoPorcentual(" ", 0.1));
    }
}
