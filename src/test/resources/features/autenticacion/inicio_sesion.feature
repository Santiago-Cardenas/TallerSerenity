# language: es
@HU-01 @autenticacion
Característica: Inicio de sesión en La Botica del Alquimista
  Como cliente de la botica
  Quiero ingresar con mi usuario y contraseña
  Para consultar el catálogo y hacer pedidos

  Antecedentes:
    Dado que Merlín está en la página de inicio de sesión

  @Issue-1 @smoke @positivo
  Escenario: Ingresar con credenciales válidas
    Cuando Merlín inicia sesión con el usuario "alquimista" y la contraseña "pocion123"
    Entonces Merlín debería ver el catálogo de productos
    Y Merlín debería ver el saludo "Hola, alquimista"

  @Issue-2 @negativo
  Esquema del escenario: Rechazar el ingreso con credenciales inválidas: <caso>
    Cuando Merlín inicia sesión con el usuario "<usuario>" y la contraseña "<contrasena>"
    Entonces Merlín debería ver el error de inicio de sesión "Usuario o contraseña incorrectos"
    Y Merlín debería permanecer en la página de inicio de sesión

    Ejemplos:
      | caso                   | usuario    | contrasena |
      | contraseña incorrecta  | alquimista | pocion999  |
      | usuario inexistente    | hechicero  | pocion123  |
      | usuario en mayúsculas  | ALQUIMISTA | pocion123  |
      | campos vacíos          |            |            |
