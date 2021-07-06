# Backend Framework Validation

### Setup
```xml
<dependency>
  <groupId>io.github.laminalfalah</groupId>
  <artifactId>backend-framework-validation</artifactId>
</dependency>
```

### Create Custom Validation
```java
/**
 * Spring Boot WebFlux
 */
public class CustomValidator extends AbstractReactiveConstraintValidator<CustomAnnotation, String> {

   @Override
   public Mono<Boolean> validate(String value, CustomAnnotation annotation, ConstraintValidatorContext context) {
     return Mono.fromCallable(() -> "Java".equals(value));
   }
}

/**
 * Spring Boot MVC
 */
public class CustomValidator extends AbstractConstraintValidator<CustomAnnotation, String> {

    @Override
    public Mono<Boolean> validate(String value, CustomAnnotation annotation, ConstraintValidatorContext context) {
        return "Java".equals(value);
    }
}
```