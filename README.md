# Servicio de bono monedero

El ejercicio consiste en construir una prueba de concepto de un servicio de bono monedero.
El bono monedero funciona como un monedero "real":
- Almacena un saldo en euros, que el usuario puede utilizar para pagar otros servicios.
- El usuario puede recargar dinero desde una pasarela de pagos de terceros (stripe, paypal, redsys...).
- No existe la posibilidad de devolver ese dinero al medio de pago original.

La estructura básica del monedero es su identificador y su saldo actual. Si consideras que necesitas más campos,
añádelos sin problemas y lo discutiremos en la entrevista.

El ejercicio consiste en que programes endpoints para:
- Consultar un bono por su identificador.
- Descontar saldo del monedero (un cobro).
- Recargar dinero en ese bono a través de un servicio de pago de terceros.

Para que puedas ir al grano, te damos un proyecto maven con una aplicación Spring Boot, con las dependencias básicas y una
base de datos H2. Tienes perfiles de develop y test.

Tienes también una implementación del servicio que implementaría la pasarela de pago real (ThirdPartyPaymentService).
Esa parte no tienes que programarla, asume que el servicio hace la llamada remota dada una cantidad de dinero.
Está pensado para que devuelva error bajo ciertas condiciones.

Ten en cuenta que es un servicio que conviviría en un entorno de microservicios y alta concurrencia.

Le puedes dedicar el tiempo que quieras, pero hemos estimado que 3-4 horas es suficiente para demostrar  [los requisitos del puesto.](OFERTA.md#requisitos)
 No hace falta que lo documentes pero puedes anotar todo lo que quieras como explicación o justificación de lo que hagas o dejes sin hacer.
 
*DEVELOPMENT NOTES*

This development repository was created for the technical test for Juanjo Acosta and it was forked from https://github.com/syltek/technical-test-backend/tree/app/exercise-1.

You could also check the API on swagger: http://127.0.0.1:8090/swagger-ui.html#/wallet-controller