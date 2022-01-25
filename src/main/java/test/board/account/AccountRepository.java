package test.board.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.board.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByName(String name);
}
