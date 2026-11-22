package onix322.initializer;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import jakarta.inject.Inject;
import com.onix322.persistence.repository.BaseRepository;

import java.lang.reflect.Constructor;
import java.util.UUID;

public class TypeFactory {

    private final Injector injector;

    @Inject
    public TypeFactory(Injector injector) {
        this.injector = injector;
    }

    public <T, R extends BaseRepository<T, UUID>> R create(Class<T> entityClass, TypeLiteral<R> repositoryClass) {
        try {
            @SuppressWarnings("unchecked")
            Class<R> classRawType = (Class<R>) repositoryClass.getRawType();

            Constructor<R> constructor = classRawType.getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            R repository = constructor.newInstance(entityClass);

            injector.injectMembers(repository);
            return repository;
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
