package co.istad.mbanking.features.account;

import co.istad.mbanking.features.account.dto.AccountCreateRequest;
import co.istad.mbanking.features.account.dto.AccountResponse;

import java.util.List;

public interface AccountService {

    /**
     * Create a new account
     * @param accountCreateRequest {@link AccountCreateRequest}
     * @return {@link AccountResponse}
     */
    AccountResponse createNew(AccountCreateRequest accountCreateRequest);


    /**
     * Find all accounts
     * @return {@link List<AccountResponse>}
     */
    List<AccountResponse> findList();


    /**
     * Find account by account no
     * @param actNo is no of account
     * @return {@link AccountResponse}
     */
    AccountResponse findByActNo(String actNo);

}
