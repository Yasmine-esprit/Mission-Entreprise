package tn.esprit.spring.missionentreprise.Services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.spring.missionentreprise.Entities.*;
import tn.esprit.spring.missionentreprise.Repositories.PieceJointeRepository;
import tn.esprit.spring.missionentreprise.Repositories.TacheRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
@Transactional
public class TacheService implements IServiceGenerique<Tache> {

    private final TacheRepository tacheRepository;
    private final PieceJointeRepository pieceJointeRepository;

    @Override
    public Tache add(Tache tache) {
        // Set default values if not provided
        if (tache.getStatut() == null) { tache.setStatut(Statut.ToDo);}
        if (tache.getPriorite() == null) {tache.setPriorite(Priorite.MEDIUM);}
        if (tache.getPendingChanges() == null) {tache.setPendingChanges(true);}

        // Initialization des collections si null
        if (tache.getLabels() == null) tache.setLabels(new ArrayList<>());
        if (tache.getMembers() == null) tache.setMembers(new ArrayList<>());
        if (tache.getPiecesJointes() == null) tache.setPiecesJointes(new ArrayList<>());
        if (tache.getSousTaches() == null) tache.setSousTaches(new ArrayList<>());
        if (tache.getChecklist() == null) tache.setChecklist(new ArrayList<>());

        // Set timestamps
        tache.setLastUpdated(new Date());

        return tacheRepository.save(tache);
    }

    @Override
    public List<Tache> addAll(List<Tache> taches) {
        taches.forEach(this::add);
        return tacheRepository.saveAll(taches);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tache> getAll() {
        return tacheRepository.findAll();
    }

    @Override
    public Tache edit(Tache tache) {
        // Ensure the task exists
        if (!tacheRepository.existsById(tache.getIdTache())) {
            throw new RuntimeException("Tâche introuvable avec l'ID: " + tache.getIdTache());
        }

        // Update timestamp
        tache.setLastUpdated(new Date());
        tache.setPendingChanges(true);

        return tacheRepository.save(tache);
    }

    public Tache partialUpdate(Long id, Map<String, Object> updates) {
        Tache existingTache = tacheRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tâche introuvable avec l'ID: " + id));

        // Handle each field update
        updates.forEach((field, value) -> {
            FieldUpdateHandler.handleFieldUpdate(existingTache, field, value);
        });

        // Update timestamps
        existingTache.setLastUpdated(new Date());
        existingTache.setPendingChanges(true);

        return tacheRepository.save(existingTache);
    }

    @Transactional // IMPORTANT: Ajoutez cette annotation
    public Tache updateTacheDates(Long tacheId, LocalDate dateDebut, LocalDate dateFin) {
        System.out.println("=== SERVICE - DÉBUT UPDATE ===");
        System.out.println("ID: " + tacheId);
        System.out.println("DateDebut: " + dateDebut);
        System.out.println("DateFin: " + dateFin);

        // Récupérer la tâche existante
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID: " + tacheId));

        System.out.println("Tâche avant modification: " + tache);

        // Mettre à jour les dates
        tache.setDateDebut(dateDebut);
        tache.setDateFin(dateFin);

        System.out.println("Tâche après modification (avant save): " + tache);

        // IMPORTANT: Sauvegarder explicitement
        Tache savedTache = tacheRepository.save(tache);

        System.out.println("Tâche après save: " + savedTache);
        System.out.println("=== SERVICE - FIN UPDATE ===");

        return savedTache;
    }


    @Component
    public class FieldUpdateHandler {

        public static void handleFieldUpdate(Tache tache, String field, Object value) {
            try {
                switch (field) {
                    case "titreTache":
                        tache.setTitreTache((String) value);
                        break;
                    case "descriptionTache":
                        tache.setDescriptionTache((String) value);
                        break;
                    case "dateDebut":
                        tache.setDateDebut(LocalDate.parse(value.toString()));
                        break;
                    case "dateFin":
                        tache.setDateFin(LocalDate.parse(value.toString()));
                        break;
                    case "statut":
                        tache.setStatut(Statut.valueOf(value.toString()));
                        break;
                    case "priorite":
                        tache.setPriorite(Priorite.valueOf(value.toString()));
                        break;
                    case "assigneA":
                        tache.setAssigneA((String) value);
                        break;
                    case "coverColor":
                        tache.setCoverColor((String) value);
                        break;
                    case "projet":
                        tache.setProjet((Projet) value); // Requires proper deserialization
                        break;
                    case "board":
                        tache.setBoard((Boards) value); // Requires proper deserialization
                        break;
                    case "phase":
                        tache.setPhase((Phase) value); // Requires proper deserialization
                        break;
                    default:
                        throw new IllegalArgumentException("Champ non valide pour mise à jour: " + field);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("Erreur lors de la mise à jour du champ " + field + ": " + e.getMessage());
            }
        }
    }

    @Override
    public List<Tache> editAll(List<Tache> taches) {
        return tacheRepository.saveAll(taches);
    }

    @Override
    public void delete(Tache tache) {
        tacheRepository.delete(tache);
    }

    @Override
    public void deleteAll() {
        tacheRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        tacheRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Tache getById(Long id) {
        return tacheRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return tacheRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long count() {
        return tacheRepository.count();
    }

    @Transactional
    public Tache addPieceJointe(Long tacheId, PieceJointe pieceJointe) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID: " + tacheId));
        if (pieceJointe.getNom() == null || pieceJointe.getNom().isEmpty()) {
            pieceJointe.setNom("Fichier sans nom");
        }
        if (tache.getPiecesJointes() == null) {
            tache.setPiecesJointes(new ArrayList<>());
        }
        pieceJointe.setTache(tache);
        tache.getPiecesJointes().add(pieceJointe);

        return tacheRepository.save(tache);
    }

    public void removePieceJointe(Long tacheId, Long pieceJointeId) {
        PieceJointe pieceJointe = pieceJointeRepository.findById(pieceJointeId)
                .orElseThrow(() -> new RuntimeException("Pièce jointe introuvable avec l'ID: " + pieceJointeId));

        if (!pieceJointe.getTache().getIdTache().equals(tacheId)) {
            throw new RuntimeException("La pièce jointe n'appartient pas à cette tâche");
        }

        Tache tache = pieceJointe.getTache();
        tache.getPiecesJointes().remove(pieceJointe);
        tache.setLastUpdated(new Date());

        pieceJointeRepository.delete(pieceJointe);
        tacheRepository.save(tache);
    }

    public Tache updateStatut(Long id, Statut statut) {
        return partialUpdate(id, Map.of("statut", statut.name()));
    }

    public Tache updatePriorite(Long id, Priorite priorite) {
        return partialUpdate(id, Map.of("priorite", priorite.name()));

    }

    public Tache assignToProject(Long tacheId, Projet projet) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID: " + tacheId));
        tache.setProjet(projet);
        tache.setLastUpdated(new Date());
        return tacheRepository.save(tache);
    }
    public Tache createTache(Tache tache) {
        return tacheRepository.save(tache);
    }


    public Tache createTacheWithId(Tache tache) {
        return tacheRepository.save(tache);
    }

    public boolean tacheExists(Long idTache) {
        return tacheRepository.existsById(idTache);
    }

    public Tache assignToBoard(Long tacheId, Boards board) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID: " + tacheId));
        tache.setBoard(board);
        tache.setLastUpdated(new Date());
        return tacheRepository.save(tache);
    }

    public Tache assignToPhase(Long tacheId, Phase phase) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID: " + tacheId));
        tache.setPhase(phase);
        tache.setLastUpdated(new Date());
        return tacheRepository.save(tache);
    }

    public Tache addLabel(Long tacheId, String label) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID: " + tacheId));
        tache.getLabels().add(label);
        tache.setLastUpdated(new Date());
        return tacheRepository.save(tache);
    }

    public Tache addMember(Long tacheId, String memberEmail) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID: " + tacheId));
        tache.getMembers().add(memberEmail);
        tache.setLastUpdated(new Date());
        return tacheRepository.save(tache);
    }

    @Transactional(readOnly = true)
    public List<Tache> getTachesByStatut(Statut statut) {
        return tacheRepository.findByStatut(statut);
    }

    @Transactional(readOnly = true)
    public List<Tache> getTachesByPriorite(Priorite priorite) {
        return tacheRepository.findByPriorite(priorite);
    }

    @Transactional(readOnly = true)
    public List<Tache> getTachesAssignees(String assigneA) {
        return tacheRepository.findByAssigneA(assigneA);
    }

    @Transactional(readOnly = true)
    public List<PieceJointe> getPiecesJointesByTacheId(Long tacheId) {
        return pieceJointeRepository.findByTache_IdTache(tacheId);
    }

    public void markAsSynced(Long tacheId) {
        Tache tache = tacheRepository.findById(tacheId)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable avec l'ID: " + tacheId));
        tache.markAsSynced();
        tacheRepository.save(tache);
    }
}