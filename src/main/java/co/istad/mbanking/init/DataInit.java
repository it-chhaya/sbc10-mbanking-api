package co.istad.mbanking.init;

import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.user.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final UserRepository userRepository;

    @PostConstruct
    void init() {

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setName("Chan Chhaya");
        user.setGender("Male");
        user.setPhoneNumber("098459947");
        user.setPin("1234");
        user.setPassword("qwer");
        user.setNationalCardId("123456789");
        user.setProfileImage("user/avatar.png");
        user.setStudentCardId("ISTAD-000001");
        user.setIsDeleted(false);
        user.setIsBlocked(false);

        User user2 = new User();
        user2.setUuid(UUID.randomUUID().toString());
        user2.setName("Leo Messi");
        user2.setGender("Male");
        user2.setPhoneNumber("077459947");
        user2.setPin("1234");
        user2.setPassword("qwer");
        user2.setNationalCardId("88889999");
        user2.setProfileImage("user/avatar.png");
        user2.setIsDeleted(false);
        user2.setIsBlocked(false);

        //userRepository.save(user);
        //userRepository.save(user2);
        userRepository.saveAll(List.of(user, user2));
    }

}
