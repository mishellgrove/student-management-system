package model;

import java.util.Comparator;

/**
 * The Class StudentComparator.
 */
public class StudentComparator implements Comparator<Student> {

	/**
	 * Compare.
	 *
	 * @param arg0 the arg 0
	 * @param arg1 the arg 1
	 * @return the int
	 */
	@Override
	public int compare(Student arg0, Student arg1) {
		String lastName1 = arg0.getLastName();
		String lastName2 = arg1.getLastName();
		int comparation = lastName1.compareToIgnoreCase(lastName2);
		if (comparation < 0) {
			return -1;
		} else if (comparation > 0) {
			return 1;
		}
		return 0;
	}

}
