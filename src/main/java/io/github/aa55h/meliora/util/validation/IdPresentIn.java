package io.github.aa55h.meliora.util.validation;

import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Set;
import java.util.UUID;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {IdPresentIn.SingleValidator.class, IdPresentIn.MultiValidator.class})
public @interface IdPresentIn {
    Class<? extends JpaRepository<?, UUID>> repository();
    String message() default "Entity with given id does not exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Component
    class SingleValidator implements ConstraintValidator<IdPresentIn, UUID> {
        
        private final ApplicationContext context;
        private JpaRepository<?, UUID> repository;

        public SingleValidator(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public boolean isValid(UUID value, ConstraintValidatorContext context) {
            return value != null && repository.existsById(value);
        }
        
        @Override
        public void initialize(IdPresentIn constraintAnnotation) {
            repository = context.getBean(constraintAnnotation.repository());
        }
    }

    @Component
    class MultiValidator implements ConstraintValidator<IdPresentIn, Set<UUID>> {

        private final ApplicationContext context;
        private JpaRepository<?, UUID> repository;

        public MultiValidator(ApplicationContext context) {
            this.context = context;
        }

        @Override
        public boolean isValid(Set<UUID> value, ConstraintValidatorContext context) {
            return value != null && repository.findAllById(value).size() == value.size();
        }

        @Override
        public void initialize(IdPresentIn constraintAnnotation) {
            repository = context.getBean(constraintAnnotation.repository());
        }
    }
}
