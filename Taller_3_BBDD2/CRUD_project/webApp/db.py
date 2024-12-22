from pymongo import MongoClient

# Configurar la conexión a MongoDB
client = MongoClient("mongodb://localhost:27017/")  # Cambia la URI si es necesario
db = client["webApp"]  # Nombre de la base de datos
