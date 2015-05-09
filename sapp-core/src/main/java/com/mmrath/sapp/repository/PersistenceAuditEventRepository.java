package com.mmrath.sapp.repository;

import com.mmrath.sapp.domain.PersistentAuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

/**
 * Created by murali on 18/01/15.
 */
public interface PersistenceAuditEventRepository extends JpaRepository<PersistentAuditEvent, Long> {
  List<PersistentAuditEvent> findByPrincipal(String principal);

  List<PersistentAuditEvent> findByPrincipalAndAuditEventDateAfter(String principal, Instant after);

  List<PersistentAuditEvent> findAllByAuditEventDateBetween(Instant fromDate, Instant toDate);
}
