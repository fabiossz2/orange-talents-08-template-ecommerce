package br.com.zupacademy.fabio.ecommerce.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {ExistsIdValidator.class})
@Target({FIELD, PARAMETER})
@Retention(RUNTIME)
public @interface ExistsId {

    String message() default "{br.com.zupacademy.fabio.ecommerce.validator.ExistsId.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fieldName();

    Class<?> domainClass();

}
