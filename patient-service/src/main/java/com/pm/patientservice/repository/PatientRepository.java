package com.pm.patientservice.repository;

import com.pm.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    boolean existsByPatientEmail(String email);
    Optional<Patient> findAllByPatientId(UUID patientId);

    Optional<Patient> findPatientByPatientId(UUID patientId);
}
