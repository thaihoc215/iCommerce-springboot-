package oldimplement.service.impl;

/*import com.example.restservice.dao.PersonRepository;
import com.example.restservice.entity.Employee;
import com.example.restservice.entity.Person;
import com.example.restservice.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    // quick and dirty: inject person dao (use constructor injection)
    private PersonRepository personDAO;

    @Autowired
    PersonServiceImpl(PersonRepository personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public List<Person> findAll() {
        return personDAO.findAll();
    }

    @Override
    public Person findById(int theId) {

        Optional<Person> person = personDAO.findById(theId);
        if (person.isPresent()) {
           return person.get();
        } else
            throw new RuntimeException("Did not find person");
    }

    @Override
    public void save(Person person) {
        personDAO.save(person);
    }

    @Override
    public void deleteById(int theId) {
        personDAO.deleteById(theId);
    }*/
//}
