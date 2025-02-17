package utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;

import controller.DetallePedidoController;
import controller.PedidoController;
import model.DetallePedido;
import model.Pedido;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.io.InputStream;

public class GenerarPdf {
    private PedidoController pedidoController;
    private DetallePedidoController detallePController;

    public GenerarPdf(PedidoController pedidoController, DetallePedidoController detallePController) {
        this.pedidoController = pedidoController;
        this.detallePController = detallePController;
    }

    public void generarPDF(int idPedido) {
        System.out.println("Generando PDF para el pedido con ID: " + idPedido);

        if (idPedido <= 0) {
            System.err.println("ID de pedido no válido.");
            return;
        }

        Pedido pedido = pedidoController.obtenerPedido(idPedido);
        if (pedido == null) {
            System.err.println("Pedido no encontrado con ID: " + idPedido);
            return;
        }

        List<DetallePedido> listaDetalles = detallePController.obtenerDetallesPorPedido(idPedido);
        if (listaDetalles == null || listaDetalles.isEmpty()) {
            System.err.println("No hay detalles de pedido.");
            return;
        }

        try {
            String userHome = System.getProperty("user.home");
            String carpetaPDF = userHome + "/Desktop/PedidosPDF/";
            File carpeta = new File(carpetaPDF);
            if (!carpeta.exists() && !carpeta.mkdirs()) {
                System.err.println("No se pudo crear la carpeta: " + carpetaPDF);
                return;
            }

            String destino = carpetaPDF + "Pedido_" + idPedido + ".pdf";
            PdfWriter writer = new PdfWriter(destino);
            PdfDocument pdf = new PdfDocument(writer);
            Document documento = new Document(pdf);

         // Crear tabla con 2 columnas: Logo y Nombre del Restaurante
            Table headerTable = new Table(2).useAllAvailableWidth();

            // Cargar imagen del logo
            try (InputStream is = getClass().getResourceAsStream("/imgs/LogoIcon.png")) {
                if (is != null) {
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int bytesRead;
                    byte[] data = new byte[1024];
                    while ((bytesRead = is.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, bytesRead);
                    }
                    byte[] imageBytes = buffer.toByteArray();
                    ImageData imageData = ImageDataFactory.create(imageBytes);
                    Image logo = new Image(imageData).setWidth(30).setHeight(30); // Ajustar tamaño

                    // Celda del logo
                    Cell logoCell = new Cell().add(logo)
                            .setBorder(null)
                            .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE)
                            .setTextAlignment(TextAlignment.LEFT);
                    headerTable.addCell(logoCell);
                }
            }

            // Celda del título "Restaurante"
            Cell titleCell = new Cell().add(new Paragraph("Restaurante")
                    .setBold()
                    .setFontSize(20))
                    .setBorder(null)
                    .setVerticalAlignment(com.itextpdf.layout.properties.VerticalAlignment.MIDDLE)
                    .setTextAlignment(TextAlignment.RIGHT);

            headerTable.addCell(titleCell);

            // Agregar la tabla al documento
            documento.add(headerTable);

            // Frase célebre centrada
            documento.add(new Paragraph("\"La mejor comida es la que se comparte\"")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic()
                    .setMarginBottom(10));
            documento.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            
            // Información del pedido
            documento.add(new Paragraph("Sala: " + pedido.getNombreSala()));
            documento.add(new Paragraph("Usuario: " + pedido.getUsuario()));
            documento.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            documento.add(new Paragraph("Fecha: " + new SimpleDateFormat("dd-MM-yyyy").format(new Date())));
            documento.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            
            // Listado de productos sin formato de tabla
         // Crear tabla con 4 columnas (Producto, Cantidad, Precio, Subtotal)
            Table table = new Table(5).useAllAvailableWidth();

            // Encabezados
            table.addHeaderCell(new Cell().add(new Paragraph("Producto").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Cantidad").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Precio (S/.)").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Comentario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Subtotal (S/.)").setBold()));

            // Agregar filas con los detalles de cada producto
            for (DetallePedido detalle : listaDetalles) {
                table.addCell(new Cell().add(new Paragraph(detalle.getNombreProducto())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(detalle.getCantidad()))));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", detalle.getPrecio()))));
                table.addCell(new Cell().add(new Paragraph(detalle.getComentario())));
                table.addCell(new Cell().add(new Paragraph(String.format("%.2f", detalle.getSubTotal()))));
            }

            // Agregar tabla al documento
            documento.add(table);

            documento.add(new Paragraph("----------------------------------------------------------------------------------------------------------------------------------"));
            
            // Total
            documento.add(new Paragraph("Total General: S/. " + pedido.getTotal())
                    .setBold()
                    .setTextAlignment(TextAlignment.RIGHT));
            
            documento.add(new Paragraph("\nGracias por su compra").setTextAlignment(TextAlignment.CENTER));
            
            documento.close();
            System.out.println("PDF generado correctamente en: " + destino);
            
            File archivoPDF = new File(destino);
            if (archivoPDF.exists() && archivoPDF.length() > 0) {
                System.out.println("El PDF se generó correctamente y tiene tamaño: " + archivoPDF.length() + " bytes.");
            } else {
                System.err.println("El PDF no se generó correctamente.");
                return;
            }

            if (java.awt.Desktop.isDesktopSupported()) {
                java.awt.Desktop.getDesktop().open(archivoPDF);
            } else {
                System.out.println("El sistema no soporta la apertura automática de archivos.");
            }

        } catch (Exception e) {
            System.err.println("Error al generar el PDF: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
