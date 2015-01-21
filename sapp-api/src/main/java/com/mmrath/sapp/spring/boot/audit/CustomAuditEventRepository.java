package com.mmrath.sapp.spring.boot.audit;

import com.mmrath.sapp.domain.PersistentAuditEvent;
import com.mmrath.sapp.repository.PersistenceAuditEventRepository;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.AuditEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by murali on 18/01/15.
 */
@Repository
public class CustomAuditEventRepository {

  @Autowired
  private PersistenceAuditEventRepository persistenceAuditEventRepository;

  @Bean
  public AuditEventRepository auditEventRepository() {
    return new AuditEventRepository() {

      @Autowired
      private AuditEventConverter auditEventConverter;

      @Override
      public List<AuditEvent> find(String principal, Date after) {
        Iterable<PersistentAuditEvent> persistentAuditEvents;
        if (principal == null && after == null) {
          persistentAuditEvents = persistenceAuditEventRepository.findAll();
        } else if (after == null) {
          persistentAuditEvents = persistenceAuditEventRepository.findByPrincipal(principal);
        } else {
          persistentAuditEvents =
              persistenceAuditEventRepository.findByPrincipalAndAuditEventDateAfter(principal, new LocalDateTime(after));
        }
        return auditEventConverter.convertToAuditEvent(persistentAuditEvents);
      }

      @Override
      public void add(AuditEvent event) {
        PersistentAuditEvent persistentAuditEvent = new PersistentAuditEvent();
        persistentAuditEvent.setPrincipal(event.getPrincipal());
        persistentAuditEvent.setAuditEventType(event.getType());
        persistentAuditEvent.setAuditEventDate(new LocalDateTime(event.getTimestamp()));
        persistentAuditEvent.setData(auditEventConverter.convertDataToStrings(event.getData()));

        persistenceAuditEventRepository.save(persistentAuditEvent);
      }
    };
  }
}
