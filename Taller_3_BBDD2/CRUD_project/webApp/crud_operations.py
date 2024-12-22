from bson.objectid import ObjectId
from .db import db

# Crear un nuevo usuario
def crear_usuario(nombre, edad):
    usuarios = db["usuarios"]
    usuario = {"nombre": nombre, "edad": edad}
    return usuarios.insert_one(usuario).inserted_id

# Leer todos los usuarios
def obtener_usuarios():
    usuarios = db["usuarios"]
    return list(usuarios.find())

# Leer un usuario por ID
def obtener_usuario_por_id(id_usuario):
    usuarios = db["usuarios"]
    return usuarios.find_one({"_id": ObjectId(id_usuario)})

# Actualizar un usuario
def actualizar_usuario(id_usuario, nuevos_datos):
    usuarios = db["usuarios"]
    return usuarios.update_one(
        {"_id": ObjectId(id_usuario)},
        {"$set": nuevos_datos}
    ).modified_count

# Eliminar un usuario
def eliminar_usuario(id_usuario):
    usuarios = db["usuarios"]
    return usuarios.delete_one({"_id": ObjectId(id_usuario)}).deleted_count
