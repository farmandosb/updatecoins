package com.hexacta.updatecoins;

import com.hexacta.updatecoins.dto.UserDTO;
import com.hexacta.updatecoins.service.UserService;
import com.hexacta.updatecoins.util.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class AppRunner implements CommandLineRunner {

  @Autowired
  UserService userService;

  @Autowired
  ExcelUtility excelUtility;

  @Override
  public void run(String... args) throws Exception {

    System.out.println("In CommandLineRunnerImpl ");

    String pathToFile = "C:/Users/fsuarez/Desktop/IN PROGRESS/Creditcoins/App/data-excel/";
    String importFileName = "usersByEmailWithCoins.xlsx";
    String exportFileName = "prueba01.xlsx";

    List<UserDTO> lista = userService.getUsersWithPointsFromDB();
    userService.updatePointsFromExcel(lista, pathToFile + importFileName);

    Map<Integer, List<String>> rawMap;
    rawMap = excelUtility.getRawDataFromExcel(pathToFile + importFileName);

    Map<String, String> processedMap = excelUtility.getUserEmailWithPointsFromSpreadsheet(pathToFile + importFileName);
    Map<Integer, List<String>> newRawMap;
    newRawMap = excelUtility.parseMapStringStringToMapIntegerListString(processedMap);
    excelUtility.exportToExcel(newRawMap);
  }
}



