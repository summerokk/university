package com.att.validator.person;

import com.att.request.person.teacher.TeacherRegisterRequest;
import com.att.validator.person.impl.TeacherRegisterValidatorImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class TeacherRegisterValidatorTest {
    private final int MIN_PASSWORD_LENGTH = 6;
    private final TeacherRegisterValidator teacherRegisterValidator =
            new TeacherRegisterValidatorImpl(MIN_PASSWORD_LENGTH);

    @Test
    void teacherRegisterValidatorShouldNotThrowRuntimeExceptionIfTeacherRegisterRequestIsValid() {
        TeacherRegisterRequest teacherRegisterRequest = TeacherRegisterRequest.builder()
                .withFirstName("test")
                .withLastName("test")
                .withEmail("test@test.ru")
                .withPassword("te1dsf12sdfg")
                .withPasswordConfirm("te1dsf12sdfg")
                .withLinkedin("https://test.ru")
                .withAcademicRankId(1)
                .withScienceDegreeId(1)
                .build();

        assertDoesNotThrow(() -> teacherRegisterValidator.validate(teacherRegisterRequest));
    }
}