package uk.gov.mca.beacons.service.accounts;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.dto.AccountHolderDTO;
import uk.gov.mca.beacons.service.dto.AccountHolderIdDTO;
import uk.gov.mca.beacons.service.dto.BeaconDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.exceptions.ResourceNotFoundException;
import uk.gov.mca.beacons.service.mappers.AccountHolderMapper;
import uk.gov.mca.beacons.service.model.AccountHolder;

@RestController
@RequestMapping("/account-holder")
@Tag(name = "Account Holder")
public class AccountHolderController {

  private final AccountHolderMapper accountHolderMapper;

  private final GetAccountHolderByAuthIdService getAccountHolderByAuthIdService;

  private final CreateAccountHolderService createAccountHolderService;

  @Autowired
  public AccountHolderController(
    AccountHolderMapper accountHolderMapper,
    GetAccountHolderByAuthIdService getAccountHolderByAuthIdService,
    CreateAccountHolderService createAccountHolderService
  ) {
    this.accountHolderMapper = accountHolderMapper;
    this.getAccountHolderByAuthIdService = getAccountHolderByAuthIdService;
    this.createAccountHolderService = createAccountHolderService;
  }

  @GetMapping(value = "/auth-id/{authId}")
  public AccountHolderIdDTO getAccountHolderId(
    @PathVariable("authId") String authId
  ) {
    final AccountHolder accountHolder = getAccountHolderByAuthIdService.execute(
      authId
    );

    if (accountHolder == null) throw new ResourceNotFoundException();

    return new AccountHolderIdDTO(accountHolder.getId());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<AccountHolderDTO> createAccountHolder(
    @RequestBody WrapperDTO<AccountHolderDTO> dto
  ) {
    final AccountHolder newAccountHolder = accountHolderMapper.fromDTO(
      dto.getData()
    );

    return accountHolderMapper.toWrapperDTO(
      createAccountHolderService.execute(newAccountHolder)
    );
  }

  @GetMapping(value = "/{accountId}/beacons")
  public WrapperDTO<List<BeaconDTO>> getBeaconsByAccountId(
    @PathVariable("accountId") UUID accountId
  ) {
    return null;
  }
}