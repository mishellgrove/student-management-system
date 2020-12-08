package model;

import java.util.Comparator;

public class CourseComparator implements Comparator<Course> {

	@Override
	public int compare(Course arg0, Course arg1) {
		String name1 = arg0.getName();
		String name2 = arg1.getName();
		if (name1.compareTo(name2) > 0) {
			return 1;
		} else if (name1.compareTo(name2) < 0) {
			return -1;
		}

		return 0;
	}

}
