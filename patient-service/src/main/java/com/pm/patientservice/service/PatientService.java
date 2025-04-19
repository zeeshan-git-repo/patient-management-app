package com.pm.patientservice.service;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.exception.EmailAlreadyExists;
import com.pm.patientservice.exception.PatientNotFoundException;
import com.pm.patientservice.mapper.PatientMapper;
import com.pm.patientservice.model.Patient;
import com.pm.patientservice.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private PatientRepository patientRepository;
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }
    public List<PatientResponseDTO> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        List<PatientResponseDTO> patientResponseDTOs = patients.stream()
                .map(PatientMapper::toResponseDTO)
                .collect(Collectors.toList());
        return patientResponseDTOs;
    }

    public PatientResponseDTO createPatient(PatientRequestDTO patientRequestDTO) {
        if(patientRepository.existsByPatientEmail(patientRequestDTO.getEmail())) {
            throw new EmailAlreadyExists("Patient with this email already exists: " + patientRequestDTO.getEmail());
        }
        Patient savedPatient = patientRepository.save(PatientMapper.toModel(patientRequestDTO));
        return PatientMapper.toResponseDTO(savedPatient);
    }


    public PatientResponseDTO updatePatient(UUID patientId, PatientRequestDTO patientRequestDTO) {
         Patient patient = patientRepository.findAllByPatientId(patientId)
                 .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));
        patient.setPatientName(patientRequestDTO.getName());
        patient.setPatientAddress(patientRequestDTO.getAddress());
        patient.setPatientEmail(patientRequestDTO.getEmail());
        patient.setPatientPhone(patientRequestDTO.getPhone());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDob()));
        Patient updatedPatient = patientRepository.save(patient);
        return PatientMapper.toResponseDTO(updatedPatient);
    }

     public void deletePatient(UUID patientId) {
         Patient patient = patientRepository.findPatientByPatientId(patientId)
                 .orElseThrow(() -> new PatientNotFoundException("Patient not found with id: " + patientId));
         patientRepository.delete(patient);
     }
}
