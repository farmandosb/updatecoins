package com.hexacta.updatecoins.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExcelUtility {
  private final String DEFAULT_FILE_PATTERN = "yyyy-MM-dd-HH-mm-ss";

  public Map<Integer, List<String>> getRawDataFromExcel(String pathToImportFile) {
    Map<Integer, List<String>> data = new TreeMap<>();
    FileInputStream file;
    try {
      file = new FileInputStream(pathToImportFile);
      XSSFWorkbook workbook = new XSSFWorkbook(file);

      XSSFSheet sheet = workbook.getSheetAt(0);

      int i = 0;
      for (Row row : sheet) {
        data.put(i, new ArrayList<>());
        for (int j = 0; j < 2; j++) {
          Cell cell = row.getCell(j);
          switch (cell.getCellType()) {
            case STRING:
              data.get(i).add(cell.getRichStringCellValue().getString());
              break;
            case NUMERIC:
              if (DateUtil.isCellDateFormatted(cell)) {
                data.get(i).add(cell.getDateCellValue() + "");
              } else {
                // get double casted to int (not fraction of points)
                data.get(i).add((int) cell.getNumericCellValue() + "");
              }
              break;
            case BOOLEAN:
              data.get(i).add(cell.getBooleanCellValue() + "");
              break;
            case FORMULA:
              data.get(i).add(cell.getCellFormula() + "");
              break;
            default:
              data.get(i).add(" ");
          }
        }
        i++;
      }
      if (workbook != null) {
        workbook.close();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return data;
  }

  public void exportDataToExcel(Map<Integer, List<String>> data) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("UsersCoinsUpdated");
    sheet.setColumnWidth(0, 6000);
    sheet.setColumnWidth(1, 4000);
    sheet.setColumnWidth(2, 4000);

    Row header = sheet.createRow(0);

    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = workbook.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 12);
    font.setBold(true);
    headerStyle.setFont(font);

    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Nombre");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(1);
    headerCell.setCellValue("NewCoins");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(2);
    headerCell.setCellValue("OldCoins");
    headerCell.setCellStyle(headerStyle);

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    CellStyle highLightStyle = workbook.createCellStyle();
    highLightStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    highLightStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    CellStyle highLightTotalStyle = workbook.createCellStyle();
    highLightTotalStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
    highLightTotalStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    highLightTotalStyle.setFont(font);

    int updated = 0;
    int total = data.size();
    for (int i = 1; i < data.size(); i++) {
      Row row = sheet.createRow(i);
      for (int j = 0; j < data.get(j).size(); j++) {
        Cell cell = row.createCell(j);
        cell.setCellValue(data.get(i).get(j));
        cell.setCellStyle(style);
        boolean updatedRow = data.get(i).get(2) != "not updated";
        if (updatedRow) {
          cell.setCellStyle(highLightStyle);
          updated += 1;
        }
      }
    }
    Row row1 = sheet.createRow(total);
    Cell cell1 = row1.createCell(0);
    cell1.setCellValue("Total");
    Cell cell2 = row1.createCell(1);
    cell2.setCellValue(total + 1);
    cell1.setCellStyle(headerStyle);
    cell2.setCellStyle(headerStyle);

    Row row2 = sheet.createRow(total + 1);
    Cell cell3 = row2.createCell(0);
    cell3.setCellValue("Updated");
    cell3.setCellStyle(highLightTotalStyle);
    Cell cell4 = row2.createCell(1);
    cell4.setCellValue(updated);
    cell4.setCellStyle(highLightTotalStyle);

    File currDir = new File(".");
    String path = currDir.getAbsolutePath();
    String fileLocation = path.substring(0, path.length() - 1) + createFileName();

    try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String createFileName() {
    Date date = new Date(System.currentTimeMillis());
    SimpleDateFormat format = new SimpleDateFormat(DEFAULT_FILE_PATTERN);
    return "UpdatedCoinsReport_" + format.format(date) + ".xlsx";
  }
}
