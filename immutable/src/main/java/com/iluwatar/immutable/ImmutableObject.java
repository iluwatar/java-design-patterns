package com.iluwatar.immutable;
import java.util.Objects;

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
    	this.name = Objects.requireNonNull(name, "name cannot be null");
        this.age = age;
        this.email = Objects.requireNonNull(email, "email cannot be null");
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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ImmutableObject)) return false;
        ImmutableObject that = (ImmutableObject) o;
        return age == that.age
            && Objects.equals(name, that.name)
            && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, email);
    }
}