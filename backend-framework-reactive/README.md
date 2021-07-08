# Backend Framework Reactive

### Setup
```xml
<dependency>
    <groupId>io.github.laminalfalah</groupId>
    <artifactId>backend-framework-reactive</artifactId>
    <version>...</version>
</dependency>
```

### Create Own ErrorController
```java
@Slf4j
@ControllerAdvice
@RestControllerAdvice
public class ExampleErrorController implements ErrorController {

    @Setter
    @Getter
    private MessageSource messageSource;

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public ErrorHelper getErrorHelper() {
        return new ErrorHelper(log);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TestingException.class)
    public ResponseEntity<Response<Object>> testingException(TestingException e) {
        getLogger().error(TestingException.class.getName(), e);

        return ResponseEntity.badRequest().body(ResponseHelper.set(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
```