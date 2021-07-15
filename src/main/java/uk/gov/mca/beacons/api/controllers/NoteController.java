package uk.gov.mca.beacons.api.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.User;
import uk.gov.mca.beacons.api.dto.NoteDTO;
import uk.gov.mca.beacons.api.dto.WrapperDTO;
import uk.gov.mca.beacons.api.mappers.NoteMapper;
import uk.gov.mca.beacons.api.services.GetUserService;
import uk.gov.mca.beacons.api.services.NoteService;

@RestController
@RequestMapping("/note")
@Tag(name = "Note Controller")
public class NoteController {

  private final NoteMapper noteMapper;
  private final NoteService noteService;
  private final GetUserService getUserService;

  @Autowired
  public NoteController(
    NoteMapper noteMapper,
    NoteService noteService,
    GetUserService getUserService
  ) {
    this.noteMapper = noteMapper;
    this.noteService = noteService;
    this.getUserService = getUserService;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public WrapperDTO<NoteDTO> createNote(
    @RequestBody @Valid WrapperDTO<NoteDTO> dto
  ) {
    final Note note = noteMapper.fromDTO(dto.getData());
    if (userDoesNotExist(note)) {
      // TODO: make the id passed in (B2C auth Id) not just null once we're getting that!
      final User user = getUserService.getUser(null);

      note.setUserAuthId(UUID.fromString(user.getAuthId()));
      note.setFullName(user.getFullName());
      note.setEmail(user.getEmail());
    }
    return noteMapper.toWrapperDTO(noteService.create(note));
  }

  private boolean userDoesNotExist(Note note) {
    return (
      note.getUserAuthId() == null &&
      note.getFullName() == null &&
      note.getEmail() == null
    );
  }
}