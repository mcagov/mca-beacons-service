package uk.gov.mca.beacons.api.mappers;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.api.db.Person;
import uk.gov.mca.beacons.api.dto.BeaconPersonDTO;

@Service
public class BeaconPersonMapper {

  public BeaconPersonDTO toDTO(Person domain) {
    final var dto = new BeaconPersonDTO();
    dto.setId(domain.getId());

    final Map<String, Object> attributes = new HashMap<>();
    attributes.put("fullName", domain.getFullName());
    attributes.put("email", domain.getEmail());
    attributes.put("telephoneNumber", domain.getTelephoneNumber());
    attributes.put(
      "alternativeTelephoneNumber",
      domain.getAlternativeTelephoneNumber()
    );
    attributes.put("addressLine1", domain.getAddressLine1());
    attributes.put("addressLine2", domain.getAddressLine2());
    attributes.put("addressLine3", domain.getAddressLine3());
    attributes.put("addressLine4", domain.getAddressLine4());
    attributes.put("townOrCity", domain.getTownOrCity());
    attributes.put("county", domain.getCounty());
    attributes.put("postcode", domain.getPostcode());
    dto.setAttributes(attributes);

    return dto;
  }
}