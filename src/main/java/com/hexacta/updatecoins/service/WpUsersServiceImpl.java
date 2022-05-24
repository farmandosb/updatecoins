package com.hexacta.updatecoins.service;

import com.hexacta.updatecoins.dao.entities.WpUsers;
import com.hexacta.updatecoins.dao.jpa.IWpUsersDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WpUsersServiceImpl implements IWpUsersService {

  @Autowired
  private IWpUsersDAO wpUsersDAO;

  @Override
  @Transactional(readOnly = true)
  public List<WpUsers> findAll() {
    return (List<WpUsers>) wpUsersDAO.findAll();
  }

  @Override
  @Transactional(readOnly = true)
  public WpUsers findById(Long id) {
    return wpUsersDAO.findById(id).orElse(null);
  }

  @Override
  @Transactional
  public WpUsers save(WpUsers wpUsers) {
    return wpUsersDAO.save(wpUsers);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    wpUsersDAO.deleteById(id);
  }
}
