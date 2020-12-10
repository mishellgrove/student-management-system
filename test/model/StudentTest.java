package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import customExceptions.EmptySearchException;
import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;

// TODO: Auto-generated Javadoc
/**
 * The Class StudentTest.
 */
class StudentTest {

	/** The student. */
	private Student student;

	/** The school. */
	private VirtualSchool school = new VirtualSchool("SAI");

	/**
	 * Sets the up scenary one.
	 */
	private void setUpScenaryFirst() {
		StudentAccount sa = new StudentAccount("2", 600000);
		student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
	}

	/**
	 * Sets the up scenary two.
	 */
	private void setUpScenarySecond() {
		StudentAccount sa = new StudentAccount("2", 600000);
		student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
		student.addRegister();
		student.addRegister();
	}

	private void setUpScenaryThird() {
		StudentAccount sa = new StudentAccount("2", 600000);
		student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
		student.addRegister();
		student.addRegister();
		int index = student.searchRegister("060000");
		Register register = student.getRegisters().get(index);

		try {
			school.addCourse("2", "Biology", "Th biology course for fourth grade");
			school.addCourse("1", "History", "Biology for 5th grade");
			school.addCourse("3", "Music", "Biology for 7th grade");
			register.addCourse("1");
			register.addCourse("2");
			register.addCourse("3");
		} catch (NullEntityException e) {
			e.printStackTrace();
		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
	}

	private void setUpScenaryFourth() {
		StudentAccount sa = new StudentAccount("2", 600000);
		student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
		student.addRegister();
		student.addRegister();
		int index = student.searchRegister("060000");
		Register register = student.getRegisters().get(index);

		try {
			school.addTeacher("1", "Andres", "Gutierrez", "root", 5000000);
			school.addCourse("2", "Biology", "Th biology course for fourth grade");
			school.addCourse("1", "History", "Biology for 5th grade");
			school.addCourse("3", "Music", "Biology for 7th grade");
			school.getCourses().get(0).setTeacher(school.getTeachers().get(0));
			school.getCourses().get(1).setTeacher(school.getTeachers().get(0));
			school.getCourses().get(2).setTeacher(school.getTeachers().get(0));

			register.addCourse("1");
			register.addCourse("2");
			register.addCourse("3");
		} catch (NullEntityException e) {
			e.printStackTrace();
		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds the registertest.
	 */
	@Test
	void addRegistertest() {
		setUpScenaryFirst();
		student.addRegister();
		assertNotNull(student.getRegisters().get(0));
	}

	/**
	 * Removes the registertest.
	 */
	@Test
	void removeRegistertest() {
		setUpScenarySecond();
		int size = student.getRegisters().size();
		student.removeRegister("060000");
		assertTrue("Wrong deleted register", size > student.getRegisters().size());
	}

	/**
	 * Search registers test.
	 */
	@Test
	void searchRegistersTest() {
		setUpScenarySecond();
		int index1 = student.searchRegister("060000");
		int index2 = student.searchRegister("160000");
		int index3 = student.searchRegister("3");
		boolean assertBool = (index1 & index2) != -1 && index3 == -1;
		assertTrue("Wrong search", assertBool);
	}

	@Test
	void getAllCoursesTest() {
		setUpScenaryThird();
		ArrayList<Course> courses = student.getAllCourses();
		String name1 = "Biology";
		String name2 = "History";
		String name3 = "Music";
		boolean assertBool = name2.equals(courses.get(0).getName()) && name1.equals(courses.get(1).getName())
				&& name3.equals(courses.get(2).getName());
		assertTrue(assertBool);
	}

	@Test
	void getCoursesByNameTest() {
		setUpScenaryThird();
		ArrayList<Course> courses = student.getCoursesByName("Biology");
		assertTrue(courses.size() == 1);
	}

	@Test
	void getCourseByTeacherTest() {
		setUpScenaryFourth();
		ArrayList<Course> courses = student.getCourseByTeacher("Andres");
		assertTrue(courses.size() == 3);
	}

	@Test
	void getCoursesTest() {
		setUpScenaryFourth();
		ArrayList<Course> courses = new ArrayList<Course>();
		ArrayList<Course> courses2 = new ArrayList<Course>();
		try {
			courses = student.getCourses("Biology");
			courses2 = student.getCourses("Andres");
		} catch (EmptySearchException e) {
			e.printStackTrace();
		}
		assertTrue(courses.size()==1 && courses2.size()==3);
	}

}
