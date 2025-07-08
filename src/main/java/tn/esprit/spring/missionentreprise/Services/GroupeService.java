//Espace collaboratif
package tn.esprit.spring.missionentreprise.Services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.spring.missionentreprise.Entities.Groupe;
import tn.esprit.spring.missionentreprise.Entities.Visibilite;
import tn.esprit.spring.missionentreprise.Repositories.GroupeRepository;
import tn.esprit.spring.missionentreprise.Repositories.EtudiantRepository;
import tn.esprit.spring.missionentreprise.Entities.Etudiant;
import java.util.Date;
import java.util.List;
import tn.esprit.spring.missionentreprise.Repositories.ProjetRepository;
import tn.esprit.spring.missionentreprise.Repositories.RepoRepository;
import tn.esprit.spring.missionentreprise.Repositories.NoteTGrpRepository;
import tn.esprit.spring.missionentreprise.Entities.Projet;
import tn.esprit.spring.missionentreprise.Entities.Repo;
import tn.esprit.spring.missionentreprise.Entities.NoteTGrp;

@Service
@AllArgsConstructor
public class GroupeService implements IServiceGenerique<Groupe> {

    private GroupeRepository groupeRepository;
    private EtudiantRepository etudiantRepository;
    private ProjetRepository projetRepository;
    private RepoRepository repoRepository;
    private NoteTGrpRepository noteTGrpRepository;

    @Override
    public Groupe add(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> addAll(List<Groupe> groupes) {
        return groupeRepository.saveAll(groupes);
    }

    @Override
    public List<Groupe> getAll() {
        return groupeRepository.findAll();
    }

    @Override
    public Groupe edit(Groupe groupe) {
        return groupeRepository.save(groupe);
    }

    @Override
    public List<Groupe> editAll(List<Groupe> groupes) {
        return groupeRepository.saveAll(groupes);
    }

    @Override
    public void delete(Groupe groupe) {
        groupeRepository.delete(groupe);
    }

    @Override
    public void deleteAll() {
        groupeRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        groupeRepository.deleteById(id);
    }

    @Override
    public Groupe getById(Long id) {
        return groupeRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        return groupeRepository.existsById(id);
    }

    @Override
    public Long count() {
        return groupeRepository.count();
    }

    public Groupe createGroupe(String nomGroupe, Date dateCreation, Visibilite visibilite, List<Long> etudiantIds, Long projetId, Long repoId, List<Long> noteTGrpIds) {
        Groupe groupe = Groupe.builder()
                .nomGroupe(nomGroupe)
                .DateCreation(dateCreation)
                .visibilite(visibilite)
                .build();
        if (etudiantIds != null && !etudiantIds.isEmpty()) {
            List<Etudiant> etudiants = etudiantRepository.findAllById(etudiantIds);
            groupe.setEtudiants(etudiants);
            for (Etudiant etudiant : etudiants) {
                etudiant.setGroupe(groupe);
            }
        }
        if (projetId != null) {
            Projet projet = projetRepository.findById(projetId).orElse(null);
            groupe.setProjet(projet);
        }
        if (repoId != null) {
            Repo repo = repoRepository.findById(repoId).orElse(null);
            groupe.setRepo(repo);
        }
        if (noteTGrpIds != null && !noteTGrpIds.isEmpty()) {
            List<NoteTGrp> notes = noteTGrpRepository.findAllById(noteTGrpIds);
            groupe.setNoteTGrps(notes);
            for (NoteTGrp note : notes) {
                note.setGroupe(groupe);
            }
        }
        return groupeRepository.save(groupe);
    }
}
