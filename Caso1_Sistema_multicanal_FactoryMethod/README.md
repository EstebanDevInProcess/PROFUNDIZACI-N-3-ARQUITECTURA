# Sistema multicanal de generación de comprobantes — MercadoRegional

Refactorización del módulo de comprobantes aplicando el patrón **Factory Method** (GoF, creacional) para que
nuevos formatos (XML, JSON, aliados comerciales) se incorporen **sin modificar** el componente coordinador.

📄 **Informe completo** (análisis, diagnóstico, matriz de alternativas, UML, pruebas, impacto y arquitectura):
[`docs/INFORME.md`](docs/INFORME.md)

## Requisitos

- JDK 17 o superior
- Maven 3.8+ (o un IDE con soporte Maven: IntelliJ IDEA, Eclipse, VS Code)

## Ejecutar las pruebas

```bash
mvn test
```

- Resultados de JUnit 5: consola / `target/surefire-reports/`
- Cobertura (JaCoCo): `target/site/jacoco/index.html`

## Ver el antes y el después

Cada hito tiene un *tag* de Git:

| Tag | Estado | Pruebas |
|---|---|---|
| `v0-original` | Código entregado, sin modificaciones | — |
| `v1-linea-base` | Código original + 10 pruebas de caracterización | 10/10 |
| `v2-refactor` | Diseño refactorizado con Factory Method | 24/24 |
| `v3-nuevo-requisito` | Nuevos formatos XML, JSON y aliados | 30/30 |

```bash
git checkout v1-linea-base && mvn test     # ANTES: línea base sobre el código original
git checkout v2-refactor   && mvn test     # DESPUÉS: misma línea base + pruebas nuevas
git checkout main
```

La rama `demo/xml-sin-patron` agrega XML **sin** el patrón, solo para comparar el impacto del cambio:

```bash
git diff v1-linea-base demo/xml-sin-patron --stat   # modifica GeneradorComprobante
git diff v2-refactor  v3-nuevo-requisito --stat -- src/main   # GeneradorComprobante no aparece
```

## Estructura

```
src/main/java
├── GeneradorComprobante.java       Cliente: coordina la generación (API pública sin cambios)
├── RegistroCreadores.java          Selecciona el creador por tipo (reemplaza el if/else)
├── ConfiguracionComprobantes.java  Raíz de composición: formatos activos
├── Comprobante.java                Producto (interfaz)
├── Comprobante{PDF,HTML,XML,JSON,Aliado}.java   Productos concretos
├── CreadorComprobante.java         Creador: declara el factory method crearComprobante()
└── Creador{PDF,HTML,XML,JSON,Aliado}.java       Creadores concretos
src/test/java
├── CaracterizacionGeneradorComprobanteTest.java  Línea base (idéntica antes y después)
├── CreadoresComprobanteTest.java
├── RegistroCreadoresTest.java
├── GeneradorComprobanteAisladoTest.java          Coordinador con doble de prueba
├── NuevosFormatosXmlJsonTest.java                Nuevo requisito
└── FormatosAliadosTest.java                      Nuevo requisito
docs/
├── INFORME.md                      Entregable escrito
├── ENUNCIADO_README.md             README original de la actividad
└── uml/                            Diagramas PlantUML (original y propuesto)
```

## Uso

```java
GeneradorComprobante generador = new GeneradorComprobante();
generador.generar("PDF", "Compra #1001");   // Generando comprobante PDF: Compra #1001
generador.generar("json", "Compra #1002");  // Generando comprobante JSON: Compra #1002
```

## Cómo agregar un formato nuevo (3 pasos)

1. Crear el producto: `class ComprobanteCSV implements Comprobante { ... }`
2. Crear su creador: `class CreadorCSV extends CreadorComprobante { CreadorCSV() { super("CSV"); } ... }`
3. Registrarlo en `ConfiguracionComprobantes.registroPorDefecto()`: `.registrar(new CreadorCSV())`

`GeneradorComprobante` **no se modifica**. Agregue una prueba del nuevo formato.

## Diagramas

Los diagramas del informe están en Mermaid (GitHub los muestra automáticamente). Las versiones PlantUML
están en `docs/uml` y se pueden renderizar en https://www.plantuml.com/plantuml o con la extensión
PlantUML del IDE.
