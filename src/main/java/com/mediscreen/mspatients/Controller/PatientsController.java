package com.mediscreen.mspatients.Controller;

import com.mediscreen.mspatients.exceptions.PatientNotFoundException;
import com.mediscreen.mspatients.model.Patient;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.mediscreen.mspatients.service.PatientsService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
public class PatientsController {

    static final Logger logger = LogManager.getLogger();
    private PatientsService patientsService;


    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }
    /**api request : all patients__tested postman*/
    @GetMapping("/patient/all")
    public Iterable<Patient> getPatients() {
        logger.info("inside getPatients");
        return patientsService.getPatients();
    }
    //@GetMapping("/patient/{id}")
    //public Patient getPatientById(@PathVariable Integer id){
    //return patientsService.getPatientById(id);
//
    /**api request : one patient by id__tested postman*/
    @GetMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable Integer id){
        try {
            return patientsService.getPatientById(id);
        }
        catch(NoSuchElementException noSuchElementException) {
            throw new PatientNotFoundException("no patient with this id ");
        }
    }
    /**api request : one patient by family and given as PathVariables__tested postman*/
    //@GetMapping("/ByFullName/{family}/{given}")
    @PostMapping("/patient")
    public int getPatIdFromFamilyAndGiven(@RequestParam ("family") String family, @RequestParam  ("given")String given){

            Patient patient = patientsService.getPatientByFullName(family, given);
            int patId = patient.getPatient_id();
            return patId;

        }

    /**api request : update patient by id and Patient RequestBody__tested postman*/
       @PostMapping("/patient/update/{id}")

       public void updatePatient(@PathVariable("id") String id, @RequestBody Patient patient){
           System.out.println(patient.getFamily());
           Patient result= new Patient();

           try{
               patientsService.updatePatient(id, patient);

           }catch(RuntimeException e){
               logger.error("person not found");
               throw new PatientNotFoundException("patient not found");
           }
       }

    /**api request : add one patient with Patient object as parameter__tested postman*/
            @PostMapping("/patient/add")
            public ResponseEntity<Patient>addPatient(@RequestBody Patient patient){
                //null
                System.out.println("inside PatientController is "+ patient.getFamily());
                try {
                    patientsService.addPatient(patient);
                    logger.info("person added successfully");
                    URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().replacePath("/patient/{id}").buildAndExpand(patient.getPatient_id()).toUri();
                    logger.info("uri location is "+ location.toString());
                    return ResponseEntity.created(location).build();
                } catch (RuntimeException e) {
                    logger.error("item not found");
                    return ResponseEntity.noContent().build();
                    }

            }
    /**api request : delete one patient by id__tested postman__ be careful about handling errors, for now 200 OK!! and person deleted successfully even if non existing id*/
    @GetMapping("/patient/delete/{id}")
        public void deletePatient(@PathVariable ("id")String id){
           System.out.println("in PatientController, patient's id to delete : "+ id);
            try {
                patientsService.deletePatient(id);
                logger.info("person deleted successfully");
            } catch (RuntimeException e) {
                logger.error("item not found");//
            }
        }


}
