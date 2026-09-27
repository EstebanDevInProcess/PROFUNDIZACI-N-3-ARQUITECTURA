package Integracion_con_proveedores;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Adapter: AdaptadorRapidExpress traduce ServicioEnvio → RapidExpressAPI")
class AdaptadorRapidExpressTest {

    @Test
    @DisplayName("Traduce origen/destino a ruta y kg a gramos, y devuelve el precio de la API")
    void traduceLaLlamada() {
        RapidExpressEspia api = new RapidExpressEspia();
        ServicioEnvio adaptador = new AdaptadorRapidExpress(api);

        assertEquals(7.5, adaptador.calcularCosto("Girardot", "Fusagasugá", 3.0), 1e-9);
        assertEquals(List.of("Girardot-Fusagasugá"), api.rutas);
        assertEquals(List.of(3000), api.gramos);
    }

    @Test
    @DisplayName("Permite usar RapidExpress con el LogisticaService ORIGINAL sin modificarlo")
    void funcionaConElClienteOriginal() {
        LogisticaService original = new LogisticaService(new AdaptadorRapidExpress(new RapidExpressAPI()));
        assertEquals(2.5, original.cotizar("A", "B", 1.0), 1e-9);
    }

    @Test
    @DisplayName("Exige la API adaptada")
    void apiObligatoria() {
        assertThrows(NullPointerException.class, () -> new AdaptadorRapidExpress(null));
    }
}
