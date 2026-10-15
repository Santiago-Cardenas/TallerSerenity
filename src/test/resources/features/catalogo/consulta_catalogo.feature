# language: es
@HU-02 @catalogo
Característica: Consulta del catálogo de productos
  Como cliente de la botica
  Quiero buscar y filtrar los productos del catálogo
  Para encontrar rápidamente las pociones, ingredientes y amuletos que necesito

  Antecedentes:
    Dado que Merlín inició sesión en la botica

  @Issue-3 @positivo
  Esquema del escenario: Filtrar el catálogo por la categoría <categoria>
    Cuando Merlín filtra el catálogo por la categoría "<categoria>"
    Entonces Merlín debería ver los productos "<productos>"

    Ejemplos:
      | categoria    | productos                                                              |
      | Pociones     | Poción de Sueño Profundo, Elixir de Vitalidad, Poción de Invisibilidad |
      | Ingredientes | Raíz de Mandrágora, Polvo de Luna, Pluma de Fénix                      |
      | Amuletos     | Amuleto de Protección, Anillo de la Suerte, Colgante Lunar             |

  @Issue-4 @negativo
  Escenario: Informar cuando ningún producto coincide con la búsqueda
    Cuando Merlín busca productos con el texto "dragón"
    Entonces Merlín debería ver el aviso "No se encontraron productos."
    Y Merlín no debería ver productos en el catálogo
