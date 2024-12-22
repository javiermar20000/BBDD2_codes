from django.shortcuts import render, redirect
from bson import ObjectId
from django.http import JsonResponse
from pymongo import MongoClient
from datetime import datetime, timedelta

# Conexión a MongoDB
client = MongoClient("mongodb://localhost:27017/")
db = client["webApp"]  # Nombre de la base de datos

# Para cargar la vista html del menú principal
def menu_principal(request):
    return render(request, 'webApp/menu_principal.html')

# Listar usuarios
def listar_usuarios(request):
    usuarios = list(
        db.usuarios.find({}, {"_id": 1, "nombre": 1, "apellido": 1, "rut": 1, "edad": 1, "direccion": 1})
    )
    for usuario in usuarios:
        usuario["id"] = str(usuario.pop("_id"))  # Cambiar _id a id y convertirlo a cadena
    return render(request, "webApp/lista_usuarios.html", {"usuarios": usuarios})

# Crear usuario
def nuevo_usuario(request):
    if request.method == "POST":
        nombre = request.POST.get("nombre")
        apellido = request.POST.get("apellido")
        rut = request.POST.get("rut")
        edad = int(request.POST.get("edad"))
        direccion = request.POST.get("direccion")
        
        # Verificar duplicados por RUT
        if db.usuarios.find_one({"rut": rut}):
            return render(request, "webApp/crear_usuario.html", {"error": "El RUT ya existe."})
        
        # Insertar usuario
        db.usuarios.insert_one({"nombre": nombre, "apellido": apellido, "rut": rut, "edad": edad, "direccion": direccion})
        return redirect("listar_usuarios")
    return render(request, "webApp/crear_usuario.html")

# Editar usuario
def editar_usuario(request, id_usuario):
    usuario = db.usuarios.find_one({"_id": ObjectId(id_usuario)})
    if request.method == "POST":
        nombre = request.POST.get("nombre")
        apellido = request.POST.get("apellido")
        rut = request.POST.get("rut")
        edad = int(request.POST.get("edad"))
        direccion = request.POST.get("direccion")
        
        db.usuarios.update_one(
            {"_id": ObjectId(id_usuario)},
            {"$set": {"nombre": nombre, "apellido": apellido, "rut": rut, "edad": edad, "direccion": direccion}}
        )
        return redirect("listar_usuarios")
    usuario["id"] = str(usuario.pop("_id"))
    return render(request, "webApp/editar_usuario.html", {"usuario": usuario})

# Eliminar usuario
def eliminar_usuario(request, id_usuario):
    db.usuarios.delete_one({"_id": ObjectId(id_usuario)})
    return redirect("listar_usuarios")


# Gestión de Productos
# Listar productos
def listar_productos(request):
    productos = list(
        db.productos.find({}, {"_id": 1, "nombre": 1, "precio": 1, "descripcion": 1, "stock": 1})
    )
    for producto in productos:
        producto["id"] = str(producto.pop("_id"))
    return render(request, "webApp/lista_productos.html", {"productos": productos})

# Crear producto
def nuevo_producto(request):
    if request.method == "POST":
        nombre = request.POST.get("nombre")
        precio = float(request.POST.get("precio"))
        descripcion = request.POST.get("descripcion")
        stock = int(request.POST.get("stock"))
        
        db.productos.insert_one({"nombre": nombre, "precio": precio, "descripcion": descripcion, "stock": stock})
        return redirect("listar_productos")
    return render(request, "webApp/crear_producto.html")

# Editar producto
def editar_producto(request, id_producto):
    producto = db.productos.find_one({"_id": ObjectId(id_producto)})
    if request.method == "POST":
        nombre = request.POST.get("nombre")
        precio = request.POST.get("precio").replace(",", ".")  # Reemplaza coma por punto
        precio = float(precio)  # Convierte a float
        descripcion = request.POST.get("descripcion")
        stock = int(request.POST.get("stock"))
        
        db.productos.update_one(
            {"_id": ObjectId(id_producto)},
            {"$set": {"nombre": nombre, "precio": precio, "descripcion": descripcion, "stock": stock}}
        )
        return redirect("listar_productos")
    producto["id"] = str(producto.pop("_id"))
    return render(request, "webApp/editar_producto.html", {"producto": producto})

# Eliminar producto
def eliminar_producto(request, id_producto):
    db.productos.delete_one({"_id": ObjectId(id_producto)})
    return redirect("listar_productos")

# Gestión de ventas
# Realizar venta
def realizar_venta(request):
    if request.method == "POST":
        usuario_nombre = request.POST.get("usuario")  # Obtiene el nombre del usuario
        producto_nombre = request.POST.get("producto")  # Obtiene el nombre del producto
        cantidad = request.POST.get("cantidad")  # Obtiene la cantidad

        # Depuración: Verificar los valores recibidos
        print("Usuario Nombre:", usuario_nombre)
        print("Producto Nombre:", producto_nombre)
        print("Cantidad:", cantidad)

        # Validación de datos
        if not usuario_nombre or not producto_nombre or not cantidad:
            return render(
                request,
                "webApp/realizar_venta.html",
                {
                    "usuarios": list(db.usuarios.find()),
                    "productos": list(db.productos.find()),
                    "error": "Todos los campos son obligatorios.",
                },
            )

        try:
            # Buscar los objetos de usuario y producto por nombre
            usuario = db.usuarios.find_one({"nombre": usuario_nombre})
            producto = db.productos.find_one({"nombre": producto_nombre})

            if not usuario or not producto:
                raise ValueError("Usuario o producto no encontrado.")

            # Verificar el stock disponible
            if producto["stock"] < int(cantidad):
                return render(
                    request,
                    "webApp/realizar_venta.html",
                    {
                        "usuarios": list(db.usuarios.find()),
                        "productos": list(db.productos.find()),
                        "error": "Stock insuficiente para la venta.",
                    },
                )

            # Actualizar el stock y registrar la venta
            db.productos.update_one(
                {"nombre": producto_nombre},
                {"$inc": {"stock": -int(cantidad)}},
            )

            monto_total = producto["precio"] * int(cantidad)
            db.ventas.insert_one(
                {
                    "usuario_nombre": usuario_nombre,
                    "producto_nombre": producto_nombre,
                    "cantidad": int(cantidad),
                    "precio_unitario": producto["precio"],
                    "monto_total": monto_total,
                    "fecha": datetime.now()  # Fecha y hora actuales
                }
            )

            # Redirigir al historial de ventas
            return redirect("historial_ventas")

        except ValueError as e:
            return render(
                request,
                "webApp/realizar_venta.html",
                {
                    "usuarios": list(db.usuarios.find()),
                    "productos": list(db.productos.find()),
                    "error": str(e),
                },
            )

    # Si el método es GET, solo mostrar el formulario
    usuarios = list(db.usuarios.find())
    productos = list(db.productos.find())
    return render(
        request,
        "webApp/realizar_venta.html",
        {"usuarios": usuarios, "productos": productos}
    )
# Historial de ventas
def historial_ventas(request):
    ventas = list(db.ventas.find({}))
    return render(request, "webApp/historial_ventas.html", {"ventas": ventas})


# Filtros de ventas
def filtros_ventas(request):
    filtros = {}
    
    # Obtener filtros del usuario desde el formulario
    usuario_nombre = request.GET.get("usuario")
    producto_nombre = request.GET.get("producto")
    fecha_inicio = request.GET.get("fecha_inicio")
    fecha_fin = request.GET.get("fecha_fin")
    
    # Construir el pipeline
    pipeline = []

    # Filtro por usuario
    if usuario_nombre:
        pipeline.append({"$match": {"usuario_nombre": usuario_nombre}})
        filtros["usuario_nombre"] = usuario_nombre

    # Filtro por producto
    if producto_nombre:
        pipeline.append({"$match": {"producto_nombre": producto_nombre}})
        filtros["producto_nombre"] = producto_nombre

    # Filtro por rango de fechas
    if fecha_inicio and fecha_fin:
        try:
            fecha_inicio = datetime.strptime(fecha_inicio, "%Y-%m-%d")
            fecha_fin = datetime.strptime(fecha_fin, "%Y-%m-%d") + timedelta(days=1) - timedelta(seconds=1)
            pipeline.append(
                {"$match": {"fecha": {"$gte": fecha_inicio, "$lte": fecha_fin}}}
            )
            filtros["fecha_inicio"] = fecha_inicio
            filtros["fecha_fin"] = fecha_fin
        except ValueError:
            pass

    # Obtener los resultados aplicando el pipeline
    ventas = list(db.ventas.aggregate(pipeline))

    return render(request, "webApp/filtros_ventas.html", {"ventas": ventas, "filtros": filtros})
