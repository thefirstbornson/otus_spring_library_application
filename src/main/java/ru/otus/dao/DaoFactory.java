package ru.otus.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

@Service
public class DaoFactory {
    private final AuthorDao authorDao;
    private final GenreDao genreDao;
    private final BookDao bookDao;

    @Autowired
    public DaoFactory(AuthorDao authorDao, GenreDao genreDao, BookDao bookDao) {
        this.authorDao = authorDao;
        this.genreDao = genreDao;
        this.bookDao = bookDao;
    }


    public AuthorDao getAuthorDao(){return authorDao;}

    public GenreDao getGenreDao(){return genreDao;}

    public BookDao getBookDao(){return bookDao;}
//
//    public GenericDao getDaoByEntityName (String entityName) throws NoSuchElementException {
//        return daoMap.entrySet()
//                .stream()
//                .filter(e -> e.getKey().contains(entityName.toLowerCase()))
//                .findFirst()
//                .get()
//                .getValue();
//    }

//    private static final Map<String, GenericDao>
//           DAOMAP;
//
//    static {
//        final Map<String, GenericDao>
//                players = new HashMap<>();
//        players.put("author", authorDao);
//        players.put("FOOTBALL", FootballPlayer::new);
//        players.put("SNOOKER", SnookerPlayer::new);
//
//        DAOMAP = Collections.unmodifiableMap(players);
//    }
//
//
//
//    public Player supplyPlayer(String playerType) {
//        Supplier<Player> player = PLAYER_SUPPLIER.get(playerType);
//
//        if (player == null) {
//            throw new IllegalArgumentException("Invalid player type: "
//                    + playerType);
//        }
//        return player.get();
//    }


}
