package leetcode;

import java.util.*;

/**
 * @author : zhaochengming
 * @date : 2022/5/15 16:07
 * @description : https://leetcode.cn/problems/employee-importance/
 */
public class L0690_EmployeeImportance {

    static class Employee {
        public int id;
        public int importance;
        public List<Integer> subordinates;
    }

    static class Solution {
        public int getImportance(List<Employee> employees, int id) {

            Map<Integer, Employee> employeeMap = new HashMap<>();
            for (Employee employee: employees) {
                employeeMap.put(employee.id, employee);
            }

            int importance = 0;
            Deque<Integer> stack = new LinkedList<>();
            stack.push(id);
            while (!stack.isEmpty()) {
                Employee employee = employeeMap.get(stack.pop());
                importance += employee.importance;
                if (!employee.subordinates.isEmpty()) {
                    stack.addAll(employee.subordinates);
                }
            }
            return importance;
        }
    }
}
