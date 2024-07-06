package kz.projects.atmSystem.repositories;

import kz.projects.atmSystem.model.Permissions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permissions, Long> {
  Permissions findByRole(String role);
}
