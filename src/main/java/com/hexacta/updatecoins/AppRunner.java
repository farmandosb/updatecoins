package com.hexacta.updatecoins;

import com.hexacta.updatecoins.service.UserService;
import com.hexacta.updatecoins.util.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {

  @Autowired
  UserService userService;

  @Autowired
  ExcelUtility excelUtility;

  @Override
  public void run(String... args) throws Exception {

    String pathToFile = "./";
    String importFileName = "usersByEmailWithCoins.xlsx";
    userService.updateUsersPointsInDBFromExcelFile(pathToFile + importFileName);
  }
}



