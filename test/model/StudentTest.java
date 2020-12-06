package model;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StudentTest {

	private Student student;
	private VirtualSchool school = new VirtualSchool("SAI");

	private void setUpScenaryOne() {
		StudentAccount sa = new StudentAccount("2", 600000);
		student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
	}

	private void setUpScenaryTwo() {
		StudentAccount sa = new StudentAccount("2", 600000);
		student = new Student("60000", "Amanda", "Castaño", "s123", sa, school);
		student.addRegister();
		student.addRegister();
	}

	@Test
	void addRegistertest() {
		setUpScenaryOne();
		student.addRegister();
		assertNotNull(student.getRegisters().get(0));
	}

	@Test
	void removeRegistertest() {
		setUpScenaryTwo();
		int size = student.getRegisters().size();
		student.removeRegister("0");
		assertTrue("Wrong deleted register", size > student.getRegisters().size());
	}

}
