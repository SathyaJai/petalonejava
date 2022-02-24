package com.petsalone.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.petsalone.core.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}
