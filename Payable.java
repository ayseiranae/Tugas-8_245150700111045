import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

interface Payable {
    void GetAmount();
}

class Invoice implements Payable {
    String productName;
    int quantity, pricePerItem;

    public Invoice(String productName, int quantity, int pricePerItem) {
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerItem = pricePerItem;
    }

    public static void addInvoice(String productName, int quantity, int pricePerItem, List<Invoice> invoices) {
        Invoice invoice = new Invoice(productName, quantity, pricePerItem);
        invoices.add(invoice);
    }

    @Override
    public void GetAmount() {
        int total = pricePerItem * quantity;
        System.out.println("Total: " + total);
    }

    public int getTotalAmount() {
        return pricePerItem * quantity;
    }
}

class Employee implements Payable {
    int registrationNumber, salaryPerMonth;
    String name;
    List<Invoice> invoices;

    public static List<Employee> employees = new ArrayList<>();

    public Employee(int registrationNumber, String name, int salaryPerMonth) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.salaryPerMonth = salaryPerMonth;
        this.invoices = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public Integer getSalaryPerMonth() {
        return salaryPerMonth;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void addInvoice(Invoice invoice) {
        invoices.add(invoice);
        salaryPerMonth -= invoice.getTotalAmount(); 
    }

    @Override
    public void GetAmount() {
        int total = 0;
        for (Invoice invoice : invoices) {
            total += invoice.getTotalAmount();
        }
        System.out.println("Total purchases by " + name + ": " + total);
    }

    public int getRemainingSalary() {
        return salaryPerMonth;
    }

    static {
        employees.add(new Employee(1904, "Archie Pamungkas", 30000000));
        employees.add(new Employee(2025, "Budiono Siregar", 1000000));
        employees.add(new Employee(2809, "Bombaridilo Crocodilo ", 1500000));
        employees.add(new Employee(9056, "Nanang Emoh", 2000000));
        employees.add(new Employee(2801, "Joko Widodo", 500000));
    }

    public static void showEmp() {
        System.out.println("__________________________________________________");
        Typography.center("Employees List");
        System.out.println();
        System.out.printf("%-25s %-36s\n", "Registration Number", "Name");
        System.out.println("__________________________________________________");
        for (int i = 0; i < employees.size(); i++) {
            Employee p = employees.get(i);
            System.out.printf("%-25s %-36s\n",
                            p.getRegistrationNumber(),
                            p.getName());
        }
        System.out.println("");
    }

    public static Employee find(int regNum) {
        for (Employee e : employees) {
            if (e.getRegistrationNumber() == regNum) {
                return e;
            }
        }
        return null;
    }
}

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Invoice> invoices = new ArrayList<>();

        boolean atmint = true;
        while (atmint) {
            System.out.println("Menu");
            System.out.println("1. Selling");
            System.out.println("2. Checking Salary");
            System.out.println("3. Exit");
            int menu = sc.nextInt();
            sc.nextLine();

            boolean chooseEmp;
            Employee selEmp;
            switch (menu) {
                case 1:
                    Employee.showEmp();
                    chooseEmp = true;
                    selEmp = null;
                    while (chooseEmp) {
                        System.out.println("Select Employee:");
                        int number = sc.nextInt();
                        sc.nextLine();
                        selEmp = Employee.find(number);
                        if (selEmp == null) {
                            System.out.println("Code Not Found!");
                            continue;
                        } else {
                            break;
                        }
                    }
                    
                    ChigarettesList.ShowList();
                    boolean buy = true;
                    while (buy) {
                        System.out.println("Enter the Code:");
                        String code = sc.nextLine();
                        ChigarettesList selCig = ChigarettesList.find(code);
                        if (selCig == null) {
                            System.out.println("Code Not Found!");
                            continue;
                        }
                        System.out.println("Enter the Quantity");
                        int qty = sc.nextInt();
                        sc.nextLine();
            
                        Invoice invoice = new Invoice(selCig.getName(), qty, selCig.getPrice());
                        selEmp.addInvoice(invoice);
                        invoices.add(invoice);
                        invoice.GetAmount();
            
                        System.out.println("Add (y/n)");
                        String add = sc.nextLine();
                        if (add.equalsIgnoreCase("Y")) {
                            continue;
                        } else if (add.equalsIgnoreCase("N")) {
                            buy = false;
                        }
                    }
            
                    int totalAmount = 0;
                    System.out.println("__________________________________________________");
                    Typography.center("Invoice Details:");
                    System.out.println();
                    System.out.printf("%-32s %-5s %-10s\n", "Product", "Qty", "Total Price");
                    for (Invoice invoice : selEmp.getInvoices()) {
                        System.out.printf("%-32s %-5s %-10s\n", invoice.productName, invoice.quantity, invoice.pricePerItem);
                        totalAmount += invoice.getTotalAmount();
                    }
                    System.out.println("__________________________________________________");
                    System.out.println("\nTotal Amount: " + totalAmount);
                    System.out.println("Remaining Salary of " + selEmp.getName() + ": " + selEmp.getRemainingSalary());
                    break;
            
                case 2:
                    Employee.showEmp();
                    chooseEmp = true;
                    selEmp = null;
                    while (chooseEmp) {
                        System.out.println("Select Employee:");
                        int number = sc.nextInt();
                        sc.nextLine();
                        selEmp = Employee.find(number);
                        if (selEmp == null) {
                            System.out.println("Code Not Found!");
                            continue;
                        } else {
                            break;
                        }
                    }
                    System.out.println("Remaining Salary of " + selEmp.getName() + ": " + selEmp.getRemainingSalary());
                    break;
                case 3:
                    atmint = false;
                    break;
            }
        }
        sc.close();
    }
}

class ChigarettesList {
    String code, name;
    int price;
    public static List<ChigarettesList> list = new ArrayList<>();

    public ChigarettesList(String code, String name, int price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
    
    public String getCode() {
        return this.code;
    }
    public String getName() {
        return this.name;
    }
    public int getPrice() {
        return this.price;
    }

    static {
        list.add(new ChigarettesList("1001", "Camel Ungu 12", 16000));
        list.add(new ChigarettesList("1002", "Cigarillos 6", 13000));
        list.add(new ChigarettesList("1003", "Galang Baru 12", 13000));
        list.add(new ChigarettesList("1004", "Gajah Baru 12", 18000));
        list.add(new ChigarettesList("1005", "Gudang Garam Internasional 12", 28000));
        list.add(new ChigarettesList("1006", "Juara Jambu 12", 15000));
        list.add(new ChigarettesList("1007", "Juara Teh", 15000));
        list.add(new ChigarettesList("1008", "Malboro Filter Black 12", 24000));
        list.add(new ChigarettesList("1009", "Mango Boost 16", 32000));
        list.add(new ChigarettesList("1010", "Surya 12", 26000));
    }

    public static void ShowList() {
        System.out.println("__________________________________________________");
        Typography.center("Chigarettes Price List");
        Typography.center("Drop the Cig, Love Your Gig");
        Typography.center("Being a Dad Ain't Just a Fig!");
        System.out.println();
        System.out.printf("%-7s %-36s %-10s\n", "Code", "Name", "Price");
        System.out.println("__________________________________________________");
        for (int i = 0; i < list.size(); i++) {
            ChigarettesList p = list.get(i);
            System.out.printf("%-7s %-36s %-10s\n",
                            p.getCode(),
                            p.getName(),
                            p.getPrice());
        }
    }

    public static ChigarettesList find(String code) {
        for (ChigarettesList c : list) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return c;
            }
        }
        return null;
    }
}

class Typography {
    public void justify(String label, String value) {
        int width = 50;
        int separator = 15;
        System.out.print(String.format("%-" + separator + "s: %" + (width - separator - 2) + "s\n", label, value));
    }

    public static void center(String text) {
        int tengah = (50 - text.length()) / 2;        
        for (int i = 0; i < tengah; i++) {
            System.out.print(" ");
        }        
        System.out.println(text);
    }
}
