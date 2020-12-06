package model;

import java.util.Comparator;

public class RegisterComparator implements Comparator<Register> {

	@Override
	public int compare(Register arg0, Register arg1) {
		int idOne = Integer.parseInt(arg0.getId());
		int idTwo = Integer.parseInt(arg1.getId());
		int comparation = 0;
		if (idOne < idTwo) {
			comparation = -1;
		} else if (idOne > idTwo) {
			comparation = 1;
		}
		return comparation;
	}

}
