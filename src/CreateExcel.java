import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class CreateExcel {
	private int rowNum = 0, colNum;
	private Cell cell;
	private Row row;
	private Sheet sheet;
	private HSSFWorkbook workbook;
	private File file;
	private ResultSet rs;

	private CellStyle createStyleForTitle(HSSFWorkbook workbook) {
		Font font = workbook.createFont();
		font.setBold(true);
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}

	public String getPath() {
		return file.getAbsolutePath();
	}

	CreateExcel(ResultSet rs, Object []col, Object []getRs, String excelName) throws IOException, SQLException {
    	this.rs = rs;
    	workbook = new HSSFWorkbook();
        sheet = workbook.createSheet(excelName);
        colNum = col.length;
        CellType setDataType[] = new CellType[colNum];
        
        CellStyle style = createStyleForTitle(workbook);
        row = sheet.createRow(rowNum);
        
        //Write title row and set data type
        for (int i=0; i<colNum; i++) {
        	cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(col[i].toString());
            cell.setCellStyle(style);
            String s = getRs[i].toString();
            if (s.equals("class_id") || s.equals("manufacturing_year") || s.equals("price") || s.equals("number_of_seats") || s.equals("power") || s.equals("truck_load")) 
            	setDataType[i] = CellType.NUMERIC;
            else setDataType[i] = CellType.STRING;
        }
  
        // Write data to Excel
        while (rs.next()) {
            rowNum++;
            row = sheet.createRow(rowNum);
            for (int i=0; i<colNum; i++) {
            	cell = row.createCell(i, setDataType[i]);
                cell.setCellValue(rs.getString(getRs[i].toString()));
            }
        }
        
        for (int i=0; i<colNum; i++) {
        	sheet.autoSizeColumn(i);
        }
        
        file = new File(excelName+".xls");
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        workbook.close();
    }

}