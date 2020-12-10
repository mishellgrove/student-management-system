package model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;

class CourseTest {

	private Course course;
	private VirtualSchool school = new VirtualSchool("SAI");

	private void setUpScenaryOne() {
		TeacherAccount ta = new TeacherAccount("123", 0);
		Teacher teacher = new Teacher("1", "Alfred", "Giraldo", "root", 3000000, ta, school);
		ta.setPerson(teacher);
		course = new Course("1", "Biology", "Biology class for 6th grade students", teacher, school);
	}

	private void setUpScenaryTwo() {
		TeacherAccount ta = new TeacherAccount("123", 0);
		Teacher teacher = new Teacher("1", "Alfred", "Giraldo", "root", 3000000, ta, school);
		ta.setPerson(teacher);
		course = new Course("1", "Biology", "Biology class for 6th grade students", teacher, school);
		StudentAccount sa = new StudentAccount("2", 600000);
		Student student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
		try {
			course.addRegister("1", student);

		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
		StudentAccount sa2 = new StudentAccount("2", 600000);
		Student student2 = new Student("60001", "Barlos", "Castaño", "s123", sa2, school);
		try {
			course.addRegister("2", student2);

		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
		StudentAccount sa3 = new StudentAccount("2", 600000);
		Student student3 = new Student("60002", "Carlos", "Castaño", "s123", sa3, school);
		try {
			course.addRegister("3", student3);

		} catch (EntityRepeatedException e) {
			e.printStackTrace();
		}
	}

	@Test
	void addRegisterTest() {
		setUpScenaryOne();
		StudentAccount sa = new StudentAccount("2", 600000);
		Student student = new Student("60001", "Carlos", "Castaño", "s123", sa, school);
		boolean assertAux = true;
		try {
			course.addRegister("1", student);

		} catch (EntityRepeatedException e) {
			assertAux = false;
			assertTrue(assertAux);
		}
		assertTrue(assertAux);
	}

	@Test
	void removeRegisterTest() {
		setUpScenaryTwo();
		boolean asserAux = false;
		int sizeRegisters = course.getRegisters().size();
		try {
			course.removeRegister("3");
		} catch (NullEntityException e) {
			assertTrue(e.getMessage(), asserAux);
		}
		asserAux = course.getRegisters().size() == sizeRegisters - 1;
		assertTrue("Could not removed the register", asserAux);
	}

	@Test
	public void searchRegisterTest() {
		setUpScenaryTwo();
		int index = course.searchRegister("1");
		int index2 = course.searchRegister("2");
		int index3 = course.searchRegister("3");
		int indexWrong = course.searchRegister("4");
		assertTrue("The search is wrong", (index & index2 & index3) != -1 && indexWrong == -1);
	}

	@Test
	public void initStudentsTest() {
		setUpScenaryTwo();
		course.initStudents();
		ArrayList<Person> studentsPreorder = course.getStudents().getInOrder();
		String nameFirst = "Amanda";
		String nameSecond = "Barlos";
		String nameThird = "Carlos";
		boolean assert1 = nameFirst.equals(studentsPreorder.get(0).getName());
		boolean assert2 = nameSecond.equals(studentsPreorder.get(1).getName());
		boolean assert3 = nameThird.equals(studentsPreorder.get(2).getName());
		assertTrue(assert1 && assert2 && assert3);
	}

}
