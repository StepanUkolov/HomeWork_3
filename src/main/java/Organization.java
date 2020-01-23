import java.time.LocalDate;
import java.util.List;

public class Organization {

    int id;
    String code;
    String name_full;
    String name_short;
    String inn;
    CompanyType company_type;
    String ogrn;
    LocalDate egrul_date;
    Country country;
    String fio_head;
    String address;
    String phone;
    String e_mail;
    String www;
    boolean is_resident;
    List<Securities> securities;


    public class CompanyType {
        int id;
        String name_short;
        String name_full;

        @Override
        public String toString() {
            return "CompanyType{" +
                    "id=" + id +
                    ", name_short='" + name_short + '\'' +
                    ", name_full='" + name_full + '\'' +
                    '}';
        }
    }

    public class Country {
        int id;
        String code;
        String name;

        @Override
        public String toString() {
            return "Country{" +
                    "id=" + id +
                    ", code='" + code + '\'' +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public class Securities {
        int id;
        String code;
        String name_full;
        String cfi;
        LocalDate date_to;
        LocalDate state_reg_date;
        State state;
        Currency currency;

        public class State {
            int id;
            String name;

            @Override
            public String toString() {
                return "State{" +
                        "id=" + id +
                        ", name='" + name + '\'' +
                        '}';
            }
        }

        public class Currency {
            int id;
            String code;
            String name_short;
            String name_full;

            @Override
            public String toString() {
                return "Currency{" +
                        "id=" + id +
                        ", code='" + code + '\'' +
                        ", name_short='" + name_short + '\'' +
                        ", name_full='" + name_full + '\'' +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "Securities{" + "\n" +
                    "id=" + id +
                    ", code='" + code + '\'' + "\n" +
                    ", name_full='" + name_full + '\'' + "\n" +
                    ", cfi='" + cfi + '\'' + "\n" +
                    ", date_to=" + date_to + "\n" +
                    ", state_reg_date=" + state_reg_date + "\n" +
                    ", state=" + state + "\n" +
                    ", currency=" + currency + "\n" +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "Organization{" + "\n" +
                "id=" + id +
                ", code='" + code + '\'' + "\n" +
                ", name_full='" + name_full + '\'' + "\n" +
                ", name_short='" + name_short + '\'' + "\n" +
                ", inn='" + inn + '\'' + "\n" +
                ", company_type=" + company_type + "\n" +
                ", ogrn='" + ogrn + '\'' + "\n" +
                ", egrul_date=" + egrul_date + "\n" +
                ", country=" + country + "\n" +
                ", fio_head='" + fio_head + '\'' + "\n" +
                ", address='" + address + '\'' + "\n" +
                ", phone='" + phone + '\'' + "\n" +
                ", e_mail='" + e_mail + '\'' + "\n" +
                ", www='" + www + '\'' + "\n" +
                ", is_resident=" + is_resident + "\n" +
                ", securities=" + securities + "\n" +
                '}';
    }
}
