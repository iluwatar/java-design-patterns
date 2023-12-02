package com.iluwatar.health.check;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

/**
 * An entity class that represents a health check record in the database. This class is used to
 * persist the results of health checks performed by the `DatabaseTransactionHealthIndicator`.
 *
 * @author ydoksanbir
 */
@Entity
@Data
public class HealthCheck {

  /** The unique identifier of the health check record. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  /** The status of the health check. Possible values are "UP" and "DOWN". */
  @Column(name = "status")
  private String status;
}
