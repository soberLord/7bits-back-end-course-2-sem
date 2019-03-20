package it.sevenbits.homework.core.repository.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValidator implements
        ConstraintValidator<EnumConstraint, String> {

    private EnumConstraint annotation;
    @Override
    public void initialize(EnumConstraint constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;

        Object[] enumValues = this.annotation.enumClass().getEnumConstants();

        if(enumValues != null)
        {
            for(Object enumValue:enumValues)
            {
                if(value.equals(enumValue.toString()))
                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
