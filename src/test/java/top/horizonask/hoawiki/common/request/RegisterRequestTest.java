package top.horizonask.hoawiki.common.request;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import top.horizonask.hoawiki.authorization.request.RegisterRequest;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
public class RegisterRequestTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("Register Request with right data.")
    public void testRegisterRequest() {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("UserName");
        registerRequest.setPassword("11111111");
        registerRequest.setUserEmail("User@test.com");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertTrue(violations.isEmpty());
    }

    @ParameterizedTest
    @DisplayName("Register Request with illegal userName data.")
    @ValueSource(strings = {";","","\n","*","#","(",")","\\","~",",","$"})
    public void testRegisterRequestUserNameWrong(String string) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName(string);
        registerRequest.setPassword("11111111");
        registerRequest.setUserEmail("User@test.com");
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertFalse(violations.isEmpty());
        for (ConstraintViolation<RegisterRequest> violation : violations) {
            assertEquals(violation.getPropertyPath().toString(), "userName");
        }
    }

    @ParameterizedTest
    @DisplayName("Register Request with illegal userEmail data.")
    @ValueSource(strings = {"","User@test@com.com","Usertest.com","User@test.com\n","*","#","(",")","\\","~",",","$"})
    public void testRegisterRequestUserEmailWrong(String string) {
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUserName("UserName");
        registerRequest.setPassword("11111111");
        registerRequest.setUserEmail(string);
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        assertFalse(violations.isEmpty());
        for (ConstraintViolation<RegisterRequest> violation : violations) {
            assertEquals(violation.getPropertyPath().toString(), "userEmail");
        }
    }
}