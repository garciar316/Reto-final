# Reto-final

- Backend desplegado en heroku: https://questions-back-final.herokuapp.com/
- Frontend desplegado en firebase hosting: https://reto-final-a24b6.web.app/preguntas

## Puntos realizados:
- Consumo del back-end actual: se consume tanto los endpoints iniciales, como los agregados
- Logueo usuario/contraseña: usando firebase
- Autenticación de Gmail: usando firebase
- Restablecer contraseña: usando firebase
- Sistema de notificaciones por email
- Sistema para calificación de las respuestas: se califica al crear o editar, se muestran primero las que tienen mejor puntuación
- Editar respuestas: solo el dueño de la respuesta puede editar, también se pueden editar las preguntas con la misma condición
- Paginador de Preguntas: se usó el componente DataView de PrimeNG
- Uso adecuado de Observable en Angular: se quitan los any y se usa el pipe async
- Uso de algún frameworks o preprocesador CSS: se continúa con la implementación de primeNG
- Uso de Swagger: se documentan los endpoints, con springdoc-openapi-webflux-ui y la anotacion @RouterOperation

## Mirco servicio creado para envio de mensajes
- https://github.com/garciar316/email-sender
