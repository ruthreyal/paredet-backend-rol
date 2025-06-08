# Endpoints de la API – ParedetApp

Este documento contiene un resumen de los endpoints disponibles en la API del backend de ParedetApp, organizados por entidad y con su nivel de acceso.

---

## Autenticación

| Método | Endpoint              | Descripción                | Público / Protegido |
|--------|-----------------------|----------------------------|---------------------|
| POST   | `/api/auth/register`  | Registro de usuario        | Público             |
| POST   | `/api/auth/login`     | Login de usuario           | Público             |

---

## Usuarios

| Método | Endpoint                      | Descripción                         | Rol requerido          |
|--------|-------------------------------|-------------------------------------|------------------------|
| GET    | `/api/usuarios`               | Listar todos los usuarios           | ADMIN                 |
| GET    | `/api/usuarios/{id}`          | Obtener usuario por ID              | ADMIN o Propietario   |
| GET    | `/api/usuarios/email/{email}` | Obtener usuario por email           | ADMIN o Propietario   |
| POST   | `/api/usuarios`               | Crear nuevo usuario                 | ADMIN                 |
| PUT    | `/api/usuarios/{id}`          | Actualizar datos personales         | ADMIN o Propietario   |
| DELETE | `/api/usuarios/{id}`          | Eliminar usuario                    | ADMIN                 |

---

## Carritos

| Método | Endpoint                 | Descripción               | Rol requerido          |
|--------|--------------------------|---------------------------|------------------------|
| GET    | `/api/carritos`          | Listar todos los carritos | ADMIN                 |
| GET    | `/api/carritos/{id}`     | Obtener carrito por ID    | ADMIN o Propietario   |
| POST   | `/api/carritos`          | Crear carrito             | Usuario autenticado   |
| DELETE | `/api/carritos/{id}`     | Eliminar carrito          | ADMIN                 |

---

## Productos

| Método | Endpoint               | Descripción                | Rol requerido        |
|--------|------------------------|----------------------------|----------------------|
| GET    | `/api/productos`       | Listar todos los productos | Público              |
| GET    | `/api/productos/{id}`  | Obtener producto por ID    | Público              |
| POST   | `/api/productos`       | Crear producto             | ADMIN                |
| DELETE | `/api/productos/{id}`  | Eliminar producto          | ADMIN                |

---

## Categorías

| Método | Endpoint            | Descripción              | Rol requerido        |
|--------|---------------------|--------------------------|----------------------|
| GET    | `/api/categorias`   | Listar categorías        | Público              |
| POST   | `/api/categorias`   | Crear categoría          | ADMIN                |

---

## Colecciones

| Método | Endpoint                  | Descripción                | Rol requerido        |
|--------|---------------------------|----------------------------|----------------------|
| GET    | `/api/colecciones`        | Listar colecciones         | Público              |
| GET    | `/api/colecciones/{id}`   | Obtener colección por ID   | Público              |
| POST   | `/api/colecciones`        | Crear colección            | ADMIN                |
| DELETE | `/api/colecciones/{id}`   | Eliminar colección         | ADMIN                |

---

## Direcciones

| Método | Endpoint                  | Descripción                  | Rol requerido        |
|--------|---------------------------|------------------------------|----------------------|
| GET    | `/api/direcciones`        | Listar direcciones           | ADMIN                |
| GET    | `/api/direcciones/{id}`   | Obtener dirección por ID     | ADMIN o Propietario  |
| POST   | `/api/direcciones`        | Crear dirección              | Usuario autenticado  |
| DELETE | `/api/direcciones/{id}`   | Eliminar dirección           | ADMIN o Propietario  |

---

## Favoritos

| Método | Endpoint                                 | Descripción                        | Rol requerido        |
|--------|------------------------------------------|------------------------------------|----------------------|
| GET    | `/api/favoritos/{usuarioId}`             | Listar favoritos de un usuario     | ADMIN o Propietario  |
| POST   | `/api/favoritos/{usuarioId}/{productoId}`| Agregar producto a favoritos       | Propietario          |
| DELETE | `/api/favoritos/{usuarioId}/{productoId}`| Eliminar producto de favoritos     | ADMIN o Propietario  |

---

## Pedidos

| Método | Endpoint            | Descripción              | Rol requerido        |
|--------|---------------------|--------------------------|----------------------|
| GET    | `/api/pedidos`      | Listar todos los pedidos | ADMIN                |
| GET    | `/api/pedidos/{id}` | Obtener pedido por ID    | ADMIN o Propietario  |
| POST   | `/api/pedidos`      | Crear nuevo pedido       | Usuario autenticado  |
| DELETE | `/api/pedidos/{id}` | Eliminar pedido          | ADMIN o Propietario  |
