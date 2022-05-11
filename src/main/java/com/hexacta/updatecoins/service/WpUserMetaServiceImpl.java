package com.hexacta.updatecoins.service;

import com.hexacta.updatecoins.dao.entities.WpUserMeta;
import com.hexacta.updatecoins.dao.jpa.IWpUserMetaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WpUserMetaServiceImpl implements IWpUserMetaService {
  @Autowired
  private IWpUserMetaDAO wpUserMetaDAO;

  @Override
  @Transactional
  public List<WpUserMeta> findALl() {
    return (List<WpUserMeta>) wpUserMetaDAO.findAll();
  }

  @Override
  @Transactional
  public WpUserMeta findById(Long id) {
    return wpUserMetaDAO.findById(id).orElse(null);
  }

  @Override
  public WpUserMeta findByUserId(Long userId) {
    return wpUserMetaDAO.findByUserId(userId);
  }

  @Override
  public List<WpUserMeta> findByMetaKey(String metaKey) {
    return wpUserMetaDAO.findByMetaKey(metaKey);
  }

  @Override
  public WpUserMeta findByUserIdAndMetaKey(Long userId, String metaKey) {
    return wpUserMetaDAO.findByUserIdAndMetaKey(userId, metaKey);
  }

  @Override
  @Transactional
  public WpUserMeta save(WpUserMeta wpUserMeta) {
    return wpUserMetaDAO.save(wpUserMeta);
  }

  @Override
  @Transactional
  public void delete(Long id) {
    wpUserMetaDAO.deleteById(id);
  }
}
