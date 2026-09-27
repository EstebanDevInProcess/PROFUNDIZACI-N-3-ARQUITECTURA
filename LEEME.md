# Taller R1-A2-S7 — Patrones GoF como soporte al diseño interno de componentes

Este repositorio reúne los **tres casos del taller**, cada uno en su propia carpeta y con los 8 productos del taller.
El historial Git de cada caso se conservó completo: sus commits, sus tags y su rama de comparación están en este
mismo repositorio, identificados con el prefijo `caso1-`, `caso2-` o `caso3-`.

| Caso | Carpeta | Patrón GoF | Pruebas (antes → después) |
|---|---|---|---|
| 1. Sistema multicanal de comprobantes (MercadoRegional) | `Caso1_Sistema_multicanal_FactoryMethod` | Factory Method (creacional) | 10 → 30 |
| 2. Integración con proveedores de logística (AgroConecta) | `Caso2_Integracion_proveedores_Adapter` | Adapter (estructural) | 13 → 25 |
| 3. Motor flexible de descuentos (TurismoCundinamarca) | `Caso3_Motor_descuentos_Strategy` | Strategy (comportamiento) | 11 → 33 |

## Dónde está cada producto (en cada caso)

| Producto del taller | Ubicación |
|---|---|
| Análisis inicial | `docs/INFORME.md` §1 |
| Diagnóstico de diseño | `docs/INFORME.md` §2 |
| Decisión fundamentada (principios + matriz + decisión) | `docs/INFORME.md` §3 y §4 |
| Diseño propuesto (UML) | `docs/INFORME.md` §5 y `docs/uml/*.puml` |
| Implementación (código + Git + README + commits) | `src/main/java`, `README.md`, `git log` |
| Validación (JUnit antes/después) | `src/test/java`, `docs/INFORME.md` §7, tags `casoN-v1-linea-base` y `casoN-v2-refactor` |
| Evaluación del impacto (tabla + nuevo requisito) | `docs/INFORME.md` §8, tag `casoN-v3-nuevo-requisito` y rama `casoN-demo-*-sin-patron` |
| Conexión arquitectónica | `docs/INFORME.md` §9 |

En los nombres de tags y ramas, `N` es el número del caso (1, 2 o 3).

## Tags y ramas

| Tag | Contenido |
|---|---|
| `casoN-v0-original` | Código entregado, sin modificaciones |
| `casoN-v1-linea-base` | Código original + pruebas de caracterización |
| `casoN-v2-refactor` | Diseño refactorizado con el patrón |
| `casoN-v3-nuevo-requisito` | Nuevo requisito implementado sobre el patrón |
| `casoN-final` | Último estado del caso (documentación incluida) |

| Rama | Contenido |
|---|---|
| `caso1-demo-xml-sin-patron` | Caso 1: nuevo requisito implementado sin el patrón, para comparar |
| `caso2-demo-andes-sin-patron` | Caso 2: nuevo requisito implementado sin el patrón, para comparar |
| `caso3-demo-aniversario-sin-patron` | Caso 3: nuevo requisito implementado sin el patrón, para comparar |

## Ejecutar

Requiere JDK 17+ y Maven. Desde la raíz, `mvn test` ejecuta las pruebas de los tres casos; también se puede
ejecutar `mvn test` dentro de la carpeta de un caso.

Para ver el antes y el después de un caso (ejemplo con el Caso 1), desde la raíz del repositorio:

    git checkout caso1-v1-linea-base
    mvn test
    git checkout caso1-v2-refactor
    mvn test
    git checkout main

**Importante:** al hacer checkout de un tag o una rama `casoN-...`, la raíz del repositorio muestra **solo ese caso**
tal como estaba en ese momento, con sus archivos directamente en la raíz. Por eso los comandos `git` que aparecen
en los README e INFORME de cada caso (con rutas como `src/main/...`) se ejecutan **desde la raíz**. Con
`git checkout main` se vuelve a la estructura con las tres carpetas.