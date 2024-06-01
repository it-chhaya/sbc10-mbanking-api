package co.istad.mbanking.features.account;

import co.istad.mbanking.domain.Account;
import co.istad.mbanking.domain.AccountType;
import co.istad.mbanking.domain.User;
import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import co.istad.mbanking.features.accounttype.AccountTypeRepository;
import co.istad.mbanking.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;

    @Override
    public AccountResponse createNew(AccountCreateRequest accountCreateRequest) {

        // Validate account type
        AccountType accountType = accountTypeRepository
                .findByAlias(accountCreateRequest.accountType())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account Type has not been found"
                ));

        // Validate user
        User user = userRepository
                .findByUuid(accountCreateRequest.userUuid())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User has not been found"
                ));

        // Validate account no
        if (accountRepository.existsByActNo(accountCreateRequest.actNo())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Account no has already been existed");
        }

        // Validate balance
        if (accountCreateRequest.balance().compareTo(BigDecimal.valueOf(10)) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Balance 10$ is required to create account"
            );
        }

        // Transfer DTO to Domain Model
        Account account = new Account();
        account.setActNo(accountCreateRequest.actNo());
        account.setBalance(accountCreateRequest.balance());
        account.setAccountType(accountType);
        account.setUser(user);

        // System generate data
        account.setActName(user.getName());
        account.setIsHidden(false);
        account.setTransferLimit(BigDecimal.valueOf(1000));

        // Save account into database and get latest data back
        account = accountRepository.save(account);

        // Transfer Domain Model to DTO
        return AccountResponse.builder()
                .alias(account.getAlias())
                .actName(account.getActName())
                .actNo(account.getActNo())
                .balance(account.getBalance())
                .accountType(account.getAccountType().getName())
                .build();
    }

    @Override
    public List<AccountResponse> findList() {
        return List.of();
    }

    @Override
    public AccountResponse findByActNo(String actNo) {
        return null;
    }

}
