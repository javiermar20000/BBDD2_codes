from django.urls import path
from . import views

urlpatterns = [
    path('', views.menu_principal, name='menu_principal'),
    path('usuarios/', views.listar_usuarios, name='listar_usuarios'),
    path('usuarios/nuevo/', views.nuevo_usuario, name='nuevo_usuario'),
    path('usuarios/editar/<str:id_usuario>/', views.editar_usuario, name='editar_usuario'),
    path('usuarios/eliminar/<str:id_usuario>/', views.eliminar_usuario, name='eliminar_usuario'),
    
    path('productos/', views.listar_productos, name='listar_productos'),
    path('productos/nuevo/', views.nuevo_producto, name='nuevo_producto'),
    path('productos/editar/<str:id_producto>/', views.editar_producto, name='editar_producto'),
    path('productos/eliminar/<str:id_producto>/', views.eliminar_producto, name='eliminar_producto'),
    
    path('ventas/', views.realizar_venta, name='realizar_venta'),
    path('ventas/historial/', views.historial_ventas, name='historial_ventas'),
    path('ventas/filtros/', views.filtros_ventas, name='filtros_ventas'),
]
