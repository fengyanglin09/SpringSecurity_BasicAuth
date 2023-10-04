package diy.com.springboot3security.config.exp0010CreateAFilterWithMultiAuthenticationProvidersWithConfiger;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Collection;

@Getter
@Setter
public class MyUser extends User {

    private static final long serialVersionUID = 1L;

    public MyUser(String username, String password, boolean enabled, boolean accountNonExpired,
                  boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
                  String firstName, String lastName, String emailaddress, LocalDateTime birthdate) {
        super(username, password, enabled, accountNonExpired,credentialsNonExpired,accountNonLocked, authorities);
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullname = firstName + " " + lastName;
        this.emailaddress = emailaddress;
        this.birthdate = birthdate;
    }

    private String firstName;
    private String lastName;
    private String fullname;
    private String emailaddress;
    private LocalDateTime birthdate;;


    @Override
    public String toString() {
        return "MyUser firstName=" + firstName + ", lastName=" + lastName + ", name=" + fullname + ", emailaddress=" + emailaddress + ", birthdate=" + birthdate
                + "] " + super.toString();
    }


}
