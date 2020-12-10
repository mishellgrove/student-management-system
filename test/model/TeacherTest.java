package model;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import customExceptions.BinaryTreeCastException;
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
	private void setUpScenaryThird() {
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
			teacher.addStudents("1", "Alejndro", "Arango", "");
			teacher.addStudents("2", "Alejndro", "Zapata", "");
			teacher.addStudents("3", "Alejndro", "Castro", "");
			teacher.addStudents("4", "Alejndro", "Baron", "");
			teacher.addStudents("5", "Alejndro", "Medina", "");
			school.getStudents().get(0).addRegister();
			school.getStudents().get(1).addRegister();;
			school.getStudents().get(2).addRegister();;
			school.getStudents().get(3).addRegister();;
			school.getStudents().get(4).addRegister();;

			Register register = school.getStudents().get(0).getRegisters().get(school.getStudents().get(0).getRegisters().size() - 1);
			register.addCourse("1");
			Register register1 = school.getStudents().get(1).getRegisters().get(school.getStudents().get(1).getRegisters().size() - 1);
			register1.addCourse("1");
			Register register2 = school.getStudents().get(2).getRegisters().get(school.getStudents().get(2).getRegisters().size() - 1);
			register2.addCourse("1");
			Register register3 = school.getStudents().get(3).getRegisters().get(school.getStudents().get(3).getRegisters().size() - 1);
			register3.addCourse("1");
			Register register4 = school.getStudents().get(4).getRegisters().get(school.getStudents().get(4).getRegisters().size() - 1);
			register4.addCourse("1");
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
	@Test
	void getByDescLastName() {
		setUpScenaryThird();
		try {
			ArrayList<Student>students =teacher.getStudentsDescByLastName();
			System.out.println(students);
		} catch (BinaryTreeCastException e) {
			e.printStackTrace();
		}
	}

}
