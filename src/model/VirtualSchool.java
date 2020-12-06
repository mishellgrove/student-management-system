package model;

import java.util.ArrayList;

import customExceptions.NullEntityException;

/**
 * The Class VirtualSchool.
 */
public class VirtualSchool implements IPaths {

	/** The name. */
	private String name;

	/** The teachers. */
	private ArrayList<Teacher> teachers;

	/** The directors. */
	private ArrayList<Director> directors;

	/** The students. */
	private ArrayList<Student> students;

	/** The courses. */
	private ArrayList<Course> courses;

	/**
	 * Instantiates a new virtual school.
	 *
	 * @param name the name
	 */
	public VirtualSchool(String name) {
		teachers = new ArrayList<Teacher>();
		directors = new ArrayList<Director>();
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
		this.name = name;
	}

	public void loadData() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(ArrayList<Teacher> teachers) {
		this.teachers = teachers;
	}

	public ArrayList<Director> getDirectors() {
		return directors;
	}

	public void setDirectors(ArrayList<Director> directors) {
		this.directors = directors;
	}

	public ArrayList<Student> getStudents() {
		return students;
	}

	public void setStudents(ArrayList<Student> students) {
		this.students = students;
	}

	public ArrayList<Course> getCourses() {
		return courses;
	}

	public void setCourses(ArrayList<Course> courses) {
		this.courses = courses;
	}

	public void addCourse(String id, String name, String description, Teacher teacher) throws NullEntityException {
		if (courses.size() > 0) {
			int index = searchCourse(id);
			if (index != -1) {
				throw new NullEntityException("The course with id: " + id + " already exists!");
			}
		}
		courses.add(new Course(id, name, description, teacher, this));
	}

	public void addCourse(String id, String name, String description) throws NullEntityException {
		if (courses.size() > 0) {
			sortCourses();
			int index = searchCourse(id);
			if (index != -1) {
				throw new NullEntityException("The course with id: " + id + " already exists!");
			}
		}
		courses.add(new Course(id, name, description, null, this));
	}

	public void removeCourse(String id) throws NullEntityException {
		sortCourses();
		int index = searchCourse(id);
		if (index == -1) {
			throw new NullEntityException("The course with id: " + id + " does not exists!");
		}
		courses.remove(index);
	}

	public void sortCourses() {
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

	private int searchCourse(String idCourse, int low, int high) {
		int mid = (low + high) / 2;
		if (high < low) {
			return -1;
		}
		if (courses.get(mid).getId().equals(idCourse)) {
			return mid;
		} else if (Integer.parseInt(idCourse) < Integer.parseInt(courses.get(mid).getId())) {
			return searchCourse(idCourse, low, mid - 1);
		}
		return searchCourse(idCourse, mid + 1, high);
	}

	public int searchCourse(String idCourse) {
		return searchCourse(idCourse, 0, courses.size() - 1);
	}
	// -----------------------------------------------------------

	public void addTeacher(String code, String name, String lastName, String password, double salary)
			throws NullEntityException {
		if (teachers.size() > 0) {
			sortTeachers();
			int index = searchTeacher(code);
			if (index != -1) {
				throw new NullEntityException("The teacher with id: " + code + " already exists!");
			}
		}
		TeacherAccount account = new TeacherAccount(code, 0);
		teachers.add(new Teacher(code, name, lastName, password, salary, account, this));
	}

	public void sortTeachers() {
		int j;
		Teacher teacher;
		for (int i = 1; i < teachers.size(); i++) {
			teacher = teachers.get(i);
			j = i - 1;
			while ((j >= 0) && (teacher.compareTo(teachers.get(j)) == -1)) {
				teachers.set(j + 1, teachers.get(j));
				j--;
			}
			teachers.set(j + 1, teacher);
		}
	}

	private int searchTeacher(String codeTeacher, int low, int high) {
		int mid = low + (high - low) / 2;
		if (high < low) {
			return -1;
		}
		if (teachers.get(mid).getCode().equals(codeTeacher)) {
			return mid;
		} else if (Integer.parseInt(codeTeacher) < Integer.parseInt(teachers.get(mid).getCode())) {
			return searchTeacher(codeTeacher, low, mid - 1);
		}
		return searchTeacher(codeTeacher, mid + 1, high);
	}

	public void removeTeacher(String code) throws NullEntityException {
		sortTeachers();
		int index = searchTeacher(code);
		if (index == -1) {
			throw new NullEntityException("The teacher with code: " + code + " does not exists!");
		}
		teachers.remove(index);
	}

	public int searchTeacher(String code) {
		return searchTeacher(code, 0, teachers.size() - 1);
	}

	// ----------------------------------------
	public void addStudents(String code, String name, String lastName, String password) throws NullEntityException {
		if (students.size() > 0) {
			sortStudents();
			int index = searchStudent(code);
			if (index != -1) {
				throw new NullEntityException("The student with id: " + code + " already exists!");
			}
		}
		StudentAccount account = new StudentAccount(code, 0);
		students.add(new Student(code, name, lastName, password, account, this));
	}

	public void sortStudents() {

		PersonComparator comparator = new PersonComparator();
		int j;
		Student student;
		for (int i = 1; i < students.size(); i++) {
			student = students.get(i);
			j = i - 1;
			while ((j >= 0) && (comparator.compare(student, students.get(j)) == -1)) {
				students.set(j + 1, students.get(j));
				j--;
			}
			students.set(j + 1, student);
		}
	}

	private int searchStudent(String codeStudent, int low, int high) {
		int mid = low + (high - low) / 2;
		if (high < low) {
			return -1;
		}
		if (students.get(mid).getCode().equals(codeStudent)) {
			return mid;
		} else if (Integer.parseInt(codeStudent) < Integer.parseInt(students.get(mid).getCode())) {
			return searchStudent(codeStudent, low, mid - 1);
		}
		return searchStudent(codeStudent, mid + 1, high);
	}

	public void removeStudent(String code) throws NullEntityException {
		sortStudents();
		int index = searchStudent(code);
		if (index == -1) {
			throw new NullEntityException("The student with code: " + code + " does not exists!");
		}
		students.remove(index);
	}

	public int searchStudent(String code) {
		return searchStudent(code, 0, students.size() - 1);
	}

	// -------------------------------------------------------------------
	public void addDirector(String code, String name, String lastName, String password, double salary)
			throws NullEntityException {
		if (directors.size() > 0) {
			sortDirectors();
			int index = searchDirector(code);
			if (index != -1) {
				throw new NullEntityException("The director with id: " + code + " already exists!");
			}
		}
		directors.add(new Director(code, name, lastName, password, salary, this));
	}

	public void sortDirectors() {
		int size = directors.size();
		Director director = null;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size - 1; j++) {
				if (directors.get(j).compareTo(directors.get(j + 1)) == 1) {
					director = directors.get(j);
					directors.set(j, directors.get(j + 1));
					directors.set(j + 1, director);
				}
			}
		}
	}

	private int searchDirector(String codeDirector, int low, int high) {
		int mid = low + (high - low) / 2;
		if (high < low) {
			return -1;
		}
		if (directors.get(mid).getCode().equals(codeDirector)) {
			return mid;
		} else if (Integer.parseInt(codeDirector) < Integer.parseInt(directors.get(mid).getCode())) {
			return searchDirector(codeDirector, low, mid - 1);
		}
		return searchDirector(codeDirector, mid + 1, high);
	}

	public void removeDirector(String code) throws NullEntityException {
		sortDirectors();
		int index = searchDirector(code);
		if (index == -1) {
			throw new NullEntityException("The director with code: " + code + " does not exists!");
		}
		directors.remove(index);
	}

	public int searchDirector(String code) {
		return searchDirector(code, 0, directors.size() - 1);
	}
}
