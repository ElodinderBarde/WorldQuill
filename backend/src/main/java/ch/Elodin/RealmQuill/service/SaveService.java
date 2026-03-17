package ch.Elodin.RealmQuill.service;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.Optional;

public class SaveService
{


    static <ID, E> void resolveAndSet(
            ID id,
            Function<ID, Optional<E>> finder,
            Consumer<E> setter) {
        if (id != null) {
            finder.apply(id).ifPresent(setter);
        }
    }

}



