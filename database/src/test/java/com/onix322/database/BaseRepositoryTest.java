package com.onix322.database;

import com.onix322.database.repository.BaseRepository;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BaseRepositoryTest {

    @Mock
    protected ORM orm;
    @Mock
    protected EntityManager em;

    BaseRepository<TestModel, UUID> baseRepository;

    @BeforeEach
    public void setUp() {
        baseRepository = new TestRepository(TestModel.class).withOrm(orm);
        lenient().when(orm.executeWithResult(any())).thenAnswer(inv -> {
            Function<EntityManager, TestModel> function = inv.getArgument(0);
            return function.apply(em);
        });
    }

    @Test
    public void testSaveMethod() {

        // GIVEN
        TestModel given = new TestModel();
        TestModel expected = new TestModel();
        expected.setId(UUID.randomUUID());

        // WHEN
        when(em.merge(given)).thenReturn(expected);

        // THEN
        assertEquals(expected, baseRepository.save(given));
        verify(em).merge(given);
        verify(orm).executeWithResult(any());
    }

    @Test
    public void testSaveMethodWithNullEntity() {

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> baseRepository.save(null));

        verifyNoInteractions(em);
        verifyNoInteractions(orm);
    }

    @Test
    public void testFindMethod() {

        // GIVEN
        UUID givenId = UUID.randomUUID();
        TestModel expected = new TestModel();
        expected.setId(givenId);

        // WHEN
        when(em.find(TestModel.class, givenId)).thenReturn(expected);

        // THEN
        assertEquals(Optional.of(expected), baseRepository.find(givenId));
        verify(em).find(TestModel.class, givenId);
        verify(orm).executeWithResult(any());
    }

    @Test
    public void testFindMethodWhenEntityDoesNotExist() {

        // GIVEN
        UUID givenId = UUID.randomUUID();

        // WHEN
        when(em.find(TestModel.class, givenId)).thenReturn(null);

        // THEN
        assertEquals(Optional.empty(), baseRepository.find(givenId));
        verify(em).find(TestModel.class, givenId);
        verify(orm).executeWithResult(any());
    }

    @Test
    public void testFindMethodWithNullId() {

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> baseRepository.find(null));

        verifyNoInteractions(em);
        verifyNoInteractions(orm);
    }

    @Test
    public void testDeleteMethod() {

        // GIVEN
        UUID givenId = UUID.randomUUID();
        TestModel givenModel = new TestModel();
        givenModel.setId(givenId);

        // WHEN
        when(em.find(TestModel.class, givenId)).thenReturn(givenModel);

        // THEN
        assertTrue(baseRepository.delete(givenId));
        verify(em).find(TestModel.class, givenId);
        verify(em).remove(givenModel);
        verify(orm).executeWithResult(any());
    }

    @Test
    public void testDeleteMethodWhenEntityDoesNotExist() {

        // GIVEN
        UUID givenId = UUID.randomUUID();

        // WHEN
        when(em.find(TestModel.class, givenId)).thenReturn(null);

        // THEN
        assertFalse(baseRepository.delete(givenId));
        verify(em).find(TestModel.class, givenId);
        verify(em, never()).remove(any());
        verify(orm).executeWithResult(any());
    }

    @Test
    public void testDeleteMethodWithNullId() {

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> baseRepository.delete(null));

        verifyNoInteractions(em);
        verifyNoInteractions(orm);
    }

    @Test
    public void testExistsMethod() {

        // GIVEN
        UUID givenId = UUID.randomUUID();

        // WHEN
        when(em.find(TestModel.class, givenId)).thenReturn(new TestModel());

        // THEN
        assertTrue(baseRepository.exists(givenId));
        verify(em).find(TestModel.class, givenId);
        verify(orm).executeWithResult(any());
    }

    @Test
    public void testExistsMethodWithNullId() {

        // WHEN / THEN
        assertThrows(IllegalArgumentException.class, () -> baseRepository.exists(null));

        verifyNoInteractions(em);
        verifyNoInteractions(orm);
    }

    @Test
    public void testFindAll() {

        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        @SuppressWarnings("unchecked")
        CriteriaQuery<TestModel> query = (CriteriaQuery<TestModel>) mock(CriteriaQuery.class);
        @SuppressWarnings("unchecked")
        Root<TestModel> root = (Root<TestModel>) mock(Root.class);
        @SuppressWarnings("unchecked")
        TypedQuery<TestModel> typedQuery = (TypedQuery<TestModel>) mock(TypedQuery.class);
        // given
        List<TestModel> expected = new ArrayList<>();
        expected.add(new TestModel());
        // when
        when(em.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createQuery(TestModel.class)).thenReturn(query);

        when(query.from(TestModel.class)).thenReturn(root);
        doAnswer(ignored -> query).when(query).select(root);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getResultList()).thenReturn(expected);
        //then
        assertEquals(expected, baseRepository.findAll());
    }

    @Test
    public void testCountMethod() {

        // given
        Long expected = 1L;

        CriteriaBuilder builder = mock(CriteriaBuilder.class);
        @SuppressWarnings("unchecked")
        CriteriaQuery<Long> query = (CriteriaQuery<Long>) mock(CriteriaQuery.class);
        @SuppressWarnings("unchecked")
        Expression<Long> count = (Expression<Long>) mock(Expression.class);
        @SuppressWarnings("unchecked")
        Root<TestModel> root = (Root<TestModel>) mock(Root.class);
        @SuppressWarnings("unchecked")
        TypedQuery<Long> typedQuery = (TypedQuery<Long>) mock(TypedQuery.class);

        // when
        when(em.getCriteriaBuilder()).thenReturn(builder);
        when(builder.createQuery(Long.class)).thenReturn(query);

        when(query.from(TestModel.class)).thenReturn(root);
        when(builder.count(root)).thenReturn(count);
        when(query.select(builder.count(root))).thenReturn(query);
        when(em.createQuery(query)).thenReturn(typedQuery);
        when(typedQuery.getSingleResult()).thenReturn(expected);
        //then
        assertEquals(expected, baseRepository.count());
        verify(typedQuery).getSingleResult();
        verify(em).createQuery(query);
    }
}

@Entity
@Table
@Data
class TestModel {
    @Id
    private UUID id;
}

class TestRepository extends BaseRepository<TestModel, UUID> {
    protected TestRepository(Class<TestModel> type) {
        super(type);
    }

    protected TestRepository withOrm(ORM orm) {
        this.orm = orm;
        return this;
    }
}

