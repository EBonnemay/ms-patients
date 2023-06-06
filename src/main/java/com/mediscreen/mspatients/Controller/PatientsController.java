package com.mediscreen.mspatients.Controller;

import com.mediscreen.mspatients.model.Patient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.mspatients.service.PatientsService;

import java.util.ArrayList;

@RestController
public class PatientsController {
    @RestController
    public class PersonController {
        static final Logger logger = LogManager.getLogger();
        private PatientsService patientsService;


        public PersonController(PatientsService patientsService){
            this.patientsService = patientsService;
        }

        @GetMapping("/patients")
        public Iterable<Patient> getPatients(){
            logger.info("inside getPatients");
            return patientsService.getPatients();
        }
        @GetMapping("/patientById")
        public Patient getPatientById(@RequestParam Integer i){
            return patientsService.getPatientById(i);
        }
        /*@GetMapping("/patientByName")
        public Patient getPatientByName(@RequestParam String family, @RequestParam String given){
            return patientsService.getPatientByFullName(family, given);
        }*/

        /*@PutMapping("/patient/update")
        public Patient updatePatient(@RequestBody Patient updatedPatientEntity){
            Patient result= new Patient();
            try{
                result = patientsService.updatePatient(updatedPatientEntity);
                logger.info("person updated successfully");
                return result;
            }catch(RuntimeException e){
                logger.error("person not found");
                return result;
            }
        }*/

        @PostMapping("/patient/add")
        public Iterable<Patient> addPatient(@RequestBody Patient newPatient){
            Iterable<Patient> listOfPatients = new ArrayList<>();
            try {
               listOfPatients = patientsService.addPatient(newPatient);
                logger.info("person added successfully");
                return listOfPatients;
            } catch (RuntimeException e) {
                logger.error("item not found");
                return listOfPatients;
            }


        }
        /*@DeleteMapping("/patient/delete")
        public Iterable<Patient> deletePatient(@RequestParam String family, @RequestParam String given){
            Iterable<Patient> listOfPatients = new ArrayList<>();
            try {
                listOfPatients = patientsService.deletePatient(family, given);
                logger.info("person deleted successfully");
                return listOfPatients;
            } catch (RuntimeException e) {
                logger.error("item not found");
                return listOfPatients;
            }
        }*/

    }
}
