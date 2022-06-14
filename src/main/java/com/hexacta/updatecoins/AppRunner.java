package com.hexacta.updatecoins;

import com.hexacta.updatecoins.service.UserService;
import com.hexacta.updatecoins.util.ExcelUtility;
import com.hexacta.updatecoins.util.JarUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class AppRunner implements CommandLineRunner {

  @Autowired
  UserService userService;

  @Autowired
  ExcelUtility excelUtility;

  @Override
  public void run(String... args) throws IOException {

    String importFileName = "usersByEmailWithCoins.xlsx";
    ApplicationHome home = new ApplicationHome(this.getClass());

    File appDir = new File(".");

    if (JarUtilities.runningFromJar()) {
      // run in jar
      appDir = home.getDir();
    }

    if (JarUtilities.runningFromTarget()) {
      // run in ide
      appDir = home.getDir().getParentFile();
    }

    userService.updateUsersPointsInDBFromExcelFile(appDir + "/" + importFileName);
  }
}



