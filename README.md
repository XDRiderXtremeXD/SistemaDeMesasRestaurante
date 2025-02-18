# SistemaDeMesasRestaurante

Sistema de restaurante que permite hacer pedidos y mostrar en pantalla qué pedidos están pendientes. Esto con el fin de optimizar las órdenes que se le dan a los cocineros a la hora de preparar los platos. Cuenta con un sistema de roles, como admin, cocinero y mozo.

![image](https://github.com/user-attachments/assets/4dab5be6-37aa-4d7e-910b-780e29329242)

## Creación de la Base de Datos

```sql
CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;

-- Tabla Sala
CREATE TABLE Sala (
  IdSala INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(100) NOT NULL,
  Mesas INT NOT NULL,
  PRIMARY KEY (IdSala)
);

-- Tabla Pedido
CREATE TABLE Pedido (
  IdPedido INT NOT NULL AUTO_INCREMENT,
  SalaId INT NOT NULL,
  NumeroMesa INT NOT NULL,
  Fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  Total DECIMAL(10,2) NOT NULL,
  Estado ENUM('Pendiente','Entregado','Finalizado') NOT NULL DEFAULT 'Pendiente',
  Usuario VARCHAR(100) NOT NULL,
  PRIMARY KEY (IdPedido),
  KEY SalaId (SalaId),
  CONSTRAINT Pedido_Sala_FK FOREIGN KEY (SalaId) REFERENCES Sala(IdSala)
);

-- Tabla DetallePedido
CREATE TABLE DetallePedido (
  IdDetallePedido INT NOT NULL AUTO_INCREMENT,
  NombreProducto VARCHAR(200) NOT NULL,
  Precio DECIMAL(10,2) NOT NULL,
  Cantidad INT NOT NULL,
  Comentario TEXT DEFAULT NULL,
  PedidoId INT NOT NULL,
  PRIMARY KEY (IdDetallePedido),
  KEY PedidoId (PedidoId),
  CONSTRAINT DetallePedido_Pedido_FK FOREIGN KEY (PedidoId) REFERENCES Pedido(IdPedido)
);

-- Tabla Plato
CREATE TABLE Plato (
  IdPlato INT NOT NULL AUTO_INCREMENT,
  Nombre VARCHAR(200) NOT NULL,
  Precio DECIMAL(10,2) NOT NULL,
  Fecha DATE DEFAULT NULL,
  PRIMARY KEY (IdPlato)
);

-- Tabla Usuario
CREATE TABLE Usuario (
  IdUsuario INT NOT NULL AUTO_INCREMENT,
  NombreUsuario VARCHAR(50) UNIQUE,
  Correo VARCHAR(200) NOT NULL,
  Contrasena VARCHAR(50) NOT NULL,
  Rol ENUM('Mozo','Cocinero','Administrador') NOT NULL,
  PRIMARY KEY (IdUsuario)
);
```
