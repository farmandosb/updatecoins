package com.hexacta.updatecoins.dao.jpa;

import com.hexacta.updatecoins.dao.entities.WpUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IWpUsersDAO extends JpaRepository<WpUsers, Long>{
}
