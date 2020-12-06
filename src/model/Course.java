package model;

import java.util.ArrayList;
import java.util.Collections;

import customExceptions.BinaryTreeCastException;
import customExceptions.EntityRepeatedException;
import customExceptions.NullEntityException;
import model.util.BinaryTree;

/**
 * The Class Course.
 */
public class Course implements ICourse, Comparable<Course> {

	/** The teacher. */
	private Teacher teacher;

	/** The registers. */
	private ArrayList<Register> registers;

	/** The students. */
	private BinaryTree<Person> students;

	/** The id. */
	private String id;

	/** The name. */
	private String name;

	/** The description. */
	private String description;

	/** The school. */
	private VirtualSchool school;

	/**
	 * Instantiates a new course.
	 *
	 * @param id          the id
	 * @param name        the name
	 * @param description the description
	 * @param teacher     the teacher
	 * @param students    the students
	 * @param school      the school
	 */
	public Course(String id, String name, String description, Teacher teacher, BinaryTree<Person> students,
			VirtualSchool school) {
		registers = new ArrayList<Register>();
		this.students = students;
		this.id = id;
		this.name = name;
		this.description = description;
		this.teacher = teacher;
		this.school = school;
	}

	/**
	 * Instantiates a new course.
	 *
	 * @param id          the id
	 * @param name        the name
	 * @param description the description
	 * @param teacher     the teacher
	 * @param school      the school
	 */
	public Course(String id, String name, String description, Teacher teacher, VirtualSchool school) {
		registers = new ArrayList<Register>();
		this.id = id;
		this.name = name;
		this.description = description;
		this.teacher = teacher;
		this.school = school;
	}

	/**
	 * Adds the register.
	 *
	 * @param id      the id
	 * @param student the student
	 * @throws EntityRepeatedException the entity repeated exception
	 */
	public void addRegister(String id, Student student) throws EntityRepeatedException {
		Register newRegister = new Register(id, student);
		if (registers.contains(newRegister)) {
			throw new EntityRepeatedException("The register: " + newRegister.getId() + " already exists!");
		}
		newRegister.getCourses().add(this);
		registers.add(newRegister);
	}

	/**
	 * Removes the register.
	 *
	 * @param id the id
	 * @throws NullEntityException the null entity exception
	 */
	public void removeRegister(String id) throws NullEntityException {
		RegisterComparator comparator = new RegisterComparator();
		Collections.sort(registers, comparator);
		int index = searchRegister(id);
		if (index == -1) {
			throw new NullEntityException("The register with id: " + id + " does not exist!");
		}
		registers.remove(index);
	}

	/**
	 * Search register.
	 *
	 * @param registerId the register id
	 * @return the int
	 */
	public int searchRegister(String registerId) {
		return searchRegister(registers, registerId, 0, registers.size()-1);
	}

	/**
	 * Search register.
	 *
	 * @param registers  the registers
	 * @param registerId the register id
	 * @param low        the low
	 * @param high       the high
	 * @return the int
	 */
	private int searchRegister(ArrayList<Register> registers, String registerId, int low, int high) {
		int mid = (high + low) / 2;
		if (high < low) {
			return -1;
		}
		if (registers.get(mid).getId().equals(registerId)) {
			return mid;
		} else if (Integer.parseInt(registerId) < Integer.parseInt(registers.get(mid).getId())) {
			return searchRegister(registers, registerId, low, mid - 1);
		}
		return searchRegister(registers, registerId, mid + 1, high);
	}

	/**
	 * Inits the students.
	 *
	 * @throws BinaryTreeCastException the binary tree cast exception
	 */
	public void initStudents() throws BinaryTreeCastException {
		if (registers.size() == 0) {
			throw new BinaryTreeCastException("The registers are empty so there are no students");
		}
		int mid = registers.size() / 2;
		Student root = registers.get(mid).getStudent();
		students = new BinaryTree<Person>(root);
		for (int i = 0; i < registers.size(); i++) {
			if (i == mid) {
				continue;
			}
			Register register = registers.get(i);
			students.add(register.getStudent());
		}
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

	/**
	 * Gets the teacher.
	 *
	 * @return the teacher
	 */
	public Teacher getTeacher() {
		return teacher;
	}

	/**
	 * Sets the teacher.
	 *
	 * @param teacher the new teacher
	 */
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	/**
	 * Gets the registers.
	 *
	 * @return the registers
	 */
	public ArrayList<Register> getRegisters() {
		return registers;
	}

	/**
	 * Sets the registers.
	 *
	 * @param registers the new registers
	 */
	public void setRegisters(ArrayList<Register> registers) {
		this.registers = registers;
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Gets the students.
	 *
	 * @return the students
	 */
	public BinaryTree<Person> getStudents() {
		return students;
	}

	/**
	 * Sets the students.
	 *
	 * @param students the new students
	 */
	public void setStudents(BinaryTree<Person> students) {
		this.students = students;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the new description
	 */
	public void setDescription(String description) {
		this.description = description;
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
		Course other = (Course) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int compareTo(Course o) {
		int id = Integer.parseInt(this.id);
		int idAux = Integer.parseInt(o.getId());
		int comparation = 0;
		if (id < idAux) {
			comparation = -1;
		} else if (id > idAux) {
			comparation = 1;
		}
		return comparation;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + "]";
	}
	
}
