# Practica integradora del Grupo1

> [!Warning]
> Desplegar en ubuntu, necesario tener instalado docker (por medio del comando $sudo snap install docker)

## Pasos a seguir:
1. Clonar el repositorio
2. Dentro del repositorio abrir un terminal
3. Escribir docker compose up -d --build
4. cambiamos el servidor DNS de nuestra máquina a 172.19.0.6

> [!Important]
> ### Funcionalidades
> > #### Vue/Mongo
> > Por la parte de Vue podemos ver los productos que tiene la base de datos, url: http://productos.poketienda.com
> >
> > Además de tener navegabilidad con las distintas partes del proyecto
> >
> > Esta habilitada la búsqueda por nombre y la búsqueda por precio
> > 
> > Por la parte de mongo, tenemos un controlador funcional en el que podemos
> > * Muestar la lista completa de productos
> > * Borrar un producto en concreto
> > * Crear un producto-> http://productos.poketienda.com/#/producto/nuevo
> > * Ver en detalle un producto-> http://productos.poketienda.com/#/producto/detalle/1
>
> > ### Registro y autenticación Usuario empleado/MySQL
> > Por la parte de MySQL se han creado las entidades de administrador y usuario empleado
> > Al registrar un usuario persiste en la base de datos y puede iniciar sesión a posteriori
> > Tenemos un controlador que se ocupa tanto de login y registro del usuario empleado y
> > del usuario administrador y de la recuperación de contraseña del empleado 
> > * Iniciar sesion empleado, url: http://www.poketienda.com/usuario/authusuario
> > * Iniciar sesion administrador, url: http://www.poketienda.com/usuario/authadmin
> > * Registrar usuario empleado, url: http://www.poketienda.com/usuario/registro
> > * Recuperar contraseña, url: http://www.poketienda.com/usuario/recuperar
> > 
> > Un controlador que se ocupa de las acciones que puede realizar el administrador
> > * Página principal, url: http://www.poketienda.com/admin/administracion
> > * Listado usuarios, url: http://www.poketienda.com/admin/listado-usuarios
> > * Listado clientes, url: http://www.poketienda.com/admin/listado-clientes
> > 
> > Un controlador que se ocupa de las acciones del empleado en la tienda
> > * Área personal, url: http://www.poketienda.com/tienda/area-personal
> > 
> > Credenciales para iniciar sesión con un usuario empleado completo
> > * Email: emp1@gmail.com
> > * Contraseña: Patata!53
> > 
> > Credenciales para iniciar sesión con un usuario empleado sin cliente registrado
> > * Email: emp2@gmail.com
> > * Contraseña: Patata!53
> > 
> > Credenciales para iniciar sesión con un usuario administrador
> > * Email: admin1@gmail.com
> > * Contraseña: Patata!53
> 
> > ### Registro Cliente/MySQL
> > Por la parte de MySQL se ha creado la entidad Cliente.
> >
> > Para poder registrar un cliente y persistirlo en la base de datos es necesario introducir
> > los datos de forma válida. Es un formulario por pasos, que realiza validaciones por grupos
> > sobre el objeto de Cliente.
> > Tenemos un controlador que se ocupa de toda la gestión del alta del cliente, que tiene un
> > Usuario empleado activo en la sesión.
> > * Registrar Cliente, url: http://www.poketienda.com/alta-cliente/datos-personales
