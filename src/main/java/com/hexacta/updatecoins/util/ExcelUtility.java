package com.hexacta.updatecoins.util;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class ExcelUtility {

  public Map<Integer, List<String>> getRawDataFromExcel(String pathToImportFile) {
    Map<Integer, List<String>> data = new TreeMap<>();
    FileInputStream file = null;
    try {
      file = new FileInputStream(pathToImportFile);
      XSSFWorkbook workbook = new XSSFWorkbook(file);

      XSSFSheet sheet = workbook.getSheetAt(0);


      int i = 0;
      for (Row row : sheet) {
        data.put(i, new ArrayList<>());
        for (Cell cell : row) {
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

  public Map<Integer, List<String>> parseMapStringStringToMapIntegerListString(Map<String, String> dataToParse) {
    Map<Integer, List<String>> excelData = new HashMap<>();

    List<String> keyList = new ArrayList<>(dataToParse.keySet());
    for (int i = 0; i < keyList.size(); i++) {
      String key = keyList.get(i);
      String value = dataToParse.get(key);
      List<String> list = new ArrayList<>();
      list.add(key);
      list.add(value);
      excelData.put(i, list);
    }

    return excelData;
  }

  public void exportToExcel(Map<Integer, List<String>> excelData) {
    XSSFWorkbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("UsersCoinsUpdated");
    sheet.setColumnWidth(0, 6000);
    sheet.setColumnWidth(1, 4000);

    Row header = sheet.createRow(0);

    CellStyle headerStyle = workbook.createCellStyle();
    headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
    headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

    XSSFFont font = workbook.createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);
    headerStyle.setFont(font);

    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Nombre");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(1);
    headerCell.setCellValue("Coins");
    headerCell.setCellStyle(headerStyle);

    CellStyle style = workbook.createCellStyle();
    style.setWrapText(true);

    for (int i = 1; i < excelData.size(); i++) {
      Row row = sheet.createRow(i);
      for (int j = 0; j < excelData.get(j).size(); j++) {
        Cell cell = row.createCell(j);
        cell.setCellValue(excelData.get(i).get(j));
        cell.setCellStyle(style);
      }
    }

    File currDir = new File(".");
    String path = currDir.getAbsolutePath();
    String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

    try (FileOutputStream outputStream = new FileOutputStream(fileLocation)) {
      workbook.write(outputStream);
      workbook.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void exportToExcel(Map<Integer, List<String>> rawData, String pathToExportFile) {
    // workbook object
    Workbook workbook = new XSSFWorkbook();

    // spreadsheet object
    Sheet spreadsheet = workbook.createSheet("creditpoints");

    // creating a row object
    Row row;

    // This data needs to be written (Object[])

    // String[] array = list.toArray(new String[0]);

    Map<Integer, List<String>> userData = new TreeMap<>();

    //rawData.forEach((k, v) -> userData.put(k, v));
    rawData.forEach(userData::put);

    // System.out.println("Key = " + k + ", Value = " + v));

    /*
     * studentData.put(1, Stream.of("Roll No", "NAME",
     * "Year").collect(Collectors.toList())); studentData.put(2, Stream.of("128",
     * "Aditya", "2nd year").collect(Collectors.toList())); studentData.put(3,
     * Stream.of("129", "Narayana", "2nd year").collect(Collectors.toList()));
     * studentData.put(4, Stream.of("130", "Mohan",
     * "2nd year").collect(Collectors.toList()));
     */

    Set<Integer> keyid = userData.keySet();

    int rowid = 0;

    // writing the data into the sheets...

    for (Integer key : keyid) {

      row = spreadsheet.createRow(rowid++);
      List<String> objectArr = userData.get(key);
      int cellid = 0;

      for (Object obj : objectArr) {
        Cell cell = row.createCell(cellid++);
        cell.setCellValue((String) obj);
      }
    }

    // .xlsx is the format for Excel Sheets...
    // writing the workbook into the file...
    FileOutputStream out;
    try {
      out = new FileOutputStream(pathToExportFile);

      workbook.write(out);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Map<String, String> getRowsAsMapByColumnsIndex(Map<Integer, List<String>> data, int keyColumn,
      int valueColumn) {
    Map<String, String> testData = new HashMap<>();
    for (Integer key : data.keySet()) {
      // Skip key == 0 because is the header row
      if (!(key == 0)) {
        testData.put(data.get(key).get(keyColumn), data.get(key).get(valueColumn));
      }
    }

    return testData;
  }

  public Map<String, String> getRowsAsMapByFirstTwoColumns(Map<Integer, List<String>> data) {
    return getRowsAsMapByColumnsIndex(data, 0, 1);
  }

  public Map<String, String> getUserEmailWithPointsFromSpreadsheet(String pathToExcelFile) {
    return getRowsAsMapByFirstTwoColumns(getRawDataFromExcel(pathToExcelFile));
  }
}
