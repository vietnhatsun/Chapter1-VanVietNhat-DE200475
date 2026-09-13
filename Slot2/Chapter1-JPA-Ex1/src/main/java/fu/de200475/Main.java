package fu.de200475;

import fu.de200475.dao.EmployeeDAO;
import fu.de200475.pojo.Employee;
import fu.de200475.pojo.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EmployeeDAO dao = new EmployeeDAO();

        System.out.println("=== 1. CREATE (TODO 0.3) ===");
        Employee emp = new Employee("Nguyen Van A", "a@fpt.edu.vn",
                new BigDecimal("15000000"), Gender.MALE, LocalDate.of(2022, 3, 1));

        dao.save(emp);
        System.out.println("Da tao thanh cong: " + emp);

        System.out.println("\n=== 2. READ by ID & ALL (TODO 0.4) ===");
        Employee found = dao.findById(emp.getId());
        System.out.println("Doc lai theo ID: " + found);

        List<Employee> allEmployees = dao.findAll();
        System.out.println("Danh sach tat ca nhan vien: " + allEmployees.size());

        System.out.println("\n=== 3. READ co dieu kien (TODO 0.5) ===");
        Employee foundByEmail = dao.findByEmail("a@fpt.edu.vn");
        System.out.println("Tim theo email: " + foundByEmail);

        List<Employee> highSalaryEmps = dao.findBySalaryGreaterThanAndActive(new BigDecimal("10000000"));
        System.out.println("Nhan vien luong > 10M: " + highSalaryEmps.size());

        System.out.println("\n=== 4. UPDATE (TODO 0.6) ===");
        found.setSalary(new BigDecimal("17000000"));
        Employee updated = dao.update(found);
        System.out.println("Sau khi update luong: " + updated);

        Employee reChecked = dao.findById(emp.getId());
        System.out.println("Kiem tra lai trong DB: " + reChecked);

//        System.out.println("\n=== 5. DELETE (TODO 0.7) ===");
//        dao.delete(emp.getId());
 //       Employee afterDelete = dao.findById(emp.getId());
   //     System.out.println("Sau khi xoa, tim lai: " + afterDelete); // Ky vong: null

        System.out.println("\n=== 6. CHECK UNIQUE CONSTRAINT (TODO 0.9) ===");
        Employee dup1 = new Employee("User 1", "trung@fpt.edu.vn",
                new BigDecimal("10000000"), Gender.FEMALE, LocalDate.now());
        Employee dup2 = new Employee("User 2", "trung@fpt.edu.vn", // trùng email
                new BigDecimal("11000000"), Gender.MALE, LocalDate.now());

        dao.save(dup1);
        try {
            dao.save(dup2);
            System.out.println("LOI: Khong thay exception nhu ky vong!");
        } catch (RuntimeException ex) {
            System.out.println("Da bat duoc loi trùng email như ky vong: "
                    + ex.getClass().getSimpleName());
        }

        // Cleanup test data
//        if (dup1.getId() != null) {
//            dao.delete(dup1.getId());
  //      }

        dao.close();
        System.out.println("\n=== THUC HANH TODO 3 HOAN THANH ===");
    }
}