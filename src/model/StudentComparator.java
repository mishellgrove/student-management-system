package model;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

	@Override
	public int compare(Student arg0, Student arg1) {
		String lastName1 = arg0.getLastName();
		String lastName2 = arg0.getLastName();
		int comparation = lastName1.compareTo(lastName2);
		if (comparation < 0) {
			return -1;
		} else if (comparation > 0) {
			return 1;
		}
		return 0;
	}

}