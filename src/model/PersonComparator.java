package model;

import java.util.Comparator;

/**
 * The Class PersonComparator.
 */
public class PersonComparator implements Comparator<Person> {

	/**
	 * Compare.
	 *
	 * @param o1 the o 1
	 * @param o2 the o 2
	 * @return the int
	 */
	@Override
	public int compare(Person o1, Person o2) {
		int comparation = 0;
		int auxiliarCode1 = Integer.parseInt(o1.getCode());
		int auxiliarCode2 = Integer.parseInt(o2.getCode());

		if (auxiliarCode1 < auxiliarCode2) {
			comparation = -1;
		} else if (auxiliarCode1 > auxiliarCode2) {
			comparation = 1;
		}

		return comparation;
	}

}
