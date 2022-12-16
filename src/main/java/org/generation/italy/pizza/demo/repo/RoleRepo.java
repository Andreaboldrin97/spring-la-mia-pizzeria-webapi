package org.generation.italy.pizza.demo.repo;

import org.generation.italy.pizza.demo.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RoleRepo extends JpaRepository<Role, Integer>{

}
