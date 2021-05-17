package uk.gov.mca.beacons.service.mappers;

import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.dto.BeaconUseDTO;
import uk.gov.mca.beacons.service.dto.WrapperDTO;
import uk.gov.mca.beacons.service.model.Activity;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.Environment;
import uk.gov.mca.beacons.service.model.Purpose;

@Service
public class BeaconUseMapper extends BaseMapper {

  public BeaconUseDTO toDTO(BeaconUse domain) {
    final var dto = new BeaconUseDTO();
    dto.setId(domain.getId());
    dto.addAttribute("environment", domain.getEnvironment());
    dto.addAttribute("activity", domain.getActivity());
    dto.addAttribute("otherActivity", domain.getOtherActivity());
    dto.addAttribute("moreDetails", domain.getMoreDetails());
    dto.addAttribute("purpose", domain.getPurpose());
    dto.addAttribute("callSign", domain.getCallSign());
    dto.addAttribute("vhfRadio", domain.getVhfRadio());
    dto.addAttribute("fixedVhfRadio", domain.getFixedVhfRadio());
    dto.addAttribute("fixedVhfRadioValue", domain.getFixedVhfRadioValue());
    dto.addAttribute("portableVhfRadio", domain.getPortableVhfRadio());
    dto.addAttribute(
      "portableVhfRadioValue",
      domain.getPortableVhfRadioValue()
    );
    dto.addAttribute("satelliteTelephone", domain.getSatelliteTelephone());
    dto.addAttribute(
      "satelliteTelephoneValue",
      domain.getSatelliteTelephoneValue()
    );
    dto.addAttribute("mobileTelephone", domain.getMobileTelephone());
    dto.addAttribute("mobileTelephone1", domain.getMobileTelephone1());
    dto.addAttribute("mobileTelephone2", domain.getMobileTelephone2());
    dto.addAttribute("otherCommunication", domain.getOtherCommunication());
    dto.addAttribute(
      "otherCommunicationValue",
      domain.getOtherCommunicationValue()
    );
    dto.addAttribute("maxCapacity", domain.getMaxCapacity());
    dto.addAttribute("vesselName", domain.getVesselName());
    dto.addAttribute("portLetterNumber", domain.getPortLetterNumber());
    dto.addAttribute("homeport", domain.getHomeport());
    dto.addAttribute("areaOfOperation", domain.getAreaOfOperation());
    dto.addAttribute("beaconLocation", domain.getBeaconLocation());
    dto.addAttribute("imoNumber", domain.getImoNumber());
    dto.addAttribute("ssrNumber", domain.getSsrNumber());
    dto.addAttribute("rssNumber", domain.getRssNumber());
    dto.addAttribute("officialNumber", domain.getOfficialNumber());
    dto.addAttribute("rigPlatformLocation", domain.getRigPlatformLocation());
    dto.addAttribute("mainUse", domain.isMainUse());
    dto.addAttribute("aircraftManufacturer", domain.getAircraftManufacturer());
    dto.addAttribute("principalAirport", domain.getPrincipalAirport());
    dto.addAttribute("secondaryAirport", domain.getSecondaryAirport());
    dto.addAttribute("registrationMark", domain.getRegistrationMark());
    dto.addAttribute("hexAddress", domain.getHexAddress());
    dto.addAttribute("cnOrMsnNumber", domain.getCnOrMsnNumber());
    dto.addAttribute("dongle", domain.getDongle());
    dto.addAttribute("beaconPosition", domain.getBeaconPosition());
    dto.addAttribute(
      "workingRemotelyLocation",
      domain.getWorkingRemotelyLocation()
    );
    dto.addAttribute(
      "workingRemotelyPeopleCount",
      domain.getWorkingRemotelyPeopleCount()
    );
    dto.addAttribute("windfarmLocation", domain.getWindfarmLocation());
    dto.addAttribute("windfarmPeopleCount", domain.getWindfarmPeopleCount());
    dto.addAttribute(
      "otherActivityLocation",
      domain.getOtherActivityLocation()
    );
    dto.addAttribute(
      "otherActivityPeopleCount",
      domain.getOtherActivityPeopleCount()
    );
    return dto;
  }

  public BeaconUse fromDTO(BeaconUseDTO beaconUseDto) {
    final var attributes = beaconUseDto.getAttributes();

    final var beaconUse = new BeaconUse();

    beaconUse.setEnvironment(
      parseEnumValueOrNull(attributes.get("environment"), Environment.class)
    );
    beaconUse.setPurpose(
      parseEnumValueOrNull(attributes.get("purpose"), Purpose.class)
    );
    beaconUse.setActivity(
      parseEnumValueOrNull(attributes.get("activity"), Activity.class)
    );

    return beaconUse;
  }
}