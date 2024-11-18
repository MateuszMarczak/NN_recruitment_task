package pl.marczak.nn.repository;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import pl.marczak.nn.model.Account;

public interface AccountRepository extends CrudRepository<Account, String> {

  Optional<Account> findAccountByUuid(String uuid);
}
