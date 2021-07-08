package uk.gov.mca.beacons.api.gateways;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.gov.mca.beacons.api.domain.Note;
import uk.gov.mca.beacons.api.domain.NoteType;
import uk.gov.mca.beacons.api.jpa.NoteJpaRepository;
import uk.gov.mca.beacons.api.jpa.entities.NoteEntity;

@ExtendWith(MockitoExtension.class)
class NoteGatewayImplTest {

  @InjectMocks
  private NoteGatewayImpl noteGateway;

  @Mock
  private NoteJpaRepository noteRepository;

  @Captor
  private ArgumentCaptor<NoteEntity> noteCaptor;

  @Test
  void shouldCreateANoteEntityFromANote() {
    final UUID id = UUID.randomUUID();
    final UUID beaconId = UUID.randomUUID();
    final String text = "This beacon belongs to a cat.";
    final NoteType type = NoteType.GENERAL;
    final LocalDateTime createdDate = LocalDateTime.now();
    final UUID personId = UUID.randomUUID();
    final String fullName = "Alfred the cat";
    final String email = "alfred@cute.cat.com";

    final Note note = Note
      .builder()
      .beaconId(beaconId)
      .text(text)
      .type(type)
      .createdDate(createdDate)
      .personId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    final NoteEntity createdEntity = NoteEntity
      .builder()
      .id(id)
      .beaconId(beaconId)
      .text(text)
      .type(type)
      .createdDate(createdDate)
      .personId(personId)
      .fullName(fullName)
      .email(email)
      .build();

    when(noteRepository.save(any(NoteEntity.class))).thenReturn(createdEntity);

    final Note createdNote = noteGateway.create(note);

    assertThat(createdNote.getId(), is(id));
    assertThat(createdNote.getBeaconId(), is(beaconId));
    assertThat(createdNote.getText(), is(text));
    assertThat(createdNote.getType(), is(type));
    assertThat(createdNote.getCreatedDate(), is(createdDate));
    assertThat(createdNote.getPersonId(), is(personId));
    assertThat(createdNote.getFullName(), is(fullName));
    assertThat(createdNote.getEmail(), is(email));
  }

  @Test
  void shouldSetCreatedDateIfNotProvided() {
    final Note note = new Note();
    final NoteEntity createdEntity = new NoteEntity();

    when(noteRepository.save(noteCaptor.capture())).thenReturn(createdEntity);

    noteGateway.create(note);

    final NoteEntity entity = noteCaptor.getValue();
    assertThat(
      entity.getCreatedDate().getDayOfYear(),
      is(equalTo(LocalDateTime.now().getDayOfYear()))
    );
  }
}
