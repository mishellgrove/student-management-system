package model;

import java.util.ArrayList;

import customExceptions.NullEntityException;

/**
 * The Class Director.
 */
public class Director extends Employee {

	/** The school. */
	private VirtualSchool school;

	/**
	 * Instantiates a new director.
	 *
	 * @param code     the code
	 * @param name     the name
	 * @param lastName the last name
	 * @param salary   the salary
	 * @param school   the school
	 */
	public Director(String code, String name, String lastName, String password, double salary, VirtualSchool school) {
		super(code, name, lastName, password, salary);
		this.school = school;
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

	public void addCourse(String id, String name, String description, Teacher teacher) throws NullEntityException {
		school.addCourse(id, name, description, teacher);
	}

	public void addCourse(String id, String name, String description) throws NullEntityException {
		school.addCourse(id, name, description);
	}

	public void removeCourse(String id) throws NullEntityException {
		school.removeCourse(id);
	}

	public void addTeacher(String code, String name, String lastName, String password, double salary)
			throws NullEntityException {
		school.addTeacher(code, name, lastName, password, salary);
	}

	public ArrayList<Course> getCoursesByNameSelection() {
		ArrayList<Course> courses = school.getCourses();
		int j;
		Course course;
		CourseComparator comparator = new CourseComparator();
		for (int i = 1; i < courses.size(); i++) {
			course = courses.get(i);
			j = i - 1;
			while ((j >= 0) && (comparator.compare(course, courses.get(j)) == -1)) {
				courses.set(j + 1, courses.get(j));
				j--;
			}
			courses.set(j + 1, course);
		}
		return courses;
	}

	public ArrayList<Teacher> getTeachersByNameInsertion() {
		ArrayList<Teacher> teachers = school.getTeachers();
		TeacherComparator comparator = new TeacherComparator();
		int size = teachers.size();
		for (int i = 0; i < size; i++) {
			int minIdIndex = i;
			for (int j = i + 1; j < size - 1; j++) {
				if (comparator.compare(teachers.get(j), teachers.get(minIdIndex)) == -1) {
					minIdIndex = j;
				}
			}
			Teacher temporal = teachers.get(minIdIndex);
			teachers.set(minIdIndex, teachers.get(i));
			teachers.set(i, temporal);
		}

		return teachers;
	}

}
