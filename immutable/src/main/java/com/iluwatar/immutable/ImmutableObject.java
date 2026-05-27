package com.iluwatar.immutable;

/**
 * Immutable Object pattern.
 * Once created, the object state cannot be changed.
 */
public final class ImmutableObject {

    private final String name;
    private final int age;
    private final String email;

    public ImmutableObject(
            final String name,
            final int age,
            final String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return "ImmutableObject{"
            + "name='" + name + '\''
            + ", age=" + age
            + ", email='" + email + '\''
            + '}';
    }
}