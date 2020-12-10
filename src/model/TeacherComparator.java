package model;

import java.util.Comparator;

public class TeacherComparator implements Comparator<Teacher> {

	@Override
	public int compare(Teacher o1, Teacher o2) {
		String name1 = o1.getName();
		String name2 = o2.getName();
		if (name1.compareToIgnoreCase(name2) > 0) {
			return 1;
		} else if (name1.compareToIgnoreCase(name2) < 0) {
			return -1;
		}
		return 0;
	}

}
