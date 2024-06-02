package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {

    /**
     * Create a new account
     * @param accountCreateRequest {@link AccountCreateRequest}
     * @return {@link AccountResponse}
     */
    AccountResponse createNew(AccountCreateRequest accountCreateRequest);


    /**
     * Find all accounts by pagination
     * @param pageNumber is current page request from client
     * @param pageSize is size of records per page from client
     * @return {@link List<AccountResponse>}
     */
    Page<AccountResponse> findList(int pageNumber, int pageSize);


    /**
     * Find account by account no
     * @param actNo is no of account
     * @return {@link AccountResponse}
     */
    AccountResponse findByActNo(String actNo);

}
