# Practica integradora del Grupo1

> [!Warning]
> Desplegar en ubuntu, necesario tener instalado docker (por medio del comando $sudo snap install docker)

## Pasos a seguir:
1. Clonar el repositorio
2. Dentro del repositorio abrir un terminal
3. Escribir docker compose up -d --build

> [!Important]
> ### Funcionalidades
> > #### Vue/Mongo
> > Por la parte de Vue podemos ver los productos que tiene la base de datos, url: http://172.19.0.4/
> > 
> > Por la parte de mongo, tenemos un controlador funcional en el que podemos
> > * Muestra la lista completa de productos
> > * Borrar un producto en concreto
> > * Crear un producto http://172.19.0.4/#/producto/nuevo
> >
> > ### Tomcat
> >
> ### Registro y autenticación Usuario empleado/MySQL
> > Por la parte de MySQL se han creado las entidades de administrador y usuario empleado
> > 
> > Al registrar un usuario persiste en la base de datos y puede iniciar sesion a posteriori
> > 
> > Tenemos un controlador que se ocupa tanto de login como de registro del usuario empleado
> > * Iniciar sesion, url: http://172.19.0.3:8080/tienda/usuario/autusuario
> > * Registrar usuario, url: http://172.19.0.3:8080/tienda/usuario/registro
> ### Registro Cliente/MySQL
> > Por la parte de MySQL se ha creado la entidad Cliente
> > * Registrar Cliente, url: http://172.19.0.3:8080/tienda/datos-personales
