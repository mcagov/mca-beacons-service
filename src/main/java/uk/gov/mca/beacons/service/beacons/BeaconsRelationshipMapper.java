package uk.gov.mca.beacons.service.beacons;

import static java.util.Collections.emptyMap;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconPerson;
import uk.gov.mca.beacons.service.model.BeaconUse;
import uk.gov.mca.beacons.service.model.PersonType;

@Service
public class BeaconsRelationshipMapper {

  public Beacon getMappedBeacon(
    Beacon beacons,
    List<BeaconPerson> persons,
    List<BeaconUse> uses
  ) {
    return getMappedBeacons(List.of(beacons), persons, uses).get(0);
  }

  public List<Beacon> getMappedBeacons(
    List<Beacon> beacons,
    List<BeaconPerson> persons,
    List<BeaconUse> uses
  ) {
    final var usesGroupedByBeaconId = getAllUsesGroupedByBeaconId(uses);
    final var personsGroupedByType = getAllPersonsGroupedByType(persons);
    final var ownersGroupedByBeaconId = getAllOwnersGroupedByBeaconId(
      personsGroupedByType
    );
    final var emergencyContactsGroupedByBeaconId = getAllContactsGroupedByBeaconId(
      personsGroupedByType
    );

    return mapBeaconRelationships(
      beacons,
      usesGroupedByBeaconId,
      ownersGroupedByBeaconId,
      emergencyContactsGroupedByBeaconId
    );
  }

  private List<Beacon> mapBeaconRelationships(
    final List<Beacon> allBeacons,
    final Map<UUID, List<BeaconUse>> usesGroupedByBeaconId,
    final Map<UUID, BeaconPerson> ownersGroupedByBeaconId,
    final Map<UUID, List<BeaconPerson>> emergencyContactsGroupedByBeaconId
  ) {
    return allBeacons
      .stream()
      .map(
        beacon -> {
          setUsesOnBeacon(usesGroupedByBeaconId, beacon);
          setOwnerOnBeacon(ownersGroupedByBeaconId, beacon);
          setContactsOnBeacon(emergencyContactsGroupedByBeaconId, beacon);
          return beacon;
        }
      )
      .collect(Collectors.toList());
  }

  private Map<UUID, List<BeaconUse>> getAllUsesGroupedByBeaconId(
    List<BeaconUse> uses
  ) {
    return uses.stream().collect(Collectors.groupingBy(BeaconUse::getBeaconId));
  }

  private Map<UUID, List<BeaconPerson>> getAllContactsGroupedByBeaconId(
    final Map<PersonType, List<BeaconPerson>> personsGroupedByType
  ) {
    final var emergencyContacts = personsGroupedByType.get(
      PersonType.EMERGENCY_CONTACT
    );
    if (emergencyContacts == null) return emptyMap();

    return emergencyContacts
      .stream()
      .collect(Collectors.groupingBy(BeaconPerson::getBeaconId));
  }

  private Map<UUID, BeaconPerson> getAllOwnersGroupedByBeaconId(
    final Map<PersonType, List<BeaconPerson>> personsGroupedByType
  ) {
    final var owners = personsGroupedByType.get(PersonType.OWNER);
    if (owners == null) return emptyMap();

    return owners
      .stream()
      .filter(person -> Objects.nonNull(person.getBeaconId()))
      .collect(
        Collectors.toMap(BeaconPerson::getBeaconId, Function.identity())
      );
  }

  private Map<PersonType, List<BeaconPerson>> getAllPersonsGroupedByType(
    List<BeaconPerson> persons
  ) {
    return persons
      .stream()
      .collect(Collectors.groupingBy(BeaconPerson::getPersonType));
  }

  private void setUsesOnBeacon(
    Map<UUID, List<BeaconUse>> usesGroupedByBeaconId,
    Beacon beacon
  ) {
    final var beaconUses = usesGroupedByBeaconId.get(beacon.getId());
    beacon.setUses(
      Objects.requireNonNullElse(beaconUses, Collections.emptyList())
    );
  }

  private void setOwnerOnBeacon(
    Map<UUID, BeaconPerson> ownersGroupedByBeaconId,
    Beacon beacon
  ) {
    final var beaconOwner = ownersGroupedByBeaconId.get(beacon.getId());
    beacon.setOwner(beaconOwner);
  }

  private void setContactsOnBeacon(
    Map<UUID, List<BeaconPerson>> emergencyContactsGroupedByBeaconId,
    Beacon beacon
  ) {
    final var beaconContacts = emergencyContactsGroupedByBeaconId.get(
      beacon.getId()
    );
    beacon.setEmergencyContacts(
      Objects.requireNonNullElse(beaconContacts, Collections.emptyList())
    );
  }
}
