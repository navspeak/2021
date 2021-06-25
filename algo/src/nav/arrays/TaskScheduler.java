package nav.arrays;

import java.util.Arrays;

public class TaskScheduler {
    public int leastInterval(char[] tasks, int n) {
        int time = 0;
        int[] task_map = new int[26];
        for (int i = 0; i< tasks.length; i++){
            task_map[tasks[i] - 'A'] = task_map[tasks[i] - 'A']+1;
        }

        Arrays.sort(task_map);
        int max_freq = task_map[25] - 1; // 1st can be done without needing to wait
        int idle = max_freq * n;

        for (int i =24; i >= 0 ; i--){
            if (task_map[i] == 0) break;
            idle = idle - Math.min(task_map[i],max_freq);
        }

        return idle > 0 ? tasks.length + idle: tasks.length;

    }
}
/*

Given a characters array tasks, representing the tasks a CPU needs to do, where each letter represents a different task. Tasks could be done in any order. Each task is done in one unit of time. For each unit of time, the CPU could complete either one task or just be idle.

However, there is a non-negative integer n that represents the cooldown period between two same tasks (the same letter in the array), that is that there must be at least n units of time between any two same tasks.

Return the least number of units of times that the CPU will take to finish all the given tasks.



Example 1:

Input: tasks = ["A","A","A","B","B","B"], n = 2
Output: 8
Explanation:
A -> B -> idle -> A -> B -> idle -> A -> B
There is at least 2 units of time between any two same tasks.

   tasks = ['A', 'B', 'C',...] - any order - 1 task = 1 unit of time (idle or complete a task)
   n = 5 => cooldown time period between two same tasks => A ---5 ---- A

    a a a b b b   => 2

    a - 1                  [a_1:4, a_2: 4, b_4:5]
    a - skip
    a - skip
    b - 1
    b - skip
    b- skip

*/
