package com.att.university.request.lesson;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class LessonUpdateRequest extends LessonRequest {
    private Integer id;

    @Builder(setterPrefix = "with")
    public LessonUpdateRequest(Integer courseId, Integer groupId, Integer classroomId, Integer teacherId,
                            LocalDateTime date, Integer id) {
        super(courseId, groupId, classroomId, teacherId, date);

        this.id = id;
    }
}
