import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * На входе имеется файл в формате json, содержащий информацию о каком-то количестве организаций, в т.ч. названия, адреса, номера телефонов, ИНН, ОГРН, а также о ценных бумагах, которыми владеет каждая компания.
 * Необходимо сформировать на основе исходного файла коллекцию объектов без потери информации, где каждый объект представляет одну организацию.
 * <p>
 * Для сформированной коллекции:
 * Вывести все имеющиеся компании в формате «Краткое название» – «Дата основания 17/01/98»;
 * <p>
 * Вывести все ценные бумаги (их код, дату истечения и полное название организации-владельца), которые просрочены
 * на текущий день, а также посчитать суммарное число всех таких бумаг;
 * <p>
 * На запрос пользователя в виде даты «ДД.ММ.ГГГГ», «ДД.ММ,ГГ», «ДД/ММ/ГГГГ» и «ДД/ММ/ГГ» вывести название
 * и дату создания всех организаций, основанных после введенной даты;
 * <p>
 * На запрос пользователя в виде кода валюты, например EU, USD, RUB и пр. выводить id и коды ценных бумаг,
 * использующих заданную валюту.
 */

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "src/main/resources/organization.json";
        JsonReader reader = new JsonReader(new FileReader(fileName));
        Type collectionType = new TypeToken<Collection<Organization>>() {
        }.getType();
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsString())).create();
        List<Organization> organizationList = gson.fromJson(reader, collectionType);


        test1(organizationList);

        test2(organizationList);

        String date1 = "23.04.2004";
        test3(organizationList, date1);
        String date2 = "27.12,10";
        test3(organizationList, date2);
        String date3 = "01/05/2007";
        test3(organizationList, date3);
        String date4 = "03/10/06";
        test3(organizationList, date4);


        String money1 = "EU";
        test4(organizationList, money1);
        String money2 = "USD";
        test4(organizationList, money2);
        String money3 = "RUB";
        test4(organizationList, money3);


    }

    public static void test1(List<Organization> organizationList) {

        System.out.println("___________________TEST_1____________________");
        //Вывести все имеющиеся компании в формате «Краткое название» – «Дата основания 17/01/98»;
        organizationList.stream().forEach(organization -> System.out.println(organization.name_short + " - " + "Дата основания " + organization.egrul_date));

        System.out.println();
    }

    public static void test2(List<Organization> organizationList) {

        System.out.println("___________________TEST_2____________________");
        //Добавляем в список все securities
        List<Organization.Securities> securitiesList = new ArrayList<>();
        organizationList.stream().forEach(organization -> securitiesList.addAll(organization.securities));

        //Вывести все ценные бумаги (их код, дату истечения и полное название организации-владельца), которые просрочены
        //на текущий день, а также посчитать суммарное число всех таких бумаг;
        securitiesList.stream().filter(securities -> securities.date_to.isAfter(LocalDate.now()))
                .forEach(securities -> System.out.println(securities.code + " " + securities.date_to + " " + securities.name_full));

        long count = securitiesList.stream().filter(securities -> securities.date_to.isAfter(LocalDate.now()))
                .count();
        System.out.println("Общее число бумаг: " + count);

        System.out.println();

    }

    public static void test3(List<Organization> organizationList, String date) {

        System.out.println("___________________TEST_3____________________");
        //На запрос пользователя в виде даты «ДД.ММ.ГГГГ», «ДД.ММ,ГГ», «ДД/ММ/ГГГГ» и «ДД/ММ/ГГ» вывести название
        //и дату создания всех организаций, основанных после введенной даты;
        String resultDate = date.replaceAll("[\\.\\/\\,]", "-"); //заменяем знаки ./, на -

        DateTimeFormatter formatter = null;
        if (resultDate.length() == 10) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        } else if (resultDate.length() == 8) {
            formatter = DateTimeFormatter.ofPattern("dd-MM-yy");
        }
        LocalDate localDate = LocalDate.parse(resultDate, formatter);

        organizationList.stream().filter(organization -> organization.egrul_date.isAfter(localDate))
                .forEach(organization -> System.out.println(organization.name_short + " " + organization.egrul_date));

        System.out.println();
    }

    public static void test4(List<Organization> organizationList, String money) {

        System.out.println("___________________TEST_4____________________");
        //На запрос пользователя в виде кода валюты, например EU, USD, RUB и пр. выводить id и коды ценных бумаг,
        //использующих заданную валюту.


        List<Organization.Securities> securitiesList1 = new ArrayList<>();
        organizationList.stream().forEach(organization -> securitiesList1.addAll(organization.securities));

        if (money.equals("EU")) {
            securitiesList1.stream().filter(securities -> securities.currency.code.equals("EUR"))
                    .forEach(securities -> System.out.println(securities.id + " " + securities.code + " " + securities.currency.code));
        } else if (money.equals("USD")) {
            securitiesList1.stream().filter(securities -> securities.currency.code.equals("USD"))
                    .forEach(securities -> System.out.println(securities.id + " " + securities.code + " " + securities.currency.code));
        } else if (money.equals("RUB")) {
            securitiesList1.stream().filter(securities -> securities.currency.code.equals("RUB"))
                    .forEach(securities -> System.out.println(securities.id + " " + securities.code + " " + securities.currency.code));
        }

        System.out.println();
    }
}
