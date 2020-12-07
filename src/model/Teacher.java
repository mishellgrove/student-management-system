package model;

import java.util.ArrayList;
import java.util.Collections;

import customExceptions.BinaryTreeCastException;
import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;

/**
 * The Class Teacher.
 */
public class Teacher extends Employee {

	/** The account. */
	private TeacherAccount account;

	/** The courses. */
	private ArrayList<Course> courses;

	/** The school. */
	private VirtualSchool school;

	/**
	 * Instantiates a new teacher.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 * @param password the password
	 * @param salary   the salary
	 * @param account  the account
	 * @param school   the school
	 */
	public Teacher(String code, String name, String lastName, String password, double salary, TeacherAccount account,
			VirtualSchool school) {
		super(code, name, lastName, password, salary);
		this.account = account;
		this.school = school;
		courses = new ArrayList<Course>();
	}

	/**
	 * Adds the course.
	 *
	 * @param id the id
	 * @throws NullEntityException     the null entity exception
	 * @throws EntityRepeatedException the entity repeated exception
	 */
	public void addCourse(String id) throws NullEntityException, EntityRepeatedException {

		int index = school.searchCourse(id);
		if (index == -1) {
			throw new NullEntityException("The course with id: " + id + " does not exists");
		}
		int index2 = searchCourse(id);
		if (index2 != -1) {
			throw new EntityRepeatedException("The course with id: " + id + " already exists!");
		}
		Course course = school.getCourses().get(index);
		course.setTeacher(this);
		courses.add(course);
	}

	/**
	 * Adds the students.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 * @param password the password
	 * @throws NullEntityException the null entity exception
	 */
	public void addStudents(String code, String name, String lastName, String password) throws NullEntityException {
		school.addStudents(code, name, lastName, password);
	}

	/**
	 * Removes the student.
	 *
	 * @param code the code
	 * @throws NullEntityException the null entity exception
	 */
	public void removeStudent(String code) throws NullEntityException {
		school.removeStudent(code);
	}

	/**
	 * Gets the students by course.
	 *
	 * @param idCourse the id course
	 * @return the students by course
	 * @throws NullEntityException the null entity exception
	 */
	public ArrayList<Student> getStudentsByCourse(String idCourse) throws NullEntityException {
		ArrayList<Student> students = new ArrayList<Student>();
		int index = searchCourse(idCourse);
		if (index == -1) {
			throw new NullEntityException("The course: " + idCourse + " does not exists");
		}
		Course course = courses.get(index);
		for (int i = 0; i < course.getRegisters().size(); i++) {
			Register register = course.getRegisters().get(i);
			students.add(register.getStudent());
		}
		return students;
	}

	/**
	 * Gets the students.
	 *
	 * @return the students
	 */
	public ArrayList<Student> getStudents() {
		ArrayList<Student> students = new ArrayList<Student>();
		for (Course course : courses) {
			for (Register register : course.getRegisters()) {
				students.add(register.getStudent());
			}
		}
		return students;
	}

	/**
	 * Gets the students desc by last name.
	 *
	 * @return the students desc by last name
	 * @throws BinaryTreeCastException the binary tree cast exception
	 */
	public ArrayList<Student> getStudentsDescByLastName() throws BinaryTreeCastException {
		ArrayList<Student> students = new ArrayList<Student>();
		for (Course course : courses) {
			course.initStudents();
			if (course.getRegisters().size() > 0) {

				ArrayList<Person> persons = course.getStudents().getInOrder();
				for (Person person : persons) {
					students.add((Student) person);
				}
			}
		}
		StudentComparator comparator = new StudentComparator();
		Collections.sort(students, comparator);
		return students;
	}

	/**
	 * Gets the students asc by code.
	 *
	 * @return the students asc by code
	 * @throws BinaryTreeCastException the binary tree cast exception
	 */
	public ArrayList<Student> getStudentsAscByCode() throws BinaryTreeCastException {
		ArrayList<Student> students = new ArrayList<Student>();
		for (Course course : courses) {
			course.initStudents();
			if (course.getRegisters().size() > 0) {
				ArrayList<Person> persons = course.getStudents().getInOrder();
				for (Person person : persons) {
					students.add((Student) person);
				}
			}
		}
		Collections.sort(students);
		return students;
	}

	/**
	 * Removes the course.
	 *
	 * @param id the id
	 * @throws NullEntityException the null entity exception
	 */
	public void removeCourse(String id) throws NullEntityException {
		sortBySelection();
		int index = searchCourse(id);
		if (index == -1) {
			throw new NullEntityException("The course with id: " + id + " does not exists!");
		}
		Course course = courses.get(index);
		course.setTeacher(null);
		courses.remove(index);
	}

	/**
	 * Sort by selection.
	 */
	public void sortBySelection() {
		int size = courses.size();
		for (int i = 0; i < size; i++) {
			int minIdIndex = i;
			for (int j = i + 1; j < size - 1; j++) {
				int id1 = Integer.parseInt(courses.get(j).getId());
				int id2 = Integer.parseInt(courses.get(minIdIndex).getId());
				if (id1 < id2) {
					minIdIndex = j;
				}
			}
			Course temporal = courses.get(minIdIndex);
			courses.set(minIdIndex, courses.get(i));
			courses.set(i, temporal);
		}
	}

	/**
	 * Search course.
	 *
	 * @param idCourse the id course
	 * @param low      the low
	 * @param high     the high
	 * @return the int
	 */
	private int searchCourse(String idCourse, int low, int high) {
		int mid = low + (high - low) / 2;
		if (high < low) {
			return -1;
		}
		int idAux = Integer.parseInt(courses.get(mid).getId());
		int idCourseAux = Integer.parseInt(idCourse);
		if (idAux == idCourseAux) {
			return mid;
		} else if (idCourseAux < idAux) {
			return searchCourse(idCourse, low, mid - 1);
		}
		return searchCourse(idCourse, mid + 1, high);
	}

	/**
	 * Search course.
	 *
	 * @param idCourse the id course
	 * @return the int
	 */
	public int searchCourse(String idCourse) {
		return searchCourse(idCourse, 0, courses.size() - 1);
	}

	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public TeacherAccount getAccount() {
		return account;
	}

	/**
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(TeacherAccount account) {
		this.account = account;
	}

	/**
	 * Gets the courses.
	 *
	 * @return the courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * Sets the courses.
	 *
	 * @param courses the new courses
	 */
	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	/**
	 * Gets the school.
	 *
	 * @return the school
	 */
	public VirtualSchool getSchool() {
		return school;
	}

	/**
	 * Sets the school.
	 *
	 * @param school the new school
	 */
	public void setSchool(VirtualSchool school) {
		this.school = school;
	}

	@Override
	public String toString() {
		return getName() + " " + getLastName();
	}

}
