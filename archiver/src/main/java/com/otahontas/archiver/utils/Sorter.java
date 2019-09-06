package com.otahontas.archiver.utils;

/**
 * Basic implementations for selection sort for different types of arrays
 * */

public class Sorter {

    /**
     * Copies char array, sorts it and returns new sorted array
     * Uses basic selection sort for max input size of ~20.
     * @param original Array to be sorted
     * @return Sorted array
     * */

    public char[] sortCharArray(char[] original) {
        int l = original.length;
        char[] nums = new char[l];

        for (int i = 0; i < l; i++) nums[i] = original[i];

	    for (int currentPlace = 0 ; currentPlace < l - 1; currentPlace++) {
	    	int smallest = Integer.MAX_VALUE;
	    	int smallestAt = currentPlace+1;
	    	for (int check = currentPlace; check < l;check++) {
	    		if (nums[check] < smallest) {
	    			smallestAt = check;
	    			smallest = nums[check];
	    		}
	    	}
	    	char temp = nums[currentPlace];
	    	nums[currentPlace] = nums[smallestAt];
	    	nums[smallestAt] = temp;
	    }
        return nums;
    }
}
