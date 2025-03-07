package Project.User_Login.domain;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter @Setter
public class Address {
    private String street; //도로명 주소
    private String city; //도시
    private String zipcode; //우편번호

    public Address(String street, String zipcode, String city) {
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;
    }

    protected Address() {

    }
}
