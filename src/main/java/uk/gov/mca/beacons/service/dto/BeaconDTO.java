package uk.gov.mca.beacons.service.dto;

import java.util.HashMap;
import java.util.Map;

public class BeaconDTO extends DomainDTO<Map<String, Object>> {

  public BeaconDTO() {
    this.attributes = new HashMap<>();
  }

  private String type = "beacon";

  public String getType() {
    return type;
  }
}
