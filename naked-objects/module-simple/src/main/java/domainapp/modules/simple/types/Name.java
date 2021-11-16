package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Inject;
import javax.jdo.annotations.Column;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.services.i18n.TranslatableString;
import org.apache.isis.applib.spec.AbstractSpecification2;

import lombok.val;

import domainapp.modules.simple.SimpleModule;

@Column(length = Name.MAX_LEN, allowsNull = "false")
@Property(mustSatisfy = Name.Specification.class, maxLength = Name.MAX_LEN)
@Parameter(mustSatisfy = Name.Specification.class, maxLength = Name.MAX_LEN)
@ParameterLayout(named = "Name")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    int MAX_LEN = 40;

    class Specification extends AbstractSpecification2<String> {

        @Inject
        private SimpleModule.Configuration configuration;

        @Override
        public TranslatableString satisfiesTranslatableSafely(final String name) {
            if(name == null) {
                return null;
            }
            val prohibitedCharacters = configuration.getTypes().getName().getValidation().getProhibitedCharacters();
            for (char prohibitedCharacter : prohibitedCharacters) {
                if( name.contains(""+prohibitedCharacter)) {
                    String message = configuration.getTypes().getName().getValidation().getMessage();
                    return TranslatableString.tr(message, "character", ""+prohibitedCharacter);
                }
            }
            return null;
        }
    }
}
