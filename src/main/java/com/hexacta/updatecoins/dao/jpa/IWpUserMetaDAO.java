package com.hexacta.updatecoins.dao.jpa;

import com.hexacta.updatecoins.dao.entities.WpUserMeta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IWpUserMetaDAO extends JpaRepository<WpUserMeta, Long> {
  WpUserMeta findByUserId(Long userId);

  List<WpUserMeta> findByMetaKey(String metaKey);

  WpUserMeta findByUserIdAndMetaKey(Long userId, String metaKey);
}

