package kz.projects.atmSystem.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePinRequest {
  private String oldPin;
  private String newPin;
}
