package com.hexacta.updatecoins.service;

import com.hexacta.updatecoins.dao.entities.WpUserMeta;
import com.hexacta.updatecoins.dao.entities.WpUsers;
import com.hexacta.updatecoins.dto.UserDTO;
import com.hexacta.updatecoins.util.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
  @Autowired
  WpUserMetaServiceImpl wpUserMetaService;
  @Autowired
  WpUsersServiceImpl wpUsersService;
  @Autowired
  ExcelUtility excelUtility;

  public List<UserDTO> getUsersWithPointsFromDB() {
    List<WpUserMeta> wpUsersMeta = wpUserMetaService.findByMetaKey("initial_points");
    List<WpUsers> wpUsers = wpUsersService.findAll();
    List<Long> wpUsersMetaIds = wpUsersMeta.stream().map(WpUserMeta::getUserId).collect(Collectors.toList());
    List<UserDTO> usersDTO = new ArrayList<>();

    wpUsers.stream().forEach(u -> {
      if (wpUsersMetaIds.contains(u.getId())) {
        UserDTO user = new UserDTO();
        user.setUserEmail(u.getUserEmail());
        user.setDisplayName(u.getDisplayName());
        user.setId(u.getId());
        WpUserMeta userMeta = wpUsersMeta.stream()
            .filter(um -> um.getUserId().equals(u.getId()))
            .findAny()
            .orElse(null);
        user.setInitialPoints(userMeta.getMetaValue());
        usersDTO.add(user);
      }
    });

    return usersDTO;
  }

  public List<UserDTO> matchUsersWithEmail(List<UserDTO> userDTOS) throws IOException {
    Map<String, String> testData = new HashMap<String, String>();
    String pathToFile = "C:/Users/fsuarez/Desktop/IN PROGRESS/Creditcoins/App/data-excel/usersByEmailWithCoins.xlsx";
    testData = excelUtility.getUserEmailWithPointsFromSpreadsheet(pathToFile);
    //System.out.println(users);
    System.out.println(testData.entrySet());
    Map<String, String> finalTestData = testData;

    //Filter users retrieved from DB by email from excel file
    List<UserDTO> filteredUserDTOS = userDTOS.stream()
        .filter(u -> finalTestData.entrySet().stream().anyMatch(
            e -> e.getKey().equalsIgnoreCase(u.getUserEmail())
        )).collect(Collectors.toList());
    System.out.println(filteredUserDTOS);
    return filteredUserDTOS;
  }

  public void updatePointsFromExcel(String pathToExcelFile) {
    List<UserDTO> userDTOS = getUsersWithPointsFromDB();
    Map<String, String> excelData;

    List<UserDTO> updatedUsers = new ArrayList<>();
    excelData = excelUtility.getUserEmailWithPointsFromSpreadsheet(pathToExcelFile);
    userDTOS.stream()
        .forEach(u -> {
          excelData.entrySet()
              .forEach(e -> {
                if (e.getKey().equalsIgnoreCase(u.getUserEmail())) {
                  UserDTO user = new UserDTO();
                  user = u;
                  user.setInitialPoints( e.getValue());
                  updatedUsers.add(user);
                }
              });
        });
    System.out.println(userDTOS);
    System.out.println(updatedUsers);
    updateUsersCoinsInDB(updatedUsers);
    List<UserDTO> lista = getUsersWithPointsFromDB();
    System.out.println(lista);

  }

  private void updateUsersCoinsInDB(List<UserDTO> usersDTOs){
    for (UserDTO usersDTO : usersDTOs) {
      WpUserMeta wpUserMeta = wpUserMetaService.findByUserIdAndMetaKey(usersDTO.getId(), "initial_points");
      wpUserMeta.setMetaValue(usersDTO.getInitialPoints());
      wpUserMetaService.save(wpUserMeta);
    }
  }

}
