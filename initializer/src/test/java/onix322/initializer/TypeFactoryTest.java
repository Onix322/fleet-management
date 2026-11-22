package onix322.initializer;

import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.onix322.persistence.repository.BaseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TypeFactoryTest {

    @Mock
    Injector injector;
    TypeFactory factory;

    @BeforeEach
    public void setUp() {
        factory = new TypeFactory(injector);
    }

    @Test
    public void testCreateSuccess() {

        TestRepositoryInFactory result = factory.create(
                TestEntity.class,
                new TypeLiteral<TestRepositoryInFactory>() {}
        );

        assertEquals(TestRepositoryInFactory.class, result.getClass());
        verify(injector).injectMembers(result);
    }

    @Test
    public void testCreateIllegalArgumentException() {

        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(
                    TestEntity.class,
                    null
            );
        });

        assertThrows(IllegalArgumentException.class, () -> {
            factory.create(
                    null,
                    new TypeLiteral<TestRepositoryInFactory>() {}
            );
        });

        verify(injector, never()).injectMembers(any());
    }


    @Test
    public void testCreateReflectiveOperationException() {

        doThrow(RuntimeException.class).when(injector).injectMembers(any());

        assertThrows(RuntimeException.class, () -> {
            factory.create(
                    TestEntity.class,
                    new TypeLiteral<TestRepositoryInFactory>() {}
            );
        });

        verify(injector).injectMembers(any());
    }
}

class TestEntity {}

class TestRepositoryInFactory extends BaseRepository<TestEntity, UUID> {
    protected TestRepositoryInFactory(Class<TestEntity> type) {
        super(type);
    }
}
