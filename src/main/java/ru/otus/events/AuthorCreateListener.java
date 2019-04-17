package ru.otus.events;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Component;
import ru.otus.domain.Author;
import ru.otus.instance_service.NextSequenceService;

@Component
@RequiredArgsConstructor
public class AuthorCreateListener extends AbstractMongoEventListener<Author> {

    private final NextSequenceService nextSequenceService;

//    @Override
//    public void onBeforeConvert(BeforeConvertEvent<Author> event) {
//        if (event.getSource().getId()!=null) {
//            event.getSource().setId(nextSequenceService.getNextSequence(Author.SEQUENCE_NAME));
//        }
//    }
}
