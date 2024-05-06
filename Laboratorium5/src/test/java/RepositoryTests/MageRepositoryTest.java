package RepositoryTests;

import DTO.Mage;
import Repository.MageRepository;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;



public class MageRepositoryTest {
    private MageRepository repository;


    @BeforeEach
    public void start(){
        Collection<Mage> collection = new ArrayList<>();
        this.repository = new MageRepository(collection);
    }

    @Test
    public void testSaveAlreadyExistingMage(){
        Mage m1 = new Mage("TestMage", 1);
        Mage m2 = new Mage("TestMage", 1);

        repository.save(m1);

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> repository.save(m2));
    }

    @Test
    public void testFindExistingMage(){
        Mage m = new Mage("TestMage", 1);

        repository.save(m);

        Optional<Mage> mageOptional = repository.find("TestMage");
        assertThat(mageOptional).contains(m);
    }

    @Test
    public void testFindNonExistingMage(){
        Optional<Mage> mageOptional = repository.find("TestMage");
        assertThat(mageOptional).isEmpty();
    }

    @Test
    public void testDeleteExistingMage(){
        Mage m = new Mage("TestMage", 1);

        repository.save(m);

        repository.delete("TestMage");

        assertThat(repository.find("TestMage")).isEmpty();
    }

    @Test
    public void testDeleteNonExistingMage(){
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> repository.delete("TestMage"));
    }







}
