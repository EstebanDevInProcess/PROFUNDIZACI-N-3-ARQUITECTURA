# Integración con proveedores externos de logística — AgroConecta

Refactorización del servicio de cotización aplicando el patrón **Adapter** (GoF, estructural) para integrar
proveedores con APIs incompatibles **sin contaminar la lógica principal** con los detalles de cada API.

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
| `v0-original` | Código entregado, sin modificaciones | — |
| `v1-linea-base` | Original + pruebas de caracterización (incluye defecto documentado) | 13/13 |
| `v2-refactor` | Adapter + inyección de proveedores | 19/19 |
| `v3-nuevo-requisito` | Tres operadores adicionales | 25/25 |

```bash
git checkout v1-linea-base && mvn test
git checkout v3-nuevo-requisito && mvn test
git checkout main
```

Rama de comparación `demo/andes-sin-patron`: el mismo operador agregado **sin** el patrón.

```bash
git diff v1-linea-base demo/andes-sin-patron --stat
```

## Estructura (paquete `Integracion_con_proveedores`)

| Rol | Clases |
|---|---|
| Target (contrato de la aplicación) | `ServicioEnvio` |
| Client | `LogisticaServiceAjustado` (multiproveedor), `LogisticaService` (original, sin cambios) |
| Adapter | `AdaptadorRapidExpress`, `AdaptadorEnviosAndes`, `AdaptadorCargaExpress`, `AdaptadorMotoEnvios` |
| Adaptee (librerías externas, no modificables) | `RapidExpressAPI`, `EnviosAndesAPI`, `CargaExpressClient`, `MotoEnviosService` (+ `MotoEnviosException`) |
| Raíz de composición | `ConfiguracionLogistica` |
| Excepción de la aplicación | `ProveedorLogisticoException` |

`EnviosAndesAPI`, `CargaExpressClient` y `MotoEnviosService` simulan los tres operadores anunciados en el caso;
cada uno presenta un tipo distinto de incompatibilidad (unidades, formato de respuesta, excepciones).

## Uso

```java
LogisticaServiceAjustado servicio =
    ConfiguracionLogistica.crearServicio(proveedorLocal, new RapidExpressAPI());

servicio.cotizar("RAPID", "Fusagasugá", "Bogotá", 2.0);   // 5.0
servicio.cotizar("ANDES", "Fusagasugá", "Bogotá", 10.0);  // 33.07
```

## Cómo integrar un operador nuevo (3 pasos)

1. Recibir su librería tal cual (no se modifica).
2. Crear `AdaptadorX implements ServicioEnvio` que traduzca parámetros, respuesta y excepciones.
3. Registrarlo en `ConfiguracionLogistica.crearServicio(...)`: `proveedores.put("X", new AdaptadorX(new XApi()));`

`LogisticaServiceAjustado` **no se modifica**. Agregue pruebas del adaptador.

## Diagramas

Mermaid en el informe (GitHub los muestra). PlantUML en `docs/uml`.
