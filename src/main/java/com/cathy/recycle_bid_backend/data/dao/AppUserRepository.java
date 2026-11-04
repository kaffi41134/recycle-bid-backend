package com.cathy.recycle_bid_backend.data.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cathy.recycle_bid_backend.data.entity.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);
}
