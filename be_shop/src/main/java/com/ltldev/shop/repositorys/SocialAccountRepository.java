package com.ltldev.shop.repositorys;

import com.ltldev.shop.models.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount,Long> {
}
