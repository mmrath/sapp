package com.mmrath.sapp.repository;

import com.mmrath.sapp.domain.PersistentAuditEvent;
import com.mmrath.sapp.domain.security.Permission;
import org.joda.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by murali on 18/01/15.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {
  List<PersistentAuditEvent> findByPrincipal(String principal);

  List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, LocalDateTime after);

  List<PersistentAuditEvent> findAllByAuditEventDateBetween(LocalDateTime fromDate, LocalDateTime toDate);
}
