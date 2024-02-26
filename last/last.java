import java.util.*;
import java.util.stream.Collectors;

class PhoneRow {
    private String unitName;
    private ArrayList<Integer> phoneNumber = new ArrayList<>();

    PhoneRow(String name, int num) {
        System.out.println("Construct: "+name);
        unitName = name;
        phoneNumber.add(num);
    }

    public int getPhoneNumberCounter() {
        return this.getPhones().size();
    }

    public String toString() {
        return String.format("{%s:%s}", this.unitName, this.phoneNumber.toString());
    }

    public String getName() {
        return this.unitName;
    }

    public ArrayList<Integer> getPhones() {
        return this.phoneNumber;
    }
}

class PhoneBook {
    private int ai = 0;
    private static HashMap<String, PhoneRow> phoneBook = new HashMap<>();

    public void add(String unitName, Integer phoneNum) {
        PhoneRow phoneRow = phoneBook.get(unitName);
        if (phoneRow != null) {
            phoneRow.getPhones().add(phoneNum);
        } else {
            phoneBook.put(unitName, new PhoneRow(unitName, phoneNum));
            this.wasAdded(unitName);
        }
    }

    public static HashMap<String, PhoneRow> getPhoneBook() {
        return phoneBook;
    }

    public void wasAdded(String unitName) {
        PhoneRow phoneRow = phoneBook.get(unitName);
        String str = String.format("**** User %s has %d numbers",
                phoneRow.getName(),
                phoneRow.getPhoneNumberCounter()
        );
        System.out.println(str);
    }
}

class Printer {
    public static void main(String[] args) {
        String name1;
        String name2;
        int phone1;
        int phone2;

        if (args.length == 0) {
            name1 = "Smirnov";
            name2 = "Semenov";
            phone1 = 123456;
            phone2 = 654321;
        } else {
            name1 = args[0];
            name2 = args[1];
            phone1 = Integer.parseInt(args[2]);
            phone2 = Integer.parseInt(args[3]);
        }

        PhoneBook myPhoneBook = new PhoneBook();

        myPhoneBook.add(name1, phone1);
        myPhoneBook.add(name1, phone2);
        myPhoneBook.add(name2, phone2);
        myPhoneBook.add(name2, phone1);
        myPhoneBook.add(name2, phone2);

        System.out.println();

        Map<String, PhoneRow> pb = PhoneBook.getPhoneBook();
        LinkedHashMap<String, PhoneRow> lhm = pb.entrySet().stream().sorted(
                (e1, e2) -> Integer.compare(
                        e2.getValue().getPhoneNumberCounter(),
                        e1.getValue().getPhoneNumberCounter()
                )
        ).collect(
                Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                )
        );

        for (var item : lhm.entrySet()) {
            System.out.println(item.toString());
        }
    }
}