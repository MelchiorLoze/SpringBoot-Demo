package com.example.demo.list_product.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;

final class ListProductCreateRequestDtoValidator
        implements ConstraintValidator<ValidListProductCreateRequestDto, ListProductCreateRequestDto> {

    @Override
    public boolean isValid(ListProductCreateRequestDto dto, ConstraintValidatorContext context) {
        if (dto.isCreateProduct()) {
            return dto.getProductName() != null && !dto.getProductName().trim().isEmpty() && dto.getProductId() == null;
        } else {
            return dto.getProductId() != null && dto.getProductName() == null;
        }
    }
}

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ListProductCreateRequestDtoValidator.class)
public @interface ValidListProductCreateRequestDto {
    String message() default "Invalid list product creation request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}