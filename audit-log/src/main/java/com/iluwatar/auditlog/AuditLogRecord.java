package com.iluwatar.auditlog;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Holder for record elements to be held in the audit log xml.
 */
@XmlRootElement
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@EqualsAndHashCode
public class AuditLogRecord {
  private String dateGiven;
  private String dateRecordChanged;
  private String objectType;
  private String objectName;
  private String variableType;
  private String variableName;
  private String valueOld;
  private String valueNew;
}
