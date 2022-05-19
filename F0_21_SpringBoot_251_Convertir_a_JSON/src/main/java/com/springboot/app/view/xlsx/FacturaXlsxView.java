package com.springboot.app.view.xlsx;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.springboot.app.models.entity.Factura;
import com.springboot.app.models.entity.ItemFactura;

@Component("factura/ver.xlsx")
public class FacturaXlsxView extends AbstractXlsxView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//SE LE CAMBIA EN NOMBRE POR DEFECTO AL ARCHIVO
		response.setHeader("Content-Disposition", "attachment; filename=\"factura_view.xlsx\"");
		
		Factura factura = (Factura) model.get("factura");
		//SE CREA PLANILLA EXCEL
		Sheet sheet = workbook.createSheet("Factura Spring");
		//SE CREA LA PRIMERA FILA
		Row row = sheet.createRow(0); 
		//SE CREA LA PRIMERA COLUMNA
		Cell cell = row.createCell(0);
		cell.setCellValue("Datos del cliente: ");
		
		row = sheet.createRow(1);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getNombre() + " " + factura.getCliente().getApellido());
		
		row = sheet.createRow(2);
		cell = row.createCell(0);
		cell.setCellValue(factura.getCliente().getEmail());
		
		//---------------- AQUÍ EL EXCEL SE VA HACIENDO DE OTRA MANERA ---> ...
		sheet.createRow(4).createCell(0).setCellValue("Datos de la factura");
		sheet.createRow(5).createCell(0).setCellValue("Folio: " + factura.getId());
		sheet.createRow(6).createCell(0).setCellValue("Descripción: " + factura.getDescripcion());
		sheet.createRow(7).createCell(0).setCellValue("Fecha: " + factura.getCreateAt());
		
		//-------------------------------------------------------------------------
		CellStyle tHeatherStyle = workbook.createCellStyle();
		tHeatherStyle.setBorderBottom(BorderStyle.MEDIUM);
		tHeatherStyle.setBorderTop(BorderStyle.MEDIUM);
		tHeatherStyle.setBorderRight(BorderStyle.MEDIUM);
		tHeatherStyle.setBorderLeft(BorderStyle.MEDIUM);
		tHeatherStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		tHeatherStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		CellStyle tbodyStyle = workbook.createCellStyle();
		tbodyStyle.setBorderBottom(BorderStyle.THIN);
		tbodyStyle.setBorderTop(BorderStyle.THIN);
		tbodyStyle.setBorderRight(BorderStyle.THIN);
		tbodyStyle.setBorderLeft(BorderStyle.THIN);
		//-------------------------------------------------------------------------
		
		Row header = sheet.createRow(9);
		header.createCell(0).setCellValue("producto");
		header.createCell(1).setCellValue("precio");
		header.createCell(2).setCellValue("cantidad");
		header.createCell(3).setCellValue("Total");
		
		//SE APLICAN LOS ESTILOS--------------------------------
		header.getCell(0).setCellStyle(tHeatherStyle);
		header.getCell(1).setCellStyle(tHeatherStyle);
		header.getCell(2).setCellStyle(tHeatherStyle);
		header.getCell(3).setCellStyle(tHeatherStyle);
		
		
		int rowNum = 10; // 10 : PARA QUE PARTA DESDE LA FILA 10
		
		for(ItemFactura item :  factura.getItems()) {
			Row fila = sheet.createRow(rowNum++);
			
			cell = fila.createCell(0);
			cell.setCellValue(item.getProducto().getNombre());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(1);
			cell.setCellValue(item.getProducto().getPrecio());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(2);
			cell.setCellValue(item.getCantidad());
			cell.setCellStyle(tbodyStyle);
			
			cell = fila.createCell(3);
			cell.setCellValue(item.calcularImporte());
			cell.setCellStyle(tbodyStyle);
		}
		
		//GRAN TOTAL : ESTE PARTE DESPUES DE LA ÚLTIMA FILA QUE ARROJE EL BUCLE FOR.
		Row filaTotal = sheet.createRow(rowNum);
		filaTotal.createCell(2).setCellValue("Gran Total: ");
		filaTotal.createCell(3).setCellValue(factura.getTotal());
		
		
	}

}
