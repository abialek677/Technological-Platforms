package ControllerTests;

import Controller.MageController;
import DTO.Mage;
import Repository.MageRepository;

import org.junit.jupiter.api.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MageControllerTest {

    private MageController controller;

    private MageRepository mockedRepository;

    @BeforeEach
    public void start(){
        mockedRepository = mock(MageRepository.class);
        controller = new MageController(mockedRepository);
    }

    @Test
    public void testDeleteExistingMage(){
        assertThat(controller.delete("TestMage")).isEqualTo("done");
    }

    @Test
    public void testDeleteNonExistingMage(){
        doThrow(new IllegalArgumentException()).when(mockedRepository).delete("TestMage");
    }

    @Test
    public void testFindExistingMage(){
        when(mockedRepository.find("TestMage")).thenReturn(Optional.of(new Mage("TestMage", 1)));
        assertThat(controller.find("TestMage")).isEqualTo(Optional.of(new Mage("TestMage", 1)).toString());
    }

    @Test
    public void testFindNonExistingMage(){
        when(mockedRepository.find("TestMage")).thenReturn(Optional.empty());
        assertThat(controller.find("TestMage")).isEqualTo("not found");
    }

    @Test
    public void testSaveNewMage(){
        assertThat(controller.save("TestMage", 1)).isEqualTo("done");
    }

    @Test
    public void testSaveAlreadyExistingMage() {
        doThrow(new IllegalArgumentException()).when(mockedRepository).save(new Mage("TestMage", 1));
        assertThat(controller.save("TestMage", 1)).isEqualTo("bad request");
    }

}
