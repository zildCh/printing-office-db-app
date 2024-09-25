package Util;

import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.asciithemes.TA_Grid;
import de.vandermeer.asciithemes.TA_GridConfig;

import entities.*;
import repositories.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Level;

import static Util.HibernateFactory.getSessionFactory;

public class Menu {
    OrdersRepository orderRepo = new OrdersRepository();
    ContractsRepository contractRepo = new ContractsRepository();
    WorkshopsRepository workshopRepo = new WorkshopsRepository();
   ProductsRepository productRepo = new ProductsRepository();
    AddressRepository addressRepo = new AddressRepository();
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        new Menu();
    }

    public Menu() {
        getSessionFactory().getMetamodel().getEntities();
        boolean flagMainMenu = true;
        while (flagMainMenu) {
            mainMenu();
            System.out.print("Enter a menu item: ");
            int num = getInputShort();
            switch (num) {
                default -> System.out.println("\nThere is no such menu item!");
                case 0 -> flagMainMenu = false;
                case 1 -> ContractsAddMenu();
                case 2 -> ContractsEditMenu();
                case 3 -> showContracts(contractRepo.getAll());
                case 4 -> ContractsDeleteMenu(contractRepo.getAll());
                case 5 -> ContractsSearchMenu();
                case 11 -> WorkshopsAddMenu();
                case 12 -> WorkshopsEditMenu();
                case 13 -> showWorkshops(workshopRepo.getAll());
                case 14 -> WorkshopsDeleteMenu(workshopRepo.getAll());
                case 15 -> WorkshopsSearchMenu();
                case 21 -> ProductsAddMenu();
                case 22 -> ProductsEditMenu();
                case 23 -> showProducts(productRepo.getAll());
                case 24 -> ProductsDeleteMenu(productRepo.getAll());
                case 25 -> ProductsSearchMenu();
                case 31 -> OrdersAddMenu();
                case 32 -> OrdersEditMenu();
                case 33 -> showOrders(orderRepo.getAll());
                case 34 -> OrdersDeleteMenu(orderRepo.getAll());
                case 35 -> OrdersSearchMenu();
            }
        }
    }

    void mainMenu() {
        System.out.println("\n------------------------ 29. Print office ------------------------");
        System.out.println("CONTRACTS\t\tWORKSHOPS\t\tPRODUCTS\t\tORDERS");
        System.out.println("1. Add\t\t\t11. Add \t\t21. Add\t\t\t31. Add\t\t0. Exit");
        System.out.println("2. Edit\t\t\t12. Edit\t\t22. Edit\t\t32. Edit\t");
        System.out.println("3. Show\t\t\t13. Show\t\t23. Show\t\t33. Show\t");
        System.out.println("4. Remove\t\t14. Remove\t\t24. Remove\t\t34. Remove\t");
        System.out.println("5. Search\t\t15. Search\t\t25. Search\t\t35. Search\t");
    }


    void WorkshopsAddMenu() {
        System.out.println("\nAdding a workshop");
        boolean flag = true;
        int id = 0;
        while(flag) {
            id = getRandomInt();
            if(workshopRepo.checkPrimaryKeyNotExists(id, Columns.WORKSHOP_ID)) flag = false;
        }
        System.out.print("Enter workshop name: ");
        String name = getInputString();
        System.out.print("Enter workshop phone: ");
        String number = getInputPhone();
        System.out.print("Enter manager name: ");
        String manager = getInputString();
        Workshops workshop = new Workshops(id,name,manager,number);
        workshopRepo.save(workshop);
        System.out.println("Object added!");
    }

    void WorkshopsEditMenu() {
        System.out.println("\nEditing Workshops table");
        System.out.print("Enter the line number to change: ");
        int numberStr = getInputIntegerLessMax(workshopRepo.workshopsList .size());
       Workshops workshop = workshopRepo.workshopsList.get(numberStr);
        boolean flag = true;
        while (flag) {
            System.out.println("\nChanging the Workshops table");
            showRaw(workshop.getRawStringList());
            System.out.println("1. workshop name");
            System.out.println("2. workshop phone");
            System.out.println("3. manager name");
            System.out.println("4. Save changes and exit");
            System.out.println("0. Exit to main menu");
            int num = getInputShort();

            switch (num) {
                case 0 -> flag = false;

                case 1 -> {
                    System.out.print("Enter new workshop name: ");
                    workshop.setName(getInputString());
                }
                case 2-> {
                    System.out.print("Enter new workshop phone:");
                    workshop.setPhoneNum(getInputPhone());
                }
                case 3 -> {
                    System.out.print("Enter new workshop manager name: ");
                    workshop.setManager(getInputString());
                }
                case 4-> {
                    workshopRepo.edit(workshop);
                    System.out.println("Changes saved!");
                    return;
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
        }
    }

    void WorkshopsDeleteMenu(List<Workshops> list) {
        showWorkshops(workshopRepo.getAll());
        System.out.println("\nDeleting from the Workshops table");
        System.out.print("Enter the row number to delete: ");
        int numberStr = getInputIntegerLessMax(workshopRepo.workshopsList.size());
        Workshops workshop = workshopRepo.workshopsList.get(numberStr);
        workshopRepo.delete(workshop);
        System.out.printf("\nRow %d successfully deleted!\n", numberStr);
    }

    void WorkshopsSearchMenu() {
        while (true) {
            System.out.println("\nSearch in the Contracts table");
            System.out.println("1. workshop name");
            System.out.println("2. workshop phone");
            System.out.println("3. manager name");
            System.out.println("0. exit");
            System.out.print("Enter a menu item: ");
            int num = getInputShort();
            if (num != 0) System.out.print("Enter the search query: ");
            String str = "";
            List<Workshops> result = null;
            switch (num) {
                case 0 -> {
                    return;
                }
                case 1 -> { //??? ??????????
                    str = getInputString();
                    result = workshopRepo.searchByString(str, Columns.WORKSHOPS_NAME);
                }
                case 2 -> { //??? ??????????
                    str = getInputPhone();;
                    result = workshopRepo.searchByString(str, Columns.WORKSHOPS_PHONE);
                }
                case 3 -> { //??? ??????????
                    str = getInputString();
                    result = workshopRepo.searchByString(str, Columns.WORKSHOPS_MANAGER);
                }

                default -> System.out.println("\nInvalid menu item entered!");
            }
            System.out.println("Search results for the query \"" + str + "\"");
            if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
            else showWorkshops(result);
        }
    }
    void showWorkshops(List<Workshops> list) {
        if (list.isEmpty()) {
            System.out.println("\nThis table is empty!");
            return;
        }
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(Workshops.columns());
        for (int i = 0; i < list.size(); i++) {
            table.addRule();
            List<String> lt = list.get(i).getRawStringList();
            lt.set(0, String.valueOf(i));
            table.addRow(lt);
        }
        table.addRule();
        TA_Grid myGrid = TA_Grid.create("grid using UTF-8 light border characters");

        myGrid.addCharacterMap(TA_GridConfig.RULESET_NORMAL, ' ', '-', '|', '+', '+', '+', '+', '+', '+', '+', '+',
                '+');
        table.getContext().setGrid(myGrid);
        String tableStr = table.render(100);
        System.out.println(tableStr);
    }


    void ContractsAddMenu() {
        System.out.println("\nAdding a contract");
        boolean flag = true;
        Integer num = -1;
        while(flag) {
            System.out.print("Enter the contract number: ");
            num = getInputInteger();
            if(contractRepo.checkPrimaryKeyNotExists(num, Columns.CONTRACT_NUMBER)) flag = false;
            else System.out.println("The contract with this number already exists!");
        }
        System.out.print("Enter the customer's name: ");
        String name = getInputString();
        System.out.println("Entering the registration date");
        String regDate = getInputDate();
        System.out.println("Entering the done date");
        String doneDate = getInputDate();
        System.out.print("Enter the city: ");
        String city = getInputString();
        System.out.print("Enter the street: ");
        String street = getInputString();
        System.out.print("Enter the building number: ");
        Short numBuilding = getInputShort();

        flag = true;
        int id = 0;
        while(flag) {
            id = getRandomInt();
            if(addressRepo.checkPrimaryKeyNotExists(id, Columns.ADDRESS_ID)) flag = false;
        }
        Address address = new Address(id ,city,street, numBuilding);
        Contracts contract = new Contracts(num,name,regDate,doneDate, address);
        addressRepo.save(address);
        contractRepo.save(contract);
        System.out.println("Object added!");
    }

    void ContractsEditMenu() {
        System.out.println("Editing the Contracts table");
        System.out.print("Enter the line number to change: ");
        int numberStr = getInputIntegerLessMax(contractRepo.contractList.size());
        Contracts contract = contractRepo.contractList.get(numberStr);

        while (true) {
            System.out.println("\nEditing the Contracts table");
            showRaw(contract.getRawStringList());
            System.out.println("1. contract number");
            System.out.println("2. customer's name");
            System.out.println("3. registration date");
            System.out.println("4. done date");
            System.out.println("5. city");
            System.out.println("6. street");
            System.out.println("7. building number");
            System.out.println("8. save changes and exit");
            System.out.println("0. exit to the main menu");
            System.out.println("Enter a menu item: ");
            int n = getInputShort();

            switch (n) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    boolean flagCode = true;
                    Integer number = -1;
                    while(flagCode) {
                        System.out.print("Enter a new contract number:");
                        number = getInputInteger();
                        if(contractRepo.checkPrimaryKeyNotExists(number, Columns.CONTRACT_NUMBER)) flagCode = false;
                        else System.out.println("A contract with this number already exists!");
                    }
                    contract.setContract_code(number);
                }
                case 2 -> {
                    System.out.print("Enter a new customer name: ");
                    contract.setName(getInputString());
                }
                case 3-> {
                    System.out.println("Enter a new registration date");
                    String regDate = getInputDate();
                    contract.setRegDate(regDate);
                }
                case 4-> {
                    System.out.println("Enter a new done date");
                    String doneDate = getInputDate();
                    contract.setDoneDate(doneDate);
                }
                case 5-> {
                    System.out.print("Enter a new city: ");
                    contract.getAddress().setCity(getInputString());
                }
                case 6-> {
                    System.out.print("Enter a street: ");
                    contract.getAddress().setStreet(getInputString());
                }
                case 7-> {
                    System.out.print("Enter a building number: ");
                    contract.getAddress().setNBuild(getInputShort());
                }
                case 8-> {
                    contractRepo.edit(contract);
                    System.out.println("Changes saved!");
                    return;
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
        }
    }

    void showContracts(List<Contracts> list) {
        if (list.isEmpty()) {
            System.out.println("\nThis table is empty!");
            return;
        }
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(Contracts.columns());
        for (int i = 0; i < list.size(); i++) {
            table.addRule();
            List<String> lt = list.get(i).getRawStringList();
            lt.set(0, String.valueOf(i));
            table.addRow(lt);
        }
        table.addRule();
        TA_Grid myGrid = TA_Grid.create("grid using UTF-8 light border characters");

        myGrid.addCharacterMap(TA_GridConfig.RULESET_NORMAL, ' ', '-', '|', '+', '+', '+', '+', '+', '+', '+', '+',
                '+');
        table.getContext().setGrid(myGrid);
        table.getContext().setGrid(myGrid);
        String tableStr = table.render(110);
        System.out.println(tableStr);
    }

    void ContractsDeleteMenu(List<Contracts> list) {
        showContracts(contractRepo.getAll());
        System.out.println("\nDeleting from the Contracts table");
        System.out.print("Enter the row number to delete: ");
        int numberStr = getInputIntegerLessMax(contractRepo.contractList.size());
        Contracts contract = contractRepo.contractList.get(numberStr);
        contractRepo.delete(contract);
        addressRepo.delete(contract.getAddress());
        System.out.printf("\nRow %d successfully deleted!\n", numberStr);
    }

    void ContractsSearchMenu() {
        while (true) {
            System.out.println("\nSearch in the Contracts table");
            System.out.println("1. Contract number");
            System.out.println("2. Customer name");
            System.out.println("3. Registration date");
            System.out.println("4. Done date");
            System.out.println("5. City");
            System.out.println("6. Street");
            System.out.println("0. Exit");
            System.out.print("Enter a menu item: ");
            int num = getInputShort();
            if (num != 0) System.out.print("Enter the search query: ");
            String str = "";
            List<Contracts> result = null;
            switch (num) {
                case 0 -> {
                    return;
                }
                case 1 -> { //????? ????????
                    Integer number = getInputInteger();
                    str = number.toString();
                    result = contractRepo.searchByRange100(str, Columns.CONTRACT_NUMBER);
                }
                case 2 -> { //??? ??????????
                    str = getInputString();
                    result = contractRepo.searchByString(str, Columns.CONTRACT_NAME);
                }
                case 3-> { //???? ???????????
                    str = getInputString();
                    result = contractRepo.searchByString(str, Columns.CONTRACT_DATE);
                }
                case 4-> { //???? ??????????
                    str = getInputString();
                    result = contractRepo.searchByString(str, Columns.COMPLETION_DATE);
                }
                case 5-> { //?????
                    str = getInputString();
                    result = contractRepo.searchByAddressString(str, Columns.ADDRESS_CITY);
                }
                case 6-> { //?????
                    str = getInputString();
                    result = contractRepo.searchByAddressString(str, Columns.ADDRESS_STREET);
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
            System.out.println("Search results for the query \"" + str + "\"");
            if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
            else showContracts(result);
        }
    }

    void OrdersAddMenu() {
        System.out.println("\nAdding a order");
        boolean flag = true;
        int id = 0;
        while(flag) {
            id = getRandomInt();
            if(orderRepo.checkPrimaryKeyNotExists(id, Columns.ORDER_ID)) flag = false;
        }
        Contracts contract = null;
        System.out.println("contract selection");
        System.out.println("1. Select from the entire list");
        System.out.println("2. Use the search");
        System.out.println("0. Use the Cancel operation and exit to the main menu search");
        switch (getInputShort()) {
            case 0 -> {
                return;
            }
            case 1 -> {
                showContracts(contractRepo.getAll());
                System.out.print("Enter the line number to add: ");
                int numStr = getInputIntegerLessMax(contractRepo.contractList.size());
                contract = contractRepo.contractList.get(numStr);
            }
            case 2 -> {
                while (true) {
                    System.out.println("\nSearch the Contracts table");
                    System.out.println("1. contract name");
                    System.out.println("2. contract date");
                    System.out.println("3. done date");
                    System.out.print("Enter a menu item: ");
                    int n = getInputShort();
                    if (n != 0) System.out.print("Enter a search query: ");
                    String str = "";
                    List<Contracts> result = null;
                    switch (n) {
                        case 1 -> { //name
                            str = getInputString();
                            result = contractRepo.searchByString(str, Columns.CONTRACT_NAME);
                        }
                        case 2 -> { //manager
                            str = getInputDate();
                            result = contractRepo.searchByString(str, Columns.CONTRACT_DATE);
                        }
                        case 3 -> { //phone
                            str = getInputDate();
                            result = contractRepo.searchByString(str, Columns.COMPLETION_DATE);
                        }
                        default -> System.out.println("\nAn incorrect menu item has been entered!");
                    }
                    System.out.println("Search results for \"" + str + "\"");
                    if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
                    else {
                        showContracts(result);
                        System.out.print("Enter the line number to add: ");
                        int numStr = getInputIntegerLessMax(result.size());
                        contract = result.get(numStr);
                        break;
                    }
                }
            }
        }
        boolean flagCode = true;
        Integer idc = -1;
        showProducts(productRepo.getAll());
        while(flagCode) {
            System.out.print("Enter a product code:");
            idc = getInputInteger();
            if(productRepo.checkPrimaryKeyNotExists(idc, Columns.PRODUCT_CODE))
            System.out.println("A product with this code doesn't exists!");
            else
                flagCode = false;
        }

        System.out.print("Enter the quantity: ");
        int quantity = getInputInteger();
        Orders order = new Orders(id, quantity, idc, contract);
        orderRepo.save(order);
        System.out.println("Object added!");
    }

    void OrdersEditMenu() {
        System.out.println("\nEditing Orders table");
        System.out.print("Enter the line number to change: ");
        int numberStr = getInputIntegerLessMax(orderRepo.orderList.size());
        Orders order = orderRepo.orderList.get(numberStr);

        while (true) {
            System.out.println("\nEditing orders table");
            showRaw(order.getRawStringList());
            System.out.println("1. product code");
            System.out.println("2. quantity");
            System.out.println("3. contracts");
            System.out.println("4. save changes and exit");
            System.out.println("0. exit");
            System.out.println("enter menu item: ");
            int nn = getInputShort();

            switch (nn) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    boolean flagCode = true;
                    Integer idc = -1;
                    while(flagCode) {
                        System.out.print("Enter a new product code:");
                        idc = getInputInteger();
                        if(productRepo.checkPrimaryKeyNotExists(idc, Columns.PRODUCT_CODE))
                            System.out.println("A product with this code doesn't exists!");
                        else
                            flagCode = false;
                    }
                    order.setProductCode (idc);
                }
                case 3 -> {
                    Contracts contract = null;
                    System.out.println("New contracts selection");
                    System.out.println("1. Select from the entire list");
                    System.out.println("2. Use the search");
                    System.out.println("0. Use the Cancel operation and exit to the main menu search");
                    switch (getInputShort()) {
                        case 0 -> {
                            return;
                        }
                        case 1 -> {
                            showContracts(contractRepo.getAll());
                            System.out.print("Enter the line number to add: ");
                            int numStr = getInputIntegerLessMax(contractRepo.contractList.size());
                            contract = contractRepo.contractList.get(numStr);
                        }
                        case 2 -> {
                            while (true) {
                                System.out.println("\nSearch the Contracts table");
                                System.out.println("1. contract name");
                                System.out.println("2. contract date");
                                System.out.println("3. done date");
                                System.out.print("Enter a menu item: ");
                                int n = getInputShort();
                                if (n != 0) System.out.print("Enter a search query: ");
                                String str = "";
                                List<Contracts> result = null;
                                switch (n) {
                                    case 1 -> { //name
                                        str = getInputString();
                                        result = contractRepo.searchByString(str, Columns.CONTRACT_NAME);
                                    }
                                    case 2 -> { //manager
                                        str = getInputDate();
                                        result = contractRepo.searchByString(str, Columns.CONTRACT_DATE);
                                    }
                                    case 3 -> { //phone
                                        str = getInputDate();
                                        result = contractRepo.searchByString(str, Columns.COMPLETION_DATE);
                                    }
                                    default -> System.out.println("\nAn incorrect menu item has been entered!");
                                }
                                System.out.println("Search results for \"" + str + "\"");
                                if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
                                else {
                                    showContracts(result);
                                    System.out.print("Enter the line number to add: ");
                                    int numStr = getInputIntegerLessMax(result.size());
                                    contract = result.get(numStr);
                                    break;
                                }
                            }
                        }
                    }
                    order.setContracts(contract);
                }
                case 2-> {
                    System.out.print("Enter new products quantity: ");
                    order.setQuantity(getInputInteger());
                }
                case 4-> {
                    orderRepo.edit(order);
                    System.out.println("Changes saved!");
                    return;
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
        }
    }
    void OrdersDeleteMenu(List<Orders> list) {
        showOrders(orderRepo.getAll());
        System.out.println("\nDeleting from the Products table");
        System.out.print("Enter the row number to delete: ");
        int numberStr = getInputIntegerLessMax(productRepo.productList.size());
        Orders order = orderRepo.orderList.get(numberStr);
        orderRepo.delete(order);
        System.out.printf("\nRow %d successfully deleted!\n", numberStr);
    }

    void OrdersSearchMenu() {
        while (true) {
            System.out.println("\nSearch in the Oredrs table");
            System.out.println("1. quantity");
            System.out.println("2. product code");
            System.out.println("3. customer name");
            System.out.println("4. contract date");
            System.out.println("5. done date");
            System.out.println("0. Exit");
            System.out.print("Enter a menu item: ");
            int num = getInputShort();
            if (num != 0) System.out.print("Enter the search query: ");
            String str = "";
            List<Orders> result = null;
            switch (num) {
                case 0 -> {
                    return;
                }
                case 1 -> { //????? ????????
                    Integer number = getInputInteger();
                    str = number.toString();
                    result = orderRepo.searchByRange100(str, Columns.ORDERS_QUANTITY);
                }
                case 2 -> { //??? ??????????
                    Integer code = getInputInteger() ;
                    str = code.toString() ;
                    result = orderRepo.searchByRange100(str, Columns.ORDERS_PRODUCT_CODE);
                }
                case 3-> { //???? ??????????
                    str = getInputString();
                    result = orderRepo.searchByContractsString(str, Columns.CONTRACT_NAME);
                }
                case 4-> { //?????
                    str = getInputDate();
                    result = orderRepo.searchByContractsString(str, Columns.CONTRACT_DATE);
                }
                case 5-> { //?????
                    str = getInputDate();
                    result = orderRepo.searchByContractsString(str, Columns.COMPLETION_DATE);
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
            System.out.println("Search results for the query \"" + str + "\"");
            if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
            else showOrders(result);
        }
    }

    void showOrders(List<Orders> list) {
        if (list.isEmpty()) {
            System.out.println("\nThis table is empty!");
            return;
        }
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(Orders.columns());
        for (int i = 0; i < list.size(); i++) {
            table.addRule();
            List<String> lt = list.get(i).getRawStringList();
            lt.set(0, String.valueOf(i));
            table.addRow(lt);
        }
        table.addRule();
        TA_Grid myGrid = TA_Grid.create("grid using UTF-8 light border characters");

        myGrid.addCharacterMap(TA_GridConfig.RULESET_NORMAL, ' ', '-', '|', '+', '+', '+', '+', '+', '+', '+', '+',
                '+');
        table.getContext().setGrid(myGrid);
        String tableStr = table.render(90);
        System.out.println(tableStr);
    }


    void ProductsAddMenu() {
        System.out.println("\nAdding a product");
        boolean flag = true;
        Integer num = -1;
        Integer code  = -1;
        while(flag) {
            System.out.print("Enter the product number: ");
            num = getInputInteger();
            if(productRepo.checkPrimaryKeyNotExists(num, Columns.PRODUCT_CODE)) flag = false;
            else System.out.println("The product with this number already exists!");
        }
        Workshops workshop = null;
        System.out.println("workshops selection");
        System.out.println("1. Select from the entire list");
        System.out.println("2. Use the search");
        System.out.println("0. Use the Cancel operation and exit to the main menu search");
        switch (getInputShort()) {
            case 0 -> {
                return;
            }
            case 1 -> {
                showWorkshops(workshopRepo.getAll());
                System.out.print("Enter the line number to add: ");
                int numStr = getInputIntegerLessMax(workshopRepo.workshopsList.size());
                workshop = workshopRepo.workshopsList.get(numStr);
            }
            case 2 -> {
                while (true) {
                    System.out.println("\nSearch the Workshops table");
                    System.out.println("1. Name");
                    System.out.println("2. Manager");
                    System.out.println("3. Phone number");
                    System.out.print("Enter a menu item: ");
                    int n = getInputShort();
                    if (n != 0) System.out.print("Enter a search query: ");
                    String str = "";
                    List<Workshops> result = null;
                    switch (n) {
                        case 1 -> { //name
                            str = getInputString();
                            result = workshopRepo.searchByString(str, Columns.WORKSHOPS_NAME);
                        }
                        case 2 -> { //manager
                            str = getInputString();
                            result = workshopRepo.searchByString(str, Columns.WORKSHOPS_MANAGER);
                        }
                        case 3 -> { //phone
                            str = getInputPhone();
                            result = workshopRepo.searchByString(str, Columns.WORKSHOPS_PHONE);
                        }
                        default -> System.out.println("\nAn incorrect menu item has been entered!");
                    }
                    System.out.println("Search results for \"" + str + "\"");
                    if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
                    else {
                        showWorkshops(result);
                        System.out.print("Enter the line number to add: ");
                        int numStr = getInputIntegerLessMax(result.size());
                        workshop = result.get(numStr);
                        break;
                    }
                }
            }
        }
        flag = true;
        int id = 0;
        while(flag) {
            id = getRandomInt();
            if(productRepo.checkPrimaryKeyNotExists(id, Columns.PRODUCT_CODE)) flag = false;
        }
        System.out.print("Enter products name: ");
        String name = getInputString();
        System.out.print("Enter products cost: ");
        Double cost = getInputDouble();
        Products product = new Products(num, name, cost, workshop);
        productRepo.save(product);
        System.out.println("Object added!");
    }

    void ProductsEditMenu() {
        System.out.println("\nEditing products table");
        System.out.print("Enter the line number to change: ");
        int numberStr = getInputIntegerLessMax(productRepo.productList.size());
        Products product = productRepo.productList.get(numberStr);

        while (true) {
            System.out.println("\nEditing products table");
            showRaw(product.getRawStringList());
            System.out.println("1. product code");
            System.out.println("2. product name");
            System.out.println("3. cost");
            System.out.println("4. workshops");
            System.out.println("5. save changes and exit");
            System.out.println("0. exit");
            System.out.println("enter menu item: ");
            int nn = getInputShort();

            switch (nn) {
                case 0 -> {
                    return;
                }
                case 1 -> {
                    boolean flagCode = true;
                    Integer number = -1;
                    while(flagCode) {
                        System.out.print("Enter a new product code:");
                        number = getInputInteger();
                        if(productRepo.checkPrimaryKeyNotExists(number, Columns.PRODUCT_CODE)) flagCode = false;
                        else System.out.println("A product with this code already exists!");
                    }
                    product.setProduct_code(number);
                }
                case 4 -> {
                    Workshops workshop = null;
                    System.out.println("New workshops selection");
                    System.out.println("1. Select from the entire list");
                    System.out.println("2. Use the search");
                    System.out.println("0. Use the Cancel operation and exit to the main menu search");
                    switch (getInputShort()) {
                        case 0 -> {
                            return;
                        }
                        case 1 -> {
                            showWorkshops(workshopRepo.getAll());
                            System.out.print("Enter the line number to add: ");
                            int numStr = getInputIntegerLessMax(workshopRepo.workshopsList.size());
                            workshop = workshopRepo.workshopsList.get(numStr);
                        }
                        case 2 -> {
                            while (true) {
                                System.out.println("\nSearch the Workshops table");
                                System.out.println("1. Name");
                                System.out.println("2. Manager");
                                System.out.println("3. Phone number");
                                System.out.print("Enter a menu item: ");
                                int n = getInputShort();
                                if (n != 0) System.out.print("Enter a search query: ");
                                String str = "";
                                List<Workshops> result = null;
                                switch (n) {
                                    case 1 -> { //????????
                                        str = getInputString();
                                        result = workshopRepo.searchByString(str, Columns.WORKSHOPS_NAME);
                                    }
                                    case 2 -> { //??????
                                        str = getInputString();
                                        result = workshopRepo.searchByString(str, Columns.WORKSHOPS_MANAGER);
                                    }
                                    case 3 -> { //color
                                        str = getInputPhone();
                                        result = workshopRepo.searchByString(str, Columns.WORKSHOPS_PHONE);
                                    }
                                    default -> System.out.println("\nAn incorrect menu item has been entered!");
                                }
                                System.out.println("Search results for \"" + str + "\"");
                                if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
                                else {
                                    showWorkshops(result);
                                    System.out.print("Enter the line number to add: ");
                                    int numStr = getInputIntegerLessMax(result.size());
                                    workshop = result.get(numStr);
                                    break;
                                }
                            }
                        }
                    }
                    product.setWorkshops(workshop);
                }
                case 3-> {
                    System.out.print("Enter new products price: ");
                    product.setUnit_price(getInputDouble());
                }
                case 2 -> {
                    System.out.print("Enter a new product name: ");
                    product.setName(getInputString());
                }
                case 5-> {
                    productRepo.edit(product);
                    System.out.println("Changes saved!");
                    return;
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
        }
    }
    void ProductsDeleteMenu(List<Products> list) {
        showProducts(productRepo.getAll());
        System.out.println("\nDeleting from the Products table");
        System.out.print("Enter the row number to delete: ");
        int numberStr = getInputIntegerLessMax(productRepo.productList.size());
        Products product = productRepo.productList.get(numberStr);
        productRepo.delete(product);
        System.out.printf("\nRow %d successfully deleted!\n", numberStr);
    }

    void ProductsSearchMenu() {
        while (true) {
            System.out.println("\nSearch in the Products table");
            System.out.println("1. Product code");
            System.out.println("2. Product name");
            System.out.println("3. unit cost");
            System.out.println("4. workshop name");
            System.out.println("5. workshop manager");
            System.out.println("6. workshop phone");
            System.out.println("0. Exit");
            System.out.print("Enter a menu item: ");
            int num = getInputShort();
            if (num != 0) System.out.print("Enter the search query: ");
            String str = "";
            List<Products> result = null;
            switch (num) {
                case 0 -> {
                    return;
                }
                case 1 -> { //????? ????????
                    Integer number = getInputInteger();
                    str = number.toString();
                    result = productRepo.searchByRange100(str, Columns.PRODUCT_CODE);
                }
                case 2 -> { //??? ??????????
                    str = getInputString();
                    result = productRepo.searchByString(str, Columns.PRODUCT_NAME);
                }
                case 3-> { //?????????
                    Double cost = getInputDouble();
                    str = cost.toString();
                    result = productRepo.searchByRange100(str, Columns.PRODUCT_UNIT_PRICE);
                }
                case 4-> { //???? ??????????
                    str = getInputString();
                    result = productRepo.searchByWorkshopString(str, Columns.WORKSHOPS_NAME);
                }
                case 5-> { //?????
                    str = getInputString();
                    result = productRepo.searchByWorkshopString(str, Columns.WORKSHOPS_MANAGER);
                }
                case 6-> { //?????
                    str = getInputPhone();
                    result = productRepo.searchByWorkshopString(str, Columns.WORKSHOPS_PHONE);
                }
                default -> System.out.println("\nInvalid menu item entered!");
            }
            System.out.println("Search results for the query \"" + str + "\"");
            if (result.isEmpty() || result.equals(null)) System.out.println("\nNothing found!");
            else showProducts(result);
        }
    }

    void showProducts(List<Products> list) {
        if (list.isEmpty()) {
            System.out.println("\nThis table is empty!");
            return;
        }
        AsciiTable table = new AsciiTable();
        table.addRule();
        table.addRow(Products.columns());
        for (int i = 0; i < list.size(); i++) {
            table.addRule();
            List<String> lt = list.get(i).getRawStringList();
            lt.set(0, String.valueOf(i));
            table.addRow(lt);
        }
        table.addRule();
        TA_Grid myGrid = TA_Grid.create("grid using UTF-8 light border characters");

        myGrid.addCharacterMap(TA_GridConfig.RULESET_NORMAL, ' ', '-', '|', '+', '+', '+', '+', '+', '+', '+', '+',
                '+');
        table.getContext().setGrid(myGrid);
        String tableStr = table.render(90);
        System.out.println(tableStr);
    }


    void showRaw(List<String> str) {
        for (int i = 1; i < str.size(); i++) {
            if (str.get(i).equals("")) System.out.print("_ ");
            System.out.print(str.get(i) + "  ");
        }
        System.out.println();
    }

    String getInputDate() {
        boolean flag = true;
        String inputDate;
        short day = 0, month = 0, year = 0;
        while (flag) {
            System.out.print("Enter date: ");
            day = getInputShort();
            System.out.print("Enter month: ");
            month = getInputShort();
            System.out.print("Enter year: ");
            year = getInputShort();
            if (!isValidDate(day,month,year)) System.out.println("The date entered is incorrect! Try again");
            else flag = false;
        }
        String format = "%d.%d.%d";
        if (day < 10) format = "0%d.%d.%d";
        if (month < 10) format = "%d.0%d.%d";
        if (day < 10 && month < 10) format = "0%d.0%d.%d";
        inputDate = String.format(format, day,month, year);
        return inputDate;
    }

    boolean isValidDate(short day, short month, short year) {
        short[] daysInMonth = { 0,31,28,31,30,31,30,31,31,30,31,30,31 };

        if (year < 2020 || year > 2050) return false;
        if (year % 4 == 0)
            daysInMonth[2] = 29;
        if ((month < 1) || (month > 12)) return false;
        return (day >= 1) && (day <= daysInMonth[month]);
    }

    String getInputString() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    Integer getInputIntegerLessMax(int max) {
        boolean flag = true;
        int num = 0;
        while (flag) {
            num = getInputInteger();
            if (num >= max) System.out.printf("\n The value cannot be greater than %d\n", max-1);
            else flag = false;
        }
        return num;
    }

    Integer getInputInteger() {
        Integer number = 0;
        boolean flag = true;
        while(flag) {
            try {
                String str = getInputString();
                number = Integer.parseInt(str);
                if (number < 0) System.out.println("The value cannot be negative!");
                else flag = false;
            } catch(NumberFormatException e) {
                System.out.println("The entered string is not a number!");
            }
        }
        return number;
    }

    String getInputPhone() {
        String str ="";
        boolean flag = true;
        while(flag) {
            try {
                str = getInputString();
                Long number = Long.parseLong(str);
                if (number < 0 || number > 89999999999L)
                    System.out.println("\n The value is out of the range of acceptable values!");
                else flag = false;
            } catch(NumberFormatException e) {
                System.out.println("The entered string is not a number!");
            }
        }
        return str;
    }

    Double getInputDouble() {
        Double number = 0.d;
        boolean flag = true;
        while(flag) {
            try {
                String str = getInputString();
                number = Double.parseDouble(str);
                flag = false;
            } catch(NumberFormatException e) {
                System.out.println("The entered string is not a number!");
            }
        }
        return number;
    }

    Short getInputShort() {
        Short number = 0;
        boolean flag = true;
        while(flag) {
            try {
                String str = getInputString();
                number = Short.parseShort(str);
                flag = false;
            }
            catch(NumberFormatException e) {
                System.out.println("The entered string is not a " +
                        " number or the number is out of the range of acceptable values!");
            }
        }
        return number;
    }

    public int getRandomInt() {
        return (int) (Math.random()*2000000000) + 100;
    }
}
