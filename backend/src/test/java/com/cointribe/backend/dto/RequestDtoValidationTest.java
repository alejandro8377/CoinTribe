package com.cointribe.backend.dto;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RequestDtoValidationTest {

    private static jakarta.validation.ValidatorFactory validatorFactory;
    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    static void closeValidatorFactory() {
        validatorFactory.close();
    }

    @Test
    void validatesRequiredRegistrationFieldsAndFormats() {
        UserRegistrationRequest invalid = new UserRegistrationRequest("", "not-an-email", "123");

        var violations = validator.validate(invalid);

        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("nombre")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("email")));
        assertTrue(violations.stream().anyMatch(violation -> violation.getPropertyPath().toString().equals("password")));
    }

    @Test
    void preservesPartialUpdatesWhileValidatingCreateRequests() {
        AchievementRequest partialUpdate = new AchievementRequest(null, null, null, null, null, null, null);

        assertTrue(validator.validate(partialUpdate, ValidationGroups.Update.class).isEmpty());
        assertTrue(validator.validate(partialUpdate, ValidationGroups.Create.class).size() >= 2);
    }

    @Test
    void validatesPostContentForCreateAndUpdateGroups() {
        PostCreateRequest invalidCreate = new PostCreateRequest(null, null, null, null);
        PostRequest invalidUpdate = new PostRequest(null, null, "tiny", null);

        assertTrue(validator.validate(invalidCreate).size() >= 3);
        assertFalse(validator.validate(invalidUpdate).isEmpty());
        assertTrue(validator.validate(new PostRequest(null, null, null, null)).isEmpty());
    }
}
