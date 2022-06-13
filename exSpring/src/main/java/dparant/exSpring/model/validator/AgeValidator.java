package dparant.exSpring.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Validator for the age of a User
 *
 * @see AgeConstraint
 *
 * @author dylan
 */
public class AgeValidator implements ConstraintValidator<AgeConstraint, LocalDate> {
    /**
     * Determines whether the age is valid
     *
     * @param localDate Date to validate
     * @param constraintValidatorContext Validation context
     *
     * @return Boolean True if the User is over 18, False otherwise
     */
    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return localDate.until(LocalDate.now(), ChronoUnit.YEARS) >= 18;
    }
}