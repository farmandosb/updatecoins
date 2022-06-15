package com.hexacta.updatecoins.dao.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "wp_usermeta")
public class WpUserMeta {

  @Id
  @Column(name = "umeta_id")
  private Long umetaId;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "meta_key")
  private String metaKey;

  @Column(name = "meta_value")
  private String metaValue;

  public Long getUmetaId() {
    return umetaId;
  }

  public void setUmetaId(Long umetaId) {
    this.umetaId = umetaId;
  }

  public String getMetaKey() {
    return metaKey;
  }

  public void setMetaKey(String metaKey) {
    this.metaKey = metaKey;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getMetakey() {
    return metaKey;
  }

  public String getMetaValue() {
    return metaValue;
  }

  public void setMetaValue(String metaValue) {
    this.metaValue = metaValue;
  }

  @Override
  public String toString() {
    return String.format("%d %15s %15s\n", userId, metaKey, metaValue);
  }
}
