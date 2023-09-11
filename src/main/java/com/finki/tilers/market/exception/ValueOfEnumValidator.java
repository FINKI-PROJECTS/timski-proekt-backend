package com.finki.tilers.market.exception;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
  Implements ConstraintValidator so when the annotation ValueOfEnum is used to
  check if the provided value it's an element of that Enum
 */
class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, Object> {
    private List<String> acceptedValues;

    @Override
    /*
      Gets all possible values string format which belong to an enum and saves
      them to another variable
     */
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
//                .map(Enum::name)
                .map(Enum::toString)
                .collect(Collectors.toList());
    }

    @Override
    /*
      Check if the provided value its part of the possible values of an enum
      (string representation)
     */
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(value.toString());
    }
}
