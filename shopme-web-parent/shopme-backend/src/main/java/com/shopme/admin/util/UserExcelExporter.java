package com.shopme.admin.util;

import com.shopme.common.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {

    private XSSFWorkbook workbook;
    private XSSFSheet xssfSheet;
    private static final String[] EXCEL_HEADER = {"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();

    }



    public void export(List<User> userList, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "application/octet-stream", ".xlsx");


        String[] fieldMapping = {"id", "email", "firstName", "lastName", "roles", "enabled"};

//        csvBeanWriter.writeHeader(csvHeader);
//
//        for (User user : userList) {
//            csvBeanWriter.write(user, fieldMapping);
//        }
//        csvBeanWriter.close();

        writerHeaderLine();
        writeDataLine(userList);

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void writerHeaderLine() {
        xssfSheet = workbook.createSheet("Users");
        XSSFRow row = xssfSheet.createRow(0);

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        cellStyle.setFont(font);

        for (int i = 0; i < EXCEL_HEADER.length; i++) {
            createCell(row, i, EXCEL_HEADER[i], cellStyle);
        }

    }

    private void writeDataLine(List<User> userList) {
        int startRow = 1;

        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(16);
        cellStyle.setFont(font);
        int columnIndex = 0;
        for (User user : userList) {
            XSSFRow row = xssfSheet.createRow(startRow++);


            createCell(row, columnIndex++, user.getId(), cellStyle);
            createCell(row, columnIndex++, user.getEmail(), cellStyle);
            createCell(row, columnIndex++, user.getFirstName(), cellStyle);
            createCell(row, columnIndex++, user.getLastName(), cellStyle);
            createCell(row, columnIndex++, user.getRoles().toString(), cellStyle);
            createCell(row, columnIndex, user.isEnabled(), cellStyle);
            columnIndex = 0;
        }
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
        XSSFCell cell = row.createCell(columnIndex);
        xssfSheet.autoSizeColumn(columnIndex);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue(value.toString());
        }

        cell.setCellStyle(style);
    }




}
