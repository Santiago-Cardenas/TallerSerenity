# language: es
@pago
Característica: Pago y confirmación del pedido
  Como cliente de la botica
  Quiero diligenciar mis datos de envío, elegir el método de envío y confirmar el pedido
  Para recibir mis productos y conocer el valor exacto que voy a pagar

  # Reglas: IVA del 19 % sobre el subtotal; envío normal $8.000 (gratis desde $150.000 después del descuento);
  # envío express $15.000. Todos los campos son obligatorios, el correo debe ser válido y el teléfono de 10 dígitos.

  @HU-06 @Issue-18 @positivo
  Esquema del escenario: Calcular el total a pagar con envío <envio> para un subtotal de <subtotal>
    Dado que Merlín va a pagar <cantidad> unidades de "<producto>"
    Cuando Merlín elige el envío "<envio>"
    Entonces el resumen del pedido debería mostrar subtotal "<subtotal>", IVA "<iva>", envío "<costo_envio>" y total "<total>"

    Ejemplos:
      | producto                 | cantidad | envio   | subtotal | iva     | costo_envio | total    |
      | Poción de Sueño Profundo | 2        | normal  | $37.000  | $7.030  | $8.000      | $52.030  |
      | Amuleto de Protección    | 5        | normal  | $175.000 | $33.250 | Gratis      | $208.250 |
      | Poción de Sueño Profundo | 2        | express | $37.000  | $7.030  | $15.000     | $59.030  |

  @HU-06 @Issue-19 @negativo
  Esquema del escenario: Impedir confirmar el pedido con datos de envío inválidos: <caso>
    Dado que Merlín va a pagar 1 unidad de "Polvo de Luna"
    Cuando Merlín confirma el pedido con el campo "<campo>" en "<valor>"
    Entonces Merlín debería ver el mensaje de pago "Revisa los campos marcados"
    Y el campo "<campo>" debería indicar el error "<error>"

    Ejemplos:
      | caso                  | campo    | valor         | error                             |
      | nombre vacío          | nombre   |               | Este campo es obligatorio         |
      | correo sin dominio    | correo   | merlin@botica | Ingresa un correo válido          |
      | teléfono de 9 dígitos | telefono | 300123456     | El teléfono debe tener 10 dígitos |
      | teléfono con letras   | telefono | 300ABC4567    | El teléfono debe tener 10 dígitos |

  @HU-07 @Issue-20 @smoke @positivo
  Escenario: Confirmar un pedido válido y descontar el stock vendido
    Dado que Merlín va a pagar 2 unidades de "Poción de Invisibilidad"
    Y Merlín elige el envío "express"
    Cuando Merlín confirma el pedido con datos de envío válidos
    Entonces Merlín debería ver la confirmación del pedido con un número válido
    Y el total pagado debería ser "$169.700"
    Y el carrito de Merlín debería tener 0 unidades
    Y Merlín debería ver 0 unidades disponibles de "Poción de Invisibilidad" en el catálogo

  # HU Funcionalidad 3, criterio 5: el IVA se calcula sobre el subtotal DESPUÉS del descuento
  @HU-06 @Issue-22 @bug @positivo
  Escenario: Calcular el IVA sobre el subtotal con descuento
    Dado que Merlín tiene 2 unidades de "Poción de Sueño Profundo" en el carrito
    Y Merlín aplicó el cupón "LUNA10"
    Cuando Merlín procede al pago
    Entonces el resumen del pedido debería mostrar subtotal "$37.000", IVA "$6.327", envío "$8.000" y total "$47.627"
