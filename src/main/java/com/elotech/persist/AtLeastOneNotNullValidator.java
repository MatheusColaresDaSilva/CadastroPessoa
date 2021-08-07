package com.elotech.persist;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

public class AtLeastOneNotNullValidator implements ConstraintValidator<AtLeastOneNotNull, Collection> {


    @Override
    public void initialize(AtLeastOneNotNull constraintAnnotation) {

    }

    @Override
    public boolean isValid(Collection collection, ConstraintValidatorContext constraintValidatorContext) {
        if (collection == null) {
            return false;
        } else {
            return collection.size() > 0;
        }
    }
}
