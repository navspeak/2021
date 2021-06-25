package nav.bst;

import java.util.Arrays;
/*
Not BST based solution
 */
public class CountOfSmallerNumberThanSelf_1 {
    //https://leetcode.com/problems/how-many-numbers-are-smaller-than-the-current-number/
        public int[] smallerNumbersThanCurrent(int[] nums) {
            int[] result = new int[nums.length];
            Element[] elements = new Element[nums.length];

            for (int i = 0; i <nums.length; i++){
                elements[i] = new Element(nums[i], i);
            }

            Arrays.sort(elements);

            for (int i = 0; i <nums.length; ){
                result[elements[i].index] = i;
                int fixedI = i;
                while(i < nums.length -1 && elements[i+1].value == elements[i].value) {
                    i++;
                    result[elements[i].index] = fixedI;
                }
                i++;

            }

            return result;

        }

        private static class Element implements Comparable<Element> {
            int value;
            int index;
            Element (int value, int index){
                this.value = value;
                this.index = index;
            }

            public int compareTo(Element o){
                return Integer.compare(this.value, o.value);
            }
        }

}
