/*
Итоговая контрольная работа по блоку специализация
Урок 2. Итоговая контрольная работа
26.07.2023
Калинин Павел
3703

14. Написать программу, имитирующую работу реестра домашних животных.
В программе должен быть реализован следующий функционал:
14.1 Завести новое животное
14.2 определять животное в правильный класс
14.3 увидеть список команд, которое выполняет животное
14.4 обучить животное новым командам
14.5 Реализовать навигацию по меню

15.Создайте класс Счетчик, у которого есть метод add(), увеличивающий̆
значение внутренней̆int переменной̆на 1 при нажатие “Завести новое
животное” Сделайте так, чтобы с объектом такого типа можно было работать в
блоке try-with-resources. Нужно бросить исключение, если работа с объектом
типа счетчик была не в ресурсном try и/или ресурс остался открыт. Значение
считать в ресурсе try, если при заведения животного заполнены все поля.
*/

import java.io.BufferedReader;
import java.io.Console;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TotalKalininPavel3703 {

    static public void main(String[] args) throws Exception {
        new TotalKalininPavel3703();
    }

    private BufferedReader bufferedReader = null;
    private List<Animal> animals = null;

    class Animal {
        static private IMyCounter myCounter = null;
        private int id = 0;
        private String name = "";
        private long birthday = 0;
        private String commands = "";

        static public boolean setMyCounter(IMyCounter myCounter) {
            if (Animal.myCounter == null) {
                Animal.myCounter = myCounter;
                return true;
            }
            return false;
        }

        public Animal(String name, long birthday, String commands) {
            this.id = myCounter.add();
            this.name = name;
            this.birthday = birthday;
            this.commands = commands;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public long getBirthday() {
            return birthday;
        }

        public String getCommands() {
            return commands;
        }

        public boolean addCommand(String command) {
            if (commands.contains(command))
                return false;
            commands += ", " + command;
            return true;
        }

        public String toString() {
            return id + "; " + name + "; " + getDate(birthday) + "; " + commands;
        }
    }

    abstract class AnimalClass extends Animal {

        public AnimalClass(String name, long birthday, String commands) {
            super(name, birthday, commands);
        }

        abstract public String getAnimalClass();

        public String toString() {
            return getAnimalClass() + "; " + super.toString();
        }
    }

    class Pets extends AnimalClass {

        static public final String ANIMAL_CLASS = "Домашнее животное";
        static public final String[] ANIMAL_KINDS = { "собака", "кошка", "хомяк" };

        public Pets(String name, long birthday, String commands) {
            super(name, birthday, commands);
        }

        public String getAnimalClass() {
            return ANIMAL_CLASS;
        }
    }

    class PackAnimals extends AnimalClass {

        static public final String ANIMAL_CLASS = "Вьючное  животное";
        static public final String[] ANIMAL_KINDS = { "лошадь", "верблюд", "осел" };

        public PackAnimals(String name, long birthday, String commands) {
            super(name, birthday, commands);
        }

        public String getAnimalClass() {
            return ANIMAL_CLASS;
        }
    }

    interface IMyCounter {
        int add();
    }

    class MyCounter implements IMyCounter, AutoCloseable {
        private int index = 0;

        public MyCounter(int initValue) {
            this.index = initValue;
        }

        synchronized public int add() {
            return ++index;
        }

        public void close() throws Exception {
            ;
        }

        public boolean isClose() {
            return true;
        }
    }

    public TotalKalininPavel3703() throws Exception {

        animals = new ArrayList<Animal>();

        Console console = System.console();
        if (console == null) {
            System.out.println("Консоль отсутствует.");
            return;
        }

        try (MyCounter myCounter = new MyCounter(9);
                BufferedReader bufferedReaderLocal = new BufferedReader(
                        new InputStreamReader(System.in, console.charset()));) {
            Animal.setMyCounter(myCounter);
            bufferedReader = bufferedReaderLocal;
            try {
                animals.add(new Pets("собака", getDate(1, 1, 2023), "сидеть"));
                animals.add(new Pets("собака", getDate(2, 1, 2023), "сидеть, лежать"));
                animals.add(new PackAnimals("лошадь", getDate(1, 1, 2023), "стоять"));
                animals.add(new PackAnimals("верблюд", getDate(1, 1, 2023), "жевать"));
            } catch (ParseException pe) {
                System.out.println("Неправильно введена дата.");
                pe.fillInStackTrace();
                return;
            }
            mainMenu();
        }

        // "Нужно бросить исключение, если работа с объектом типа счетчик была не в
        // ресурсном try"
        // Не понимаю, как это отлавливается.
        // Такому меня не учили ни на курсе "Исключения", ни на других курсах, ни в
        // книжках, ни в жизни.
        // Откуда пользователю Счетчка знать, где был создан объект, в каком окружении.
        // И зачем вообще пользователю проверять это.
        // Если немного поумничать, то, по принципу единственной отвественности SOLID,
        // пользователь,
        // который будет использовать Счетчик, не должен заниматься посторонними вещами.

        // Как можно проверить, что ресурс был закрыт, если его область видимости в
        // блоке try-with-resources.
        // if (!myCounter.isClose())
        // throw new RuntimeException("Ресурс Счетчик не закрыт.");

    }

    private long getDate(int day, int month, int year) throws ParseException {
        String dd = (day > 9) ? String.valueOf(day) : "0" + day;
        String mm = (month > 9) ? String.valueOf(month) : "0" + month;
        return (new SimpleDateFormat("ddMMyyyy")).parse(dd + mm + year).getTime();
    }

    private String getDate(long date) {
        return (new SimpleDateFormat("dd.MM.yyyy")).format(new Date(date));
    }

    private void mainMenu() {
        /*
         * 14.1 Завести новое животное
         * 14.2 определять животное в правильный класс
         * 14.3 увидеть список команд, которое выполняет животное
         * 14.4 обучить животное новым командам
         * 14.5 Реализовать навигацию по меню
         */

        while (true) {
            try {
                int n = 0;
                String s = "";
                boolean b = false;

                System.out.println("---------------------------------------------");
                System.out.println("Меню:");
                System.out.println("1) Завести новое животное");
                System.out.println("2) Список комaнд, которое выполняет животное");
                System.out.println("3) Обучить животное новым командам");
                System.out.println("4) Список животных");
                System.out.println("0) Выход");
                s = bufferedReader.readLine();
                n = Integer.valueOf(s);
                if (n == 1) {
                    menuNewAnimal();
                } else if (n == 2) {
                    System.out.println("Введите номер животного: ");
                    s = bufferedReader.readLine();
                    n = Integer.valueOf(s);
                    b = false;
                    for (Animal animal : animals)
                        if (animal.getId() == n) {
                            b = true;
                            System.out.println(animal.commands);
                            break;
                        }
                    if (!b)
                        System.out.println("Животное не найдено.");
                } else if (n == 3) {
                    System.out.println("Введите номер животного: ");
                    s = bufferedReader.readLine();
                    n = Integer.valueOf(s);
                    b = false;
                    for (Animal animal : animals)
                        if (animal.getId() == n) {
                            b = true;
                            System.out.println("Введите название новой команды: ");
                            s = bufferedReader.readLine();
                            b = animal.addCommand(s);
                            if (b)
                                System.out.println(animal);
                            else
                                System.out.println("Такая команда уже присутствует. " + animal);
                            break;
                        }
                    if (!b)
                        System.out.println("Животное не найдено.");
                } else if (n == 4) {
                    for (Animal animal : animals)
                        System.out.println(animal);
                } else if (n == 0) {
                    return;
                } else {
                    System.out.println("Ошибка.");
                }
            } catch (Exception e) {
                System.out.println("Ошибка.");
            }
        } // while
    }

    private void menuNewAnimal() {
        String s = "";
        int kind = 0;
        long date = 0;
        String commands = "";

        try {

            System.out.println("---------------------------------------------");
            System.out.println("Введите вид животного:");
            for (int i = 0; i < Pets.ANIMAL_KINDS.length; i++)
                System.out.println((i + 1) + ") " + Pets.ANIMAL_KINDS[i]);
            for (int i = 0; i < PackAnimals.ANIMAL_KINDS.length; i++)
                System.out.println((i + 1 + Pets.ANIMAL_KINDS.length) + ") " + PackAnimals.ANIMAL_KINDS[i]);
            System.out.println("0) Выход");
            s = bufferedReader.readLine();
            kind = Integer.valueOf(s);
            if (kind == 0)
                return;
            if (kind < 0 || kind > Pets.ANIMAL_KINDS.length + PackAnimals.ANIMAL_KINDS.length) {
                System.out.println("Ошибка.");
                return;
            }

            System.out.println("Введите год рождения животного: ");
            s = bufferedReader.readLine();
            int year = Integer.valueOf(s);
            System.out.println("Введите месяц рождения животного: ");
            s = bufferedReader.readLine();
            int month = Integer.valueOf(s);
            System.out.println("Введите день месяца рождения животного: ");
            s = bufferedReader.readLine();
            int day = Integer.valueOf(s);

            try {

                date = getDate(day, month, year);
            } catch (ParseException pe) {
                System.out.println("Неправильный формат даты. " + pe.getMessage());
                return;
            }

            System.out.println("Введите перечень команд, которые может выполнять животное: ");
            commands = bufferedReader.readLine();

            if ((kind - 1) < Pets.ANIMAL_KINDS.length) {
                Animal animal = new Pets(Pets.ANIMAL_KINDS[kind - 1], date, commands);
                animals.add(animal);
                System.out.println(animal);
            } else {
                Animal animal = new PackAnimals(
                        PackAnimals.ANIMAL_KINDS[kind - 1 - Pets.ANIMAL_KINDS.length], date, commands);
                animals.add(animal);
                System.out.println(animal);
            }

        } catch (Exception e) {
            System.out.println("Ошибка.");
        }
    }

}