from pymongo import MongoClient

# Conexión a la base de datos
client = MongoClient("mongodb://localhost:27017/")
db = client["tienda"]

# Creación y población de colecciones
def populate_database():
    productos = [
        {"_id": 1, "nombre": "Laptop", "categoria": "Electrónica", "precio": 800},
        {"_id": 2, "nombre": "Auriculares", "categoria": "Electrónica", "precio": 50},
        {"_id": 3, "nombre": "Camiseta", "categoria": "Ropa", "precio": 20},
        {"_id": 4, "nombre": "Pantalón", "categoria": "Ropa", "precio": 40},
        {"_id": 5, "nombre": "Zapatillas", "categoria": "Calzado", "precio": 60},
    ]

    ventas = [
        {"_id": 1, "producto_id": 1, "cantidad": 2, "fecha": "2024-11-01"},
        {"_id": 2, "producto_id": 2, "cantidad": 5, "fecha": "2024-11-02"},
        {"_id": 3, "producto_id": 3, "cantidad": 3, "fecha": "2024-11-02"},
        {"_id": 4, "producto_id": 1, "cantidad": 1, "fecha": "2024-11-03"},
        {"_id": 5, "producto_id": 4, "cantidad": 2, "fecha": "2024-11-03"},
        {"_id": 6, "producto_id": 5, "cantidad": 4, "fecha": "2024-11-04"},
    ]

    db.productos.insert_many(productos)
    db.ventas.insert_many(ventas)
    print("Base de datos poblada exitosamente.")

# Consultas con pipelines de agregación
def consultas_aggregaciones():
    # Consulta 1: Total de ingresos por producto
    pipeline_1 = [
        {"$lookup": {"from": "productos", "localField": "producto_id", "foreignField": "_id", "as": "producto"}},
        {"$unwind": "$producto"},
        {"$group": {"_id": "$producto.nombre", "total_ingresos": {"$sum": {"$multiply": ["$cantidad", "$producto.precio"]}}}},
        {"$sort": {"total_ingresos": -1}},
    ]
    resultado_1 = list(db.ventas.aggregate(pipeline_1))
    print("Consulta 1: Total de ingresos por producto:")
    for r in resultado_1:
        print(r)

    # Consulta 2: Ventas totales por categoría
    pipeline_2 = [
        {"$lookup": {"from": "productos", "localField": "producto_id", "foreignField": "_id", "as": "producto"}},
        {"$unwind": "$producto"},
        {"$group": {"_id": "$producto.categoria", "total_ventas": {"$sum": "$cantidad"}}},
        {"$sort": {"total_ventas": -1}},
    ]
    resultado_2 = list(db.ventas.aggregate(pipeline_2))
    print("\nConsulta 2: Ventas totales por categoría:")
    for r in resultado_2:
        print(r)

    # Consulta 3: Producto más vendido
    pipeline_3 = [
        {"$lookup": {"from": "productos", "localField": "producto_id", "foreignField": "_id", "as": "producto"}},
        {"$unwind": "$producto"},
        {"$group": {"_id": "$producto.nombre", "total_vendido": {"$sum": "$cantidad"}}},
        {"$sort": {"total_vendido": -1}},
        {"$limit": 1},
    ]
    resultado_3 = list(db.ventas.aggregate(pipeline_3))
    print("\nConsulta 3: Producto más vendido:")
    for r in resultado_3:
        print(r)

if __name__ == "__main__":
    # Poblar base de datos y realizar consultas
    print("Iniciando el script...")
    db.productos.delete_many({})
    db.ventas.delete_many({})
    populate_database()
    consultas_aggregaciones()
