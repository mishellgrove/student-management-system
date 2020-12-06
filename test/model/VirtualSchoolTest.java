package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import customExceptions.NullEntityException;

class VirtualSchoolTest {

	private VirtualSchool school;

	private void setUpScenary() {
		school = new VirtualSchool("SAI");
	}

	private void setUpScenaryTwo() {
		school = new VirtualSchool("SAI");
		try {
			school.addCourse("2", "Biology 4th grade", "Th biology course for fourth grade");
			school.addCourse("1", "Biology", "Biology for 5th grade");
			school.addCourse("3", "Biology", "Biology for 7th grade");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
	}

	private void setUpScenaryThree() {
		school = new VirtualSchool("SAI");
		try {
			school.addTeacher("3", "Andrey", "Gutierrez", "root", 5000000);
			school.addTeacher("1", "Andres", "Gutierrez", "root", 5000000);
			school.addTeacher("2", "Andrey", "Meneses", "root", 5000000);
		} catch (NullEntityException e) {
			e.printStackTrace();
		}

	}

	private void setUpScenaryFourth() {
		school = new VirtualSchool("SAI");
		try {
			school.addStudents("3", "Carlos", "Gutierrez", "456");
			school.addStudents("1", "Carlos", "Gutierrez", "456");
			school.addStudents("4", "Carlos", "Gutierrez", "456");
			school.addStudents("5", "Carlos", "Gutierrez", "456");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}

	}
	private void setUpScenaryFifth() {
		school = new VirtualSchool("SAI");
		try {
			school.addDirector("2", "Alejandro", "Martinez", "root", 4500000);
			school.addDirector("1", "Alejandro", "Martinez", "root", 4500000);
		} catch (NullEntityException e) {
			e.printStackTrace();
		}

	}

	@Test
	void addCoursesTest() {
		setUpScenary();
		boolean assertAuxiliar = true;
		try {
			school.addCourse("1", "Biology", "Biology realnes for 5th grade");
		} catch (NullEntityException e) {
			assertAuxiliar = false;
		}
		assertAuxiliar = school.getCourses().size() == 1;
		assertTrue(assertAuxiliar);
	}

	@Test
	void removeCoursesTest() {
		setUpScenaryTwo();
		try {
			school.removeCourse("1");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertTrue(school.getCourses().size() == 2);
	}

	@Test
	void addTeacherTest() {
		setUpScenary();
		try {
			school.addTeacher("1", "Andrey", "Gutierrez", "root", 5000000);
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertNotNull(school.getTeachers().get(0));
	}

	@Test
	void removeTeacherTest() {
		setUpScenaryThree();
		try {
			school.removeTeacher("3");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertTrue(school.getTeachers().size() == 2);
	}

	@Test
	void addStudentTest() {
		setUpScenary();
		try {
			school.addStudents("1", "Carlos", "Peña", "3445");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertNotNull(school.getStudents().get(0));
	}

	@Test
	void removeStudentTest() {
		setUpScenaryFourth();
		try {
			school.removeStudent("1");
			school.removeStudent("3");
			school.removeStudent("4");
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertTrue(school.getStudents().size() == 1);
	}

	@Test
	void addDirectorTest() {
		setUpScenary();
		try {
			school.addDirector("2", "Alejandro", "Martinez", "root", 4500000);

			school.addDirector("1", "Alejandro", "Martinez", "root", 4500000);
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertTrue(school.getDirectors().size() == 2);
	}
	@Test
	void removeDirectorTest() {
		setUpScenaryFifth();
		try {
			school.removeDirector("1");
			
		} catch (NullEntityException e) {
			e.printStackTrace();
		}
		assertTrue(school.getDirectors().size()==1);
	}

}
