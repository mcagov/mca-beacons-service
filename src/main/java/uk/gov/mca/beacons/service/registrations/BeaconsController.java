package uk.gov.mca.beacons.service.registrations;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.service.model.Beacon;
import uk.gov.mca.beacons.service.model.BeaconsSearchResult;

@RestController
@RequestMapping("/beacons")
@Tag(name = "Beacons Controller")
public class BeaconsController {

  private final GetAllBeaconsService getAllBeaconsService;

  @Autowired
  public BeaconsController(GetAllBeaconsService getAllBeaconsService) {
    this.getAllBeaconsService = getAllBeaconsService;
  }

  @GetMapping
  public BeaconsSearchResult findAll() {
    final var results = new BeaconsSearchResult();
    results.setBeacons(getAllBeaconsService.findAll());
    return results;
  }

  @GetMapping(value = "/{uuid}")
  public BeaconsSearchResult findByUuid(
    @PathVariable("uuid") String uuidString
  ) {
    final var result = new BeaconsSearchResult();
    UUID uuid = UUID.fromString(uuidString);
    Beacon beacon = getAllBeaconsService.find(uuid);
    List<Beacon> beaconList = new ArrayList<Beacon>();
    beaconList.add(beacon);
    result.setBeacons(beaconList);
    return result;
  }
}