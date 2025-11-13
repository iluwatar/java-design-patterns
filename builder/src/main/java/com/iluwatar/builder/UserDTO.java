/*
 * The MIT License
 * Copyright © 2014-2021 Ilkka Seppälä
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.iluwatar.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 실무 예제: 사용자 정보 DTO (Data Transfer Object)
 *
 * <p>UserDTO는 사용자 정보를 전송하기 위한 객체로, Builder 패턴을 사용하여
 * 복잡한 사용자 정보를 단계적으로 구성할 수 있습니다.
 *
 * <h2>실무 활용 사례:</h2>
 * <ul>
 *   <li>API 요청/응답 DTO 생성</li>
 *   <li>데이터베이스 엔티티에서 DTO로 변환</li>
 *   <li>복잡한 사용자 프로필 구성</li>
 *   <li>테스트 데이터 생성</li>
 * </ul>
 *
 * <h2>사용 예제:</h2>
 * <pre>
 * UserDTO user = UserDTO.builder()
 *     .id(1L)
 *     .username("john.doe")
 *     .email("john@example.com")
 *     .firstName("John")
 *     .lastName("Doe")
 *     .age(30)
 *     .phoneNumber("010-1234-5678")
 *     .addRole("USER")
 *     .addRole("ADMIN")
 *     .build();
 * </pre>
 *
 * <h2>장점:</h2>
 * <ul>
 *   <li>가독성 높은 객체 생성 코드</li>
 *   <li>선택적 파라미터 쉽게 처리</li>
 *   <li>불변(Immutable) 객체 생성 가능</li>
 *   <li>객체 생성 과정과 표현 분리</li>
 * </ul>
 */
public final class UserDTO {

  private final Long id;
  private final String username;
  private final String email;
  private final String firstName;
  private final String lastName;
  private final Integer age;
  private final String phoneNumber;
  private final String address;
  private final LocalDate birthDate;
  private final boolean active;
  private final List<String> roles;

  /**
   * Private 생성자 - Builder를 통해서만 객체 생성 가능.
   */
  private UserDTO(Builder builder) {
    this.id = builder.id;
    this.username = builder.username;
    this.email = builder.email;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.age = builder.age;
    this.phoneNumber = builder.phoneNumber;
    this.address = builder.address;
    this.birthDate = builder.birthDate;
    this.active = builder.active;
    this.roles = Collections.unmodifiableList(new ArrayList<>(builder.roles));
  }

  /**
   * Builder 인스턴스 생성.
   *
   * @return UserDTO Builder
   */
  public static Builder builder() {
    return new Builder();
  }

  // Getters
  public Long getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public String getEmail() {
    return email;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFullName() {
    if (firstName != null && lastName != null) {
      return firstName + " " + lastName;
    }
    return username;
  }

  public Integer getAge() {
    return age;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public String getAddress() {
    return address;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public boolean isActive() {
    return active;
  }

  public List<String> getRoles() {
    return roles;
  }

  public boolean hasRole(String role) {
    return roles.contains(role);
  }

  @Override
  public String toString() {
    return "UserDTO{"
        + "id=" + id
        + ", username='" + username + '\''
        + ", email='" + email + '\''
        + ", fullName='" + getFullName() + '\''
        + ", age=" + age
        + ", active=" + active
        + ", roles=" + roles
        + '}';
  }

  /**
   * UserDTO Builder 클래스.
   */
  public static class Builder {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Integer age;
    private String phoneNumber;
    private String address;
    private LocalDate birthDate;
    private boolean active = true;
    private List<String> roles = new ArrayList<>();

    /**
     * 사용자 ID 설정.
     *
     * @param id 사용자 ID
     * @return Builder
     */
    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    /**
     * 사용자명 설정 (필수).
     *
     * @param username 사용자명
     * @return Builder
     */
    public Builder username(String username) {
      this.username = username;
      return this;
    }

    /**
     * 이메일 설정 (필수).
     *
     * @param email 이메일
     * @return Builder
     */
    public Builder email(String email) {
      this.email = email;
      return this;
    }

    /**
     * 이름 설정.
     *
     * @param firstName 이름
     * @return Builder
     */
    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    /**
     * 성 설정.
     *
     * @param lastName 성
     * @return Builder
     */
    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    /**
     * 나이 설정.
     *
     * @param age 나이
     * @return Builder
     */
    public Builder age(Integer age) {
      this.age = age;
      return this;
    }

    /**
     * 전화번호 설정.
     *
     * @param phoneNumber 전화번호
     * @return Builder
     */
    public Builder phoneNumber(String phoneNumber) {
      this.phoneNumber = phoneNumber;
      return this;
    }

    /**
     * 주소 설정.
     *
     * @param address 주소
     * @return Builder
     */
    public Builder address(String address) {
      this.address = address;
      return this;
    }

    /**
     * 생년월일 설정.
     *
     * @param birthDate 생년월일
     * @return Builder
     */
    public Builder birthDate(LocalDate birthDate) {
      this.birthDate = birthDate;
      return this;
    }

    /**
     * 활성화 상태 설정.
     *
     * @param active 활성화 여부
     * @return Builder
     */
    public Builder active(boolean active) {
      this.active = active;
      return this;
    }

    /**
     * 역할 추가.
     *
     * @param role 역할
     * @return Builder
     */
    public Builder addRole(String role) {
      this.roles.add(role);
      return this;
    }

    /**
     * 여러 역할 추가.
     *
     * @param roles 역할 목록
     * @return Builder
     */
    public Builder roles(List<String> roles) {
      this.roles.addAll(roles);
      return this;
    }

    /**
     * UserDTO 객체 생성.
     * 필수 필드 유효성 검증 수행.
     *
     * @return UserDTO 객체
     * @throws IllegalStateException 필수 필드가 없는 경우
     */
    public UserDTO build() {
      if (username == null || username.trim().isEmpty()) {
        throw new IllegalStateException("Username is required");
      }
      if (email == null || email.trim().isEmpty()) {
        throw new IllegalStateException("Email is required");
      }
      if (!email.contains("@")) {
        throw new IllegalStateException("Invalid email format");
      }
      if (age != null && age < 0) {
        throw new IllegalStateException("Age cannot be negative");
      }
      return new UserDTO(this);
    }
  }
}
