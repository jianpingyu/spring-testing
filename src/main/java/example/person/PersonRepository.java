package example.person;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String> {

    /**
     * 根据名字查询用户
     * @param lastName
     * @return
     */
    Optional<Person> findByLastName(String lastName);

}
