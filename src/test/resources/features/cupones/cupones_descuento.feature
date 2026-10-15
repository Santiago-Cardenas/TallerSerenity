# language: es
@HU-05 @cupones
Característica: Cupones de descuento
  Como cliente de la botica
  Quiero aplicar un cupón de descuento a mi compra
  Para pagar menos cuando cumplo las condiciones de la promoción

  # Cupones vigentes: LUNA10 (10 %, sin mínimo) y ELIXIR20 (20 %, compra mínima de $100.000).
  # Solo se permite un cupón por compra.

  @Issue-9 @positivo
  Esquema del escenario: Aplicar el cupón <cupon> a una compra que cumple sus condiciones
    Dado que Merlín tiene <cantidad> unidades de "<producto>" en el carrito
    Cuando Merlín aplica el cupón "<cupon>"
    Entonces Merlín debería ver el mensaje del cupón "Cupón <cupon> aplicado correctamente"
    Y el cupón aplicado debería ser "<cupon>"
    Y el descuento debería ser "<descuento>"
    Y el total parcial debería ser "<total_parcial>"

    Ejemplos:
      | cupon    | cantidad | producto                 | descuento | total_parcial |
      | LUNA10   | 2        | Poción de Sueño Profundo | -$3.700   | $33.300       |
      | ELIXIR20 | 3        | Elixir de Vitalidad      | -$25.200  | $100.800      |

  @Issue-10 @negativo
  Esquema del escenario: Rechazar el cupón "<cupon>": <caso>
    Dado que Merlín tiene 2 unidades de "Elixir de Vitalidad" en el carrito
    Cuando Merlín aplica el cupón "<cupon>"
    Entonces Merlín debería ver el mensaje del cupón "<mensaje>"
    Y el total parcial debería ser "$84.000"

    Ejemplos:
      | caso                   | cupon    | mensaje                                           |
      | código inexistente     | DRAGON50 | Cupón no válido                                   |
      | compra menor al mínimo | ELIXIR20 | Este cupón requiere una compra mínima de $100.000 |
      | código vacío           |          | Ingresa un código de cupón                        |
