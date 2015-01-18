package com.mmrath.sapp.service;

import com.mmrath.sapp.domain.PersistentAuditEvent;
import com.mmrath.sapp.repository.AuditEventConverter;
import com.mmrath.sapp.repository.PersistenceAuditEventRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class AuditEventService {

  private final PersistenceAuditEventRepository persistenceAuditEventRepository;
  private final AuditEventConverter auditEventConverter;

  @Autowired
  public AuditEventService(PersistenceAuditEventRepository persistenceAuditEventRepository,
      AuditEventConverter auditEventConverter) {
    this.persistenceAuditEventRepository = persistenceAuditEventRepository;
    this.auditEventConverter = auditEventConverter;
  }

  public List<AuditEvent> findAll() {
    return auditEventConverter.convertToAuditEvent(persistenceAuditEventRepository.findAll());
  }

  public List<AuditEvent> findByDates(LocalDateTime fromDate, LocalDateTime toDate) {
    List<PersistentAuditEvent> persistentAuditEvents =
        persistenceAuditEventRepository.findAllByAuditEventDateBetween(fromDate, toDate);

    return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
  }
}