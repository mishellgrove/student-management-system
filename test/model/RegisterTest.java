package model;

import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;

class RegisterTest {

	private Register register;
	private VirtualSchool school = new VirtualSchool("SAI");

	private void setUpScenaryOne() {
		TeacherAccount ta = new TeacherAccount("123", 0);
		Teacher teacher = new Teacher("1", "Alfred", "Giraldo", "root", 3000000, ta, school);
		ta.setPerson(teacher);
		try {
			school.addCourse("1", "Biology", "Biology for 6th grade", teacher);
			school.addCourse("2", "History", "History for 6th grade", teacher);
			school.addCourse("3", "Physic", "Physic for 6th grade", teacher);
			school.addCourse("4", "Math", "Math for 6th grade", teacher);
			StudentAccount sa = new StudentAccount("2", 600000);
			Student student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
			register = new Register("1", student);
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
	}

	private void setUpScenaryTwo() {
		TeacherAccount ta = new TeacherAccount("123", 0);
		Teacher teacher = new Teacher("1", "Alfred", "Giraldo", "root", 3000000, ta, school);
		ta.setPerson(teacher);
		try {
			school.addCourse("1", "Biology", "Biology for 6th grade", teacher);
			school.addCourse("2", "History", "History for 6th grade", teacher);
			school.addCourse("3", "Physic", "Physic for 6th grade", teacher);
			school.addCourse("4", "Math", "Math for 6th grade", teacher);
			StudentAccount sa = new StudentAccount("2", 600000);
			Student student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
			register = new Register("1", student);
			register.addCourse("2");
			register.addCourse("1");
			register.addCourse("3");
		} catch (NullEntityException e) {
			e.printStackTrace();
		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
	}

	@Test
	void addCourseTest() {
		setUpScenaryOne();
		boolean assertAux = true;
		try {
			register.addCourse("1");
		} catch (NullEntityException e) {
			assertAux = false;
		} catch (EntityRepeatedException e) {
			assertAux = false;
		}
		assertAux = register.getCourses().size() == 1;
		assertTrue("Wrong insertion in register method", assertAux);
	}

	@Test
	void removeCourseTest() {
		setUpScenaryOne();
		boolean assertAux = true;
		int courses = 0;
		try {
			register.addCourse("2");
			register.addCourse("1");
			register.addCourse("3");
			courses = register.getCourses().size();
			register.removeCourse("2");
		} catch (NullEntityException e) {
			assertAux = false;
		} catch (EntityRepeatedException e) {
			assertAux = false;
		}
		assertAux = register.getCourses().size() == courses - 1;
		assertTrue("Wrong remove in remove method", assertAux);
	}

	@Test
	void searchTest() {
		setUpScenaryTwo();
		int index1 = register.searchCourse("1");
		int index2 = register.searchCourse("2");
		int index3 = register.searchCourse("3");
		int index4 = register.searchCourse("4");
		boolean assertBool = (index1 & index2 & index3) != -1 && index4 == -1;
		assertTrue(assertBool);
	}

	@Test
	void sortByInsertionTest() {
		setUpScenaryTwo();
		register.sortByInsertion();
		String code1 = "1";
		String code2 = "2";
		String code3 = "3";
		boolean assertBool = code1.equals(register.getCourses().get(0).getId())
				&& code2.equals(register.getCourses().get(1).getId())
				&& code3.equals(register.getCourses().get(2).getId());
		assertTrue(assertBool);
	}
	
}
