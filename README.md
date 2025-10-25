#  dailyGo

**dailyGo** is a Java-based desktop application developed to automate and digitize the operations of newspaper agencies.  
It streamlines daily workflows such as customer enrollment, hawker management, area allocation, billing, and collection tracking.  

By replacing manual record-keeping with a centralized and user-friendly interface, dailyGo ensures accuracy, efficiency, and transparency in managing daily newspaper distribution and billing activities.  
The project is scalable and allows multiple agencies to operate independently with secure login access and cloud data management.

---

## Technologies Used

1. **Frontend:** JavaFX (FXML, SceneBuilder)  
2. **Backend:** Core Java (JDBC, MVC Architecture)  
3. **Database:** MySQL  
4. **IDE:** IntelliJ IDEA 
5. **Libraries Used:** JavaFX Controls, FXML, MySQL Connector/J    
6. **Data Visualization:** TableView (using MVC + Bean architecture)

---

## Features

### 1. Admin & Manager Dashboard
- **Secure Login:** Admin/Manager authentication to ensure data privacy.  
- **Centralized Access:** Acts as the navigation hub for all modules (Customers, Papers, Hawkers, Billing, etc.).  
- **Dynamic Loading:** Opens modules in new windows using `FXMLLoader`.

---

### 2. Paper Master
- **Newspaper Entry:** Allows adding new newspapers directly into the system. 
- **Paper Management:** Add, update, delete, and search newspaper records.  
- **Details Tracked:** Paper name, language, and price.  
- **Billing Integration:** Automatically fetches newspaper prices during billing.

---

### 3. Hawkers Console
- **Recruitment Management:** Add or modify hawker details such as name, contact, address, Aadhaar, and joining date.  
- **Auto Hawker ID:** Generated dynamically using a combination of name and contact number.  
- **Area Allocation:** Assign delivery areas from predefined list without manual typing.  
- **Lifecycle Management:** Recruit, modify, or remove hawkers.

---

### 4. Area Master
- **Simple Setup:** Add and store unique service areas for the agency.  
- **Data Integrity:** Prevents duplicate area entries.  
- **Integration:** Used by Hawker and Customer modules for dropdown selections.

---

### 5. Customer Enrollment
- **Primary Key:** Customer identified uniquely by mobile number.  
- **Auto-Fetch System:** Retrieves details if the customer already exists.  
- **Dynamic Area & Hawker Loading:** Automatically lists valid areas and assigned hawkers.  
- **Paper Selection:** Dual ListBox system for selecting multiple newspapers with corresponding prices.  
- **Operations:** Add new customer, update details, stop service, or search records.  
- **Billing Integration:** Calculates charges automatically based on date of joining.

---

### 6. Billing Module
- **Automated Bill Calculation:** Computes monthly bills from subscription dates.  
- **Prorated Billing:** Adjusts charges for mid-month joins or holidays.  
- **Database Integration:** Stores records in billing and collections tables with status flags.  
- **Payment Handling:** Updates status from unpaid to paid with real-time tracking.  
- **Collection Summary:** Helps track total collected and pending amounts.

---

### 7. Billing Dashboard
- **Status Filters:** View *Paid* and *Unpaid* bills.  
- **Date Range Filter:** Filter results between selected dates and list all relevant records.
- **Total Calculation:** Automatically sums collected or pending totals.
- **Export to Excel (CSV):** Allows exporting billing records to a CSV file with a custom save location and filename.  
- **Quick Access:** The exported file opens automatically after saving, simplifying data backup, reporting, and sharing.

---

### 8. Bill Collector
- **Hawker Access:** Allows hawkers to record customer payments.  
- **Pending Bills Display:** Shows unpaid customer bills in `TableView`.  
- **Payment Update:** Marks bills as paid instantly.  
- **Collection Validation:** Keeps real-time payment records synchronized.

---

### 9. Customer Manager
- **Filter Options:** Search customers by *Area*, *Newspaper*, or *Hawker*.  
- **Data Display:** Shows results in `TableView` using Bean classes combining multiple filters.
- **Scalable Design:** Handles customers subscribed to multiple papers.

---

