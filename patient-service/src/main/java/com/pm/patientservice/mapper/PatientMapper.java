package com.pm.patientservice.mapper;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.model.Patient;

import java.time.LocalDate;

public class PatientMapper {

    public static PatientResponseDTO toResponseDTO(Patient patient) {
        if (patient == null) {
            return null;
        }
        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(patient.getPatientId().toString());
        patientDTO.setName(patient.getPatientName());
        patientDTO.setEmail(patient.getPatientEmail());
        patientDTO.setPhone(patient.getPatientPhone());
        patientDTO.setAddress(patient.getPatientAddress());
        patientDTO.setDob(patient.getDateOfBirth().toString());
        return patientDTO;
    }

    public static Patient toModel(PatientRequestDTO patientRequestDTO) {
        if (patientRequestDTO == null) {
            return null;
        }
        Patient patient = new Patient();
        patient.setPatientName(patientRequestDTO.getName());
        patient.setPatientEmail(patientRequestDTO.getEmail());
        patient.setPatientPhone(patientRequestDTO.getPhone());
        patient.setPatientAddress(patientRequestDTO.getAddress());
        patient.setDateOfBirth(LocalDate.parse(patientRequestDTO.getDob()));
        patient.setDateOfRegistration(LocalDate.now());
        return patient;
    }
}
