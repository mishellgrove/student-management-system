package model;

import java.util.ArrayList;
import java.util.Date;

import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;

/**
 * The Class Register.
 */
public class Register {

	/** The id. */
	private String id;

	/** The student. */
	private Student student;

	/** The date. */
	private Date date;

	/** The courses. */
	private ArrayList<Course> courses;

	/** The total. */
	private double total;

	/** The state. */
	private String state;

	/**
	 * Instantiates a new register.
	 *
	 * @param id      the id
	 * @param student the student
	 */
	public Register(String id, Student student) {
		this.id = id;
		this.student = student;
		date = new Date();
		courses = new ArrayList<Course>();
		state = "Inactive";
	}

	/**
	 * Adds the course.
	 *
	 * @param id the id
	 * @throws NullEntityException     the null entity exception
	 * @throws EntityRepeatedException the entity repeated exception
	 */
	public void addCourse(String id) throws NullEntityException, EntityRepeatedException {
		VirtualSchool school = student.getSchool();
		int index = school.searchCourse(id);
		if (index == -1) {
			throw new NullEntityException("The course wiuth id: " + id + " does not exists");
		}
		int index2 = searchCourse(id);
		if (index2 != -1) {
			throw new EntityRepeatedException("The course with id: " + id + " already exists!");
		}
		Course course = school.getCourses().get(index);
		courses.add(course);
		course.addRegister(getId(), student);
	}

	/**
	 * Removes the course.
	 *
	 * @param id the id
	 * @throws NullEntityException the null entity exception
	 */
	public void removeCourse(String id) throws NullEntityException {
		sortByInsertion();
		int index = searchCourse(id);
		if (index == -1) {
			throw new NullEntityException("The course with id: " + id + " does not exists!");
		}
		Course course = courses.get(index);
		course.removeRegister(getId());
		courses.remove(index);
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
	 * Sort by insertion.
	 */
	public void sortByInsertion() {
		int j;
		Course course;
		for (int i = 1; i < courses.size(); i++) {
			course = courses.get(i);
			j = i - 1;
			while ((j >= 0) && (course.compareTo(courses.get(j)) == -1)) {
				courses.set(j + 1, courses.get(j));
				j--;
			}
			courses.set(j + 1, course);
		}
	}

	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Sets the date.
	 *
	 * @param date the new date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the new id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the student.
	 *
	 * @return the student
	 */
	public Student getStudent() {
		return student;
	}

	/**
	 * Sets the student.
	 *
	 * @param student the new student
	 */
	public void setStudent(Student student) {
		this.student = student;
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
	 * Gets the total.
	 *
	 * @return the total
	 */
	public double getTotal() {
		return courses.size() * 200000;
	}

	/**
	 * Sets the total.
	 *
	 * @param total the new total
	 */
	public void setTotal(double total) {
		this.total = total;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(String state) {
		this.state = state;
		for (Course course : courses) {
			course.setState(state);
		}
	}

	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Equals.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Register other = (Register) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public String toString() {
		return "Register [id=" + id + "]";
	}

}
