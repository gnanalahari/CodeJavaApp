package net.codejava;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository repo;

    @Test
    public void testCreateAndFindUserByEmail() {
        // Step 1: Create and save user
        User user = new User();
        user.setEmail("Lahari@gmail.com");
        user.setPassword("Lahari@23106");
        user.setFirstName("Gnana");
        user.setLastName("Lahari");

        repo.save(user); // Save to DB

        // Step 2: Try to fetch the same user
        User found = repo.findByEmail("Lahari@gmail.com");

        // Step 3: Validate the result
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("Lahari@gmail.com");
    }
}