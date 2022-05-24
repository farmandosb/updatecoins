package com.hexacta.updatecoins.service;

import com.hexacta.updatecoins.dao.entities.WpUserMeta;
import com.hexacta.updatecoins.dao.entities.WpUsers;
import com.hexacta.updatecoins.dto.UserDTO;
import com.hexacta.updatecoins.util.ExcelUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

  public void updateUsersPointsInDBFromExcelFile(String pathToExcelFile) {
    List<UserDTO> usersDTOBeforeUpdate = getUsersWithPointsFromDB();
    System.out.println(usersDTOBeforeUpdate);

    Map<Integer, List<String>> excelData = excelUtility.getRawDataFromExcel(pathToExcelFile);
    Map<Integer, List<String>> dataToExport = new HashMap<>();

    List<UserDTO> usersFoundByEmail = new ArrayList<>();
    excelData.entrySet().forEach(e -> {
      List<String> columns = e.getValue();
      int columnsSize = columns.size();
      usersDTOBeforeUpdate.forEach(u -> {
        if (e.getValue().get(0).equalsIgnoreCase(u.getUserEmail())) {
          String newPoints = e.getValue().get(1);
          String oldPoints = u.getInitialPoints();
          if (!newPoints.equalsIgnoreCase(oldPoints)) {
            WpUserMeta wpUserMeta = wpUserMetaService.findByUserIdAndMetaKey(u.getId(), "initial_points");
            columns.add(oldPoints);
            u.setInitialPoints(newPoints);
            wpUserMeta.setMetaValue(newPoints);
            wpUserMetaService.save(wpUserMeta);
          }
        }
      });
      if (columns.size() == columnsSize) {
        columns.add("not updated");
      }
      dataToExport.put(e.getKey(), columns);
    });

    System.out.println(usersDTOBeforeUpdate);
    System.out.println(usersFoundByEmail);

    List<UserDTO> usersDTOAfterUpdate = getUsersWithPointsFromDB();
    excelUtility.exportDataToExcel(dataToExport);
    System.out.println(usersDTOAfterUpdate);
  }

  private void updateUsersCoinsInDB(List<UserDTO> usersDTOs) {
    for (UserDTO usersDTO : usersDTOs) {
      WpUserMeta wpUserMeta = wpUserMetaService.findByUserIdAndMetaKey(usersDTO.getId(), "initial_points");
      wpUserMeta.setMetaValue(usersDTO.getInitialPoints());
      wpUserMetaService.save(wpUserMeta);
    }
  }

  private void updateUserCoinsInDB(UserDTO usersDTO) {
    WpUserMeta wpUserMeta = wpUserMetaService.findByUserIdAndMetaKey(usersDTO.getId(), "initial_points");
    wpUserMeta.setMetaValue(usersDTO.getInitialPoints());
    wpUserMetaService.save(wpUserMeta);
  }
}
