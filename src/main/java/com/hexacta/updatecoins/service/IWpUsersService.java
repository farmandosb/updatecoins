package com.hexacta.updatecoins.service;

import com.hexacta.updatecoins.dao.entities.WpUsers;

import java.util.List;

public interface IWpUsersService {

  public List<WpUsers> findAll();

  public WpUsers findById(Long id);

  public WpUsers save(WpUsers wpUsers);

  public void delete(Long id);
}
