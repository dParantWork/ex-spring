package dparant.exSpring.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Constraint for the age of a User
 *
 * @author dylan
 */
@Constraint(validatedBy = AgeValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
@Documented
public @interface AgeConstraint {
    String message() default "The user is not 18.";
    Class <?> [] groups() default {};
    Class <? extends Payload> [] payload() default {};
}