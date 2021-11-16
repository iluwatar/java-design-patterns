package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.annotation.Where;

@Column(length = Notes.MAX_LEN, allowsNull = "true")
@Property(editing = Editing.ENABLED, maxLength = Notes.MAX_LEN)
@PropertyLayout(named = "Notes", multiLine = 10, hidden = Where.ALL_TABLES)
@Parameter(maxLength = Notes.MAX_LEN)
@ParameterLayout(named = "Notes", multiLine = 10)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Notes {

    int MAX_LEN = 4000;

}
