package com.otahontas.archiver.utils;

/**
 * Basic implementations for selection sort for different types of arrays
 * */

public class Sorter {

    /**
     * Copies array, sorts it and returns new sorted array
     * @param original Array to be sorted
     * */

    public int[] sortArray(int[] original) {
        int l = original.length;
        int[] nums = new int[l];

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
	    	int temp = nums[currentPlace];
	    	nums[currentPlace] = nums[smallestAt];
	    	nums[smallestAt] = temp;
	    }
        return nums;
    }
}
