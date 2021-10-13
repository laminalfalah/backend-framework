# Backend Framework MVC

### Setup
```xml
<dependency>
    <groupId>io.github.laminalfalah</groupId>
    <artifactId>backend-framework-mvc</artifactId>
    <version>...</version>
</dependency>
```

### Example Controller
```java
@RestController
@RequestMapping(path = "/example", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class ExampleController {

    private final ExampleService service;

    @GetMapping
    @Authentication(bypassMiddleware = true) // Without Authentication
    public ResponseEntity<?> index(Filter<ExampleFilter> filter) {
        return ResponseEntity.ok(service.index(filter));
    }
    
    @GetMapping("/{id}")
    @Authentication // With Authentication
    public ResponseEntity<?> show(@PathVariable("id") int id) {
        return ResponseEntity.ok(service.show(id));
    }
}
```
Noted: Untuk penggunaan `@Authentication` pada controller. jangan lupa membuat class 
implementation seperti dibawah ini.
### Create Interceptor for app
```java
@Component
public class CustomInterceptor implements AuthenticationInterceptor {
    
    @Override
    public boolean isPreHandle(Authentication authentication, String token) {
        // logic for authentication
        return token.equals("1234567890");
    }
}
```
Noted: Kemudian lakukan registry class `CustomInterceptor` ini pada method addInterceptors dari
interface WebMvcConfigurer.

```java
@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Bean
    public AuthenticationInterceptor getAuthenticationInterceptor() {
        return new CustomInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthenticationInterceptor());
    }
}
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TestingException.class)
    public ResponseEntity<Response<Object>> testingException(TestingException e) {
        getLogger().error(TestingException.class.getName(), e);

        return ResponseEntity.badRequest().body(ResponseHelper.set(HttpStatus.BAD_REQUEST, e.getMessage()));
    }
}
```