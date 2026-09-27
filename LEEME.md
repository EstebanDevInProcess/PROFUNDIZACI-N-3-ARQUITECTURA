# Taller R1-A2-S7 — Patrones GoF como soporte al diseño interno de componentes

Cada carpeta es un **repositorio Git independiente** (con su historial, tags y rama de comparación) y contiene
los 8 productos del taller.

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
| Validación (JUnit antes/después) | `src/test/java`, `docs/INFORME.md` §7, tags `v1-linea-base` y `v2-refactor` |
| Evaluación del impacto (tabla + nuevo requisito) | `docs/INFORME.md` §8, tag `v3-nuevo-requisito` y rama `demo/*-sin-patron` |
| Conexión arquitectónica | `docs/INFORME.md` §9 |

## Ejecutar

En cada carpeta: `mvn test` (JDK 17+). Para ver el antes y el después: `git checkout v1-linea-base && mvn test`,
luego `git checkout main && mvn test`.
