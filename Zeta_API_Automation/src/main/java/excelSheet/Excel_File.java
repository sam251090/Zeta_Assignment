package excelSheet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Excel_File {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	
	public static void setExcelFile(String excel_Path, String SheetName) throws Exception
	{
		try
		{
			FileInputStream ExcelFile = new FileInputStream(excel_Path);
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		}
		catch(Exception e)
		{
			throw (e);
		}
	}
	
	public static String getCellData( int RowNum, int ColNum) throws Exception
	{
		try
		{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getStringCellValue();
			return CellData;
		}
		catch(Exception e)
		{
			return " ";
		}
	}
	
	public static void setCellData( int RowNum, int ColNum, String data) throws Exception
	{
		try
		{    
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (Cell == null) 
			{
				Cell=ExcelWSheet.getRow(RowNum).createCell(ColNum);
				Cell.setCellType(CellType.STRING);
				Cell.setCellValue(data);

            } 
			else {

            	Cell.setCellType(CellType.STRING);
    			Cell.setCellValue(data);

                }
		}
		catch(Exception e)
		{
			throw(e);
		}
	}
	
	public static void closeExcelFile(String excel_Path) throws Exception
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(excel_Path);
			ExcelWBook.write(fos);
			fos.close();
		}
		catch(NullPointerException e)
		{
			throw (e);
		}
	}
	

}
