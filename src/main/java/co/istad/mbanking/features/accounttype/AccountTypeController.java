package co.istad.mbanking.features.accounttype;

import co.istad.mbanking.features.accounttype.dto.AccountTypeRequest;
import co.istad.mbanking.features.accounttype.dto.AccountTypeResponse;
import co.istad.mbanking.features.accounttype.dto.AccountTypeUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account-types")
@RequiredArgsConstructor
public class AccountTypeController {

    private final AccountTypeService accountTypeService;

    @PatchMapping("/{alias}")
    AccountTypeResponse updateByAlias(@PathVariable String alias,
                                      @RequestBody AccountTypeUpdateRequest accountTypeUpdateRequest) {
        return accountTypeService.updateByAlias(alias, accountTypeUpdateRequest);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountTypeRequest accountTypeRequest) {
        accountTypeService.createNew(accountTypeRequest);
    }

    @GetMapping
    List<AccountTypeResponse> findList() {
        return accountTypeService.findList();
    }

}
