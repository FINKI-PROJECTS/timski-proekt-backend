package com.finki.tilers.market.exception;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
/*
  We define an annotation which accepts two parameters enumClass which accepts a class of enum
  and the second parameter typeOfEnum which is a String representing the name of the enum to
  display in the error message during validation if the provided value isn't a type of that enum
 */
public @interface ValueOfEnum {
    Class<? extends Enum<?>> enumClass();
    String typeOfEnum();
    String message() default "The entered value doesn't match any value of {typeOfEnum}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}