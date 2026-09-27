# Motor flexible de asignación de descuentos — TurismoCundinamarca

Refactorización del cálculo de descuentos aplicando el patrón **Strategy** (GoF, comportamiento) para que las
políticas se **incorporen, reemplacen, activen o desactiven** sin modificar el componente que usa el proceso de compra.

📄 **Informe completo:** [`docs/INFORME.md`](docs/INFORME.md)

## Requisitos

- JDK 17 o superior
- Maven 3.8+ (o IDE con soporte Maven)

## Ejecutar las pruebas

```bash
mvn test
```

Cobertura (JaCoCo): `target/site/jacoco/index.html`

## Antes y después

| Tag | Estado | Pruebas |
|---|---|---|
| `v0-original` | Código del enunciado, sin modificaciones | — |
| `v1-linea-base` | Original + 11 pruebas de caracterización | 11/11 |
| `v2-refactor` | Strategy + catálogo de políticas | 26/26 |
| `v3-nuevo-requisito` | Cinco políticas nuevas de Mercadeo | 33/33 |

Rama de comparación `demo/aniversario-sin-patron`: una política agregada **sin** el patrón.

## Estructura

| Rol | Clases |
|---|---|
| Context | `CalculadorDescuento` (API original conservada) |
| Strategy | `PoliticaDescuento` |
| ConcreteStrategy | `DescuentoPorcentual`, `PromocionTemporal`, `DescuentoPorMunicipio`, `DescuentoConTope` |
| Selección y gestión | `CatalogoPoliticas` |
| Objeto parámetro | `Compra` (valor, municipio, fecha) |
| Raíz de composición | `ConfiguracionDescuentos` |

## Uso

```java
CatalogoPoliticas catalogo = ConfiguracionDescuentos.catalogoPorDefecto();
CalculadorDescuento calculador = new CalculadorDescuento(catalogo);

calculador.calcular("FRECUENTE", 100_000);                                          // 10000.0
calculador.calcular("PROMO_REGIONAL_SUMAPAZ", new Compra(100_000, "Fusagasugá", null)); // 12000.0

catalogo.reemplazar(new DescuentoPorcentual("CONVENIO", 0.22)); // cambiar un porcentaje
catalogo.desactivar("TEMPORADA_BAJA");                           // suspender una campaña
```

## Cómo agregar una política

- **Mismo algoritmo, otros parámetros** (p. ej. otro porcentaje o vigencia): una línea en `ConfiguracionDescuentos`.
- **Algoritmo nuevo**: una clase `implements PoliticaDescuento` + una línea de registro.

`CalculadorDescuento` **no se modifica**. Agregue pruebas de la política.

## Diagramas

Mermaid en el informe (GitHub los muestra). PlantUML en `docs/uml`.
