# API GasVolcan (`/api/v1`)

Autenticación: `Authorization: Bearer <token>` (se obtiene en login/registro).

| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | /auth/registro | público | Crea cliente: rut, nombre, email, password (≥6), region, comuna |
| POST | /auth/login | público | email + password → token |
| GET | /productos, /productos/{id} | público | Catálogo |
| POST/PUT/DELETE | /productos | ADMINISTRADOR, VENDEDOR | Gestión (PUT sirve para editar stock) |
| GET/POST | /categorias | GET público, POST admin/vendedor | |
| GET/POST/PUT/DELETE | /usuarios | ADMINISTRADOR | CRUD usuarios (DELETE es lógico; no puedes borrarte a ti mismo) |
| POST | /boletas | autenticado | Crea pedido: `direccion` (5-100) + `items[{id,cantidad}]`; descuenta stock |
| GET | /boletas/mias | autenticado | Pedidos del usuario |
| GET | /boletas | ADMINISTRADOR, VENDEDOR | Todos los pedidos |
| PUT | /boletas/{id}/estado | ADMINISTRADOR, VENDEDOR | `{"estado":"En reparto"}`; Cancelado repone stock; Entregado/Cancelado son finales |

## Formato de datos (igual al frontend)
- **Producto**: `id` (`G-05`), `name`, `descripcion`, `price`, `stock`, `stockCritico`, `img`, `categoriaId`. Al crear, `id` es opcional (si no se envía se genera `G-<n>`). Las rutas `/productos/{id}` usan ese código.
- **Pedido**: `id` (`P-0001`), `fecha`, `total`, `usuarioId`, `cliente`, `comuna`, `direccion`, `estado`, `items[{id, nombre, cantidad, precio, subtotal}]`. Para crear: `{"direccion": "...", "items": [{"id": "G-05", "cantidad": 2}]}`.
- **Estado**: `Pendiente`, `En reparto`, `Entregado`, `Cancelado`. Cambiar: `PUT /boletas/P-0001/estado` con `{"estado": "En reparto"}`.
- **Usuario**: `id`, `rut`, `nombre`, `email`, `rol` (`Admin` / `Cliente` / `Vendedor`), `region`, `comuna`.
- **Login/registro**: `{token, tokenType, id, nombre, email, rol}`.
