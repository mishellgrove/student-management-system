package model;

import java.util.ArrayList;

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

	public void addCourse(String id) throws NullEntityException, EntityRepeatedException {

		int index = school.searchCourse(id);
		if (index == -1) {
			throw new NullEntityException("The course with id: " + id + " does not exists");
		}
		int index2 = searchCourse(id);
		if(index2 != -1) {
			throw new EntityRepeatedException("The course with id: " + id + " already exists!");
		}
		Course course = school.getCourses().get(index);
		course.setTeacher(this);
		courses.add(course);
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
		return searchCourse(idCourse, 0, courses.size()-1);
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
}

