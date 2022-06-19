package dparant.exSpring.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for the country of a UserRequest, he must be french
 *
 * @author dylan
 * @see AgeConstraint
 */
public class CountryValidator implements ConstraintValidator<CountryConstraint, String> {
    /**
     * Determines whether the country is valid
     *
     * @param country                    country to validate
     * @param constraintValidatorContext Validation context
     * @return Boolean True if the UserRequest is french, False otherwise
     */
    @Override
    public boolean isValid(String country, ConstraintValidatorContext constraintValidatorContext) {
        if (country == null) {
            return false;
        }
        return "france".equalsIgnoreCase(country);
    }
}