package com.hexacta.updatecoins.service;

import com.hexacta.updatecoins.dao.entities.WpUserMeta;

import java.util.List;

public interface IWpUserMetaService {
  public List<WpUserMeta> findALl();

  public WpUserMeta findById(Long id);

  public WpUserMeta findByUserId(Long userId);

  public List<WpUserMeta> findByMetaKey(String metaKey);

  public WpUserMeta findByUserIdAndMetaKey(Long userId, String metaKey);

  public WpUserMeta save(WpUserMeta wpUserMeta);

  public void delete(Long id);
}
