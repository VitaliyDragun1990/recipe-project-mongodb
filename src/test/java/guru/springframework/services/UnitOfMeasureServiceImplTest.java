package guru.springframework.services;

import guru.springframework.commands.UnitOfMeasureCommand;
import guru.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureToUnitOfMeasureCommand converter = new UnitOfMeasureToUnitOfMeasureCommand();
    private UnitOfMeasureService service;

    @Mock
    private UnitOfMeasureRepository repository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(repository, converter);
    }

    @Test
    public void testListAllUoms() {
        // given
        Set<UnitOfMeasure> units = new HashSet<>();

        UnitOfMeasure unit1 = new UnitOfMeasure();
        unit1.setId(1L);
        UnitOfMeasure unit2 = new UnitOfMeasure();
        unit2.setId(2L);

        units.add(unit1);
        units.add(unit2);

        when(repository.findAll()).thenReturn(units);

        // when
        Set<UnitOfMeasureCommand> commands = service.listAllUoms();

        // then
        assertEquals(2, commands.size());
        verify(repository, times(1)).findAll();
    }
}