package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.MainCriteria;
import tn.esprit.spring.missionentreprise.Repositories.MainCriteriaRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MainCriteriaService implements IServiceGenerique<MainCriteria> {

    private final MainCriteriaRepository mainCriteriaRepository;

    @Override
    public MainCriteria add(MainCriteria mainCriteria) {
        validateMainCriteria(mainCriteria);
        return (MainCriteria) mainCriteriaRepository.save(mainCriteria);
    }

    @Override
    public List<MainCriteria> addAll(List<MainCriteria> mainCriteriaList) {
        mainCriteriaList.forEach(this::validateMainCriteria);
        return mainCriteriaRepository.saveAll(mainCriteriaList);
    }

    @Override
    public List<MainCriteria> getAll() {
        return mainCriteriaRepository.findAll();
    }

    @Override
    public MainCriteria edit(MainCriteria mainCriteria) {
        validateMainCriteria(mainCriteria);
        return (MainCriteria) mainCriteriaRepository.save(mainCriteria);
    }

    @Override
    public List<MainCriteria> editAll(List<MainCriteria> mainCriteriaList) {
        mainCriteriaList.forEach(this::validateMainCriteria);
        return mainCriteriaRepository.saveAll(mainCriteriaList);
    }

    @Override
    public void delete(MainCriteria mainCriteria) {
        mainCriteriaRepository.delete(mainCriteria);
    }

    @Override
    public void deleteAll() {
        mainCriteriaRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        mainCriteriaRepository.deleteById(id);
    }

    @Override
    public MainCriteria getById(Long id) {
        return (MainCriteria) mainCriteriaRepository.findById(id)
                .orElse(MainCriteria.builder()
                        .idMainCritere(0L)
                        .descMainCritere("Default Main Criteria")
                        .MaxPoints(0f)
                        .build());
    }

    @Override
    public boolean existsById(Long id) {
        return mainCriteriaRepository.existsById(id);
    }

    @Override
    public Long count() {
        return mainCriteriaRepository.count();
    }

    // Additional business methods
    public List<MainCriteria> findByDescriptionContaining(String description) {
        return mainCriteriaRepository.findByDescMainCritereContainingIgnoreCase(description);
    }

    private void validateMainCriteria(MainCriteria mainCriteria) {
        if (mainCriteria.getDescMainCritere() == null || mainCriteria.getDescMainCritere().isEmpty()) {
            throw new IllegalArgumentException("Main criteria description cannot be empty");
        }
        if (mainCriteria.getMaxPoints() == null || mainCriteria.getMaxPoints() < 0) {
            throw new IllegalArgumentException("Max points must be positive");
        }
    }
}