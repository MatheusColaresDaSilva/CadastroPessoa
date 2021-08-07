package com.elotech.persist;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy=AtLeastOneNotNullValidator.class)
public @interface AtLeastOneNotNull {

    String message() default "List must contain on element at least";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
