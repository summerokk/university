package com.att.university.service.impl;

import com.att.university.dao.ClassroomDao;
import com.att.university.dao.CourseDao;
import com.att.university.dao.GroupDao;
import com.att.university.dao.LessonDao;
import com.att.university.dao.TeacherDao;
import com.att.university.entity.Classroom;
import com.att.university.entity.Course;
import com.att.university.entity.Group;
import com.att.university.entity.Lesson;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.LessonNotFoundException;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.service.LessonService;
import com.att.university.validator.lesson.LessonAddValidator;
import com.att.university.validator.lesson.LessonUpdateValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonServiceImpl implements LessonService {
    private final LessonAddValidator lessonAddValidator;
    private final LessonUpdateValidator lessonUpdateValidator;
    private final TeacherDao teacherDao;
    private final LessonDao lessonDao;
    private final CourseDao courseDao;
    private final GroupDao groupDao;
    private final ClassroomDao classroomDao;

    @Override
    public List<Lesson> findAll(int page, int count) {
        return lessonDao.findAll(page, count);
    }

    @Override
    @Transactional
    public void add(LessonAddRequest addRequest) {
        log.debug("Adding lesson with request {}", addRequest);

        lessonAddValidator.validate(addRequest);

        Course course = courseDao.findById(addRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomDao.findById(addRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupDao.findById(addRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherDao.findById(addRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        LocalDateTime date = LocalDateTime.parse(addRequest.getDate());

        lessonDao.save(Lesson.builder()
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(date)
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }

    @Override
    @Transactional
    public void update(LessonUpdateRequest updateRequest) {
        log.debug("Updating lesson with request {}", updateRequest);

        lessonUpdateValidator.validate(updateRequest);

        Lesson lesson = lessonDao.findById(updateRequest.getId())
                .orElseThrow(() -> new LessonNotFoundException("Lesson is not found"));

        Course course = courseDao.findById(updateRequest.getCourseId())
                .orElseThrow(() -> new LessonNotFoundException("Course is not found"));

        Classroom classroom = classroomDao.findById(updateRequest.getClassroomId())
                .orElseThrow(() -> new LessonNotFoundException("Classroom is not found"));

        Group group = groupDao.findById(updateRequest.getGroupId())
                .orElseThrow(() -> new LessonNotFoundException("Group is not found"));

        Teacher teacher = teacherDao.findById(updateRequest.getTeacherId())
                .orElseThrow(() -> new LessonNotFoundException("Teacher is not found"));

        LocalDateTime date = LocalDateTime.parse(updateRequest.getDate());

        lessonDao.update(Lesson.builder()
                .withId(lesson.getId())
                .withTeacher(teacher)
                .withGroup(group)
                .withDate(date)
                .withClassroom(classroom)
                .withCourse(course)
                .build());
    }

    @Override
    public List<LocalDate> findTeacherLessonWeeks(LocalDate startDate, LocalDate endDate, Integer teacherId) {
        List<LocalDate> weeks = lessonDao.findTeacherLessonWeeks(startDate, endDate, teacherId);
        weeks.set(0, startDate);

        return weeks;
    }

    @Override
    public List<Lesson> findTeacherWeekSchedule(int currentPage, List<LocalDate> weeks, Integer teacherId) {
        LocalDate startDate = weeks.get(currentPage - 1);

        return lessonDao.findTeacherWeekSchedule(startDate, teacherId);
    }

    @Override
    public List<Lesson> findByDateBetweenAndTeacherId(LocalDate startDate, LocalDate endDate, Integer teacherId) {
        return lessonDao.findByDateBetweenAndTeacherId(teacherId, startDate, endDate);
    }
}
