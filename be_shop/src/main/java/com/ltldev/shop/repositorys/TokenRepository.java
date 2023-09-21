package com.ltldev.shop.repositorys;

import com.ltldev.shop.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Long> {
}
