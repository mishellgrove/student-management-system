package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;

class TeacherTest {

	private VirtualSchool school = new VirtualSchool("SAI");
	private Teacher teacher;

	private void setUpScenaryOne() {
		TeacherAccount ta = new TeacherAccount("123", 0);
		teacher = new Teacher("1", "Alfred", "Giraldo", "root", 3000000, ta, school);
		ta.setPerson(teacher);
		try {
			school.addCourse("1", "Biology", "Biology for 6th grade");
			school.addCourse("2", "History", "History for 6th grade");
			school.addCourse("3", "Physic", "Physic for 6th grade");
			school.addCourse("4", "Math", "Math for 6th grade");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
	}
	private void setUpScenaryTwo() {
		TeacherAccount ta = new TeacherAccount("123", 0);
		teacher = new Teacher("1", "Alfred", "Giraldo", "root", 3000000, ta, school);
		ta.setPerson(teacher);
		try {
			school.addCourse("1", "Biology", "Biology for 6th grade");
			school.addCourse("2", "History", "History for 6th grade");
			school.addCourse("3", "Physic", "Physic for 6th grade");
			school.addCourse("4", "Math", "Math for 6th grade");

			teacher.addCourse("3");
			teacher.addCourse("2");
			teacher.addCourse("1");
			teacher.addCourse("4");

		} catch (NullEntityException e) {
			e.printStackTrace();
		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
	}

	@Test
	void addCoursesTest() {
		setUpScenaryOne();
		boolean assertAux = true;
		int sizeCourses = teacher.getCourses().size();
		try {

			teacher.addCourse("1");
			teacher.addCourse("2");
			teacher.addCourse("3");
			teacher.addCourse("4");

		} catch (NullEntityException e) {
			assertAux = false;
		} catch (EntityRepeatedException e) {
			assertAux = false;
		}
		assertAux = sizeCourses<teacher.getCourses().size();
		assertTrue("Could not add the course", assertAux);
	}
	
	@Test
	void removeCourseTest() {
		setUpScenaryTwo();
		boolean assertAux = true;
		
		try {
			teacher.removeCourse("1");
			teacher.removeCourse("2");
			teacher.removeCourse("4");
		} catch (NullEntityException e) {
			assertAux = false;
			e.printStackTrace();
		}
		assertAux = teacher.getCourses().size() == 1;
		assertTrue("Wrong in the removed course method", assertAux);
	}

}
