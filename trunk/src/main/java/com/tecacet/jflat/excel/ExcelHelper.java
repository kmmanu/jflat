package com.tecacet.jflat.excel;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {

	private final Workbook workbook;
	private final CreationHelper createHelper;

	private String dateFormat = "m/d/yy h:mm";

	private ExcelHelper(Workbook workbook) {
		this.workbook = workbook;
		this.createHelper = workbook.getCreationHelper();
	}

	public static ExcelHelper getHelper(Workbook workbook) {
		return new ExcelHelper(workbook);
	}

	public static Workbook getWorkbook(String extension) throws IOException {
		if ("xlsx".equals(extension)) {
			return new XSSFWorkbook();
		}
		if ("xls".equals(extension)) {
			return new HSSFWorkbook(); // xls
		}
		throw new IOException("Invlaid extension " + extension);
	}

	public static void writeWorkbook(Workbook workbook, String filename)
			throws IOException {
		String completeName;
		if (workbook instanceof HSSFWorkbook) {
			completeName = filename + ".xls";
		} else if (workbook instanceof XSSFWorkbook) {
			completeName = filename + ".xlsx";
		} else { // TODO support 3rd type
			throw new IOException("Invalid workbook");
		}
		FileOutputStream fileOut = new FileOutputStream(completeName);
		workbook.write(fileOut);
		fileOut.close();
	}

	public Cell[] putValues(Row row, Object[] values) {
		Cell[] cells = new Cell[values.length];
		for (int i = 0; i < values.length; i++) {
			cells[i] = row.createCell(i);
			setCellValue(cells[i], values[i]); // TODO
		}
		return cells;
	}

	private void setCellValue(Cell cell, Object object) {

		if (object == null) {
			cell.setCellValue("");
			return;
		}
		if (object instanceof Number) {
			cell.setCellValue(((Number) object).doubleValue());
		} else if (object instanceof Boolean) {
			cell.setCellValue((Boolean) object);
		} else if (object instanceof String) {
			cell.setCellValue(createHelper
					.createRichTextString((String) object));
		} else if (object instanceof Date) {
			cell.setCellValue((Date) object);
			cell.setCellStyle(getDateStyle());
			// TODO handle Joda
		} else if (object instanceof Calendar) {
			cell.setCellValue((Calendar) object);
			cell.setCellStyle(getDateStyle());
		} else {
			cell.setCellValue(object.toString());
		}
	}

	private CellStyle getDateStyle() {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setDataFormat(createHelper.createDataFormat().getFormat(
				dateFormat));
		return cellStyle;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * Creates a cell and aligns it a certain way.
	 * 
	 * @param wb
	 *            the workbook
	 * @param row
	 *            the row to create the cell in
	 * @param column
	 *            the column number to create the cell in
	 * @param halign
	 *            the horizontal alignment for the cell.
	 */
	public Cell createCell(Row row, short column, short halign, short valign) {
		Cell cell = row.createCell(column);
		// cell.setCellValue("Align It");
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(halign);
		cellStyle.setVerticalAlignment(valign);
		cell.setCellStyle(cellStyle);
		return cell;
	}

}
