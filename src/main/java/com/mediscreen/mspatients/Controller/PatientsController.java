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
import java.sql.Date;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@RestController
public class PatientsController {

    static final Logger logger = LogManager.getLogger();
    private PatientsService patientsService;


    public PatientsController(PatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @GetMapping("/patients")
    public Iterable<Patient> getPatients() {
        logger.info("inside getPatients");
        return patientsService.getPatients();
    }
    //@GetMapping("/patient/{id}")
    //public Patient getPatientById(@PathVariable Integer id){
    //return patientsService.getPatientById(id);
//
    @GetMapping("/patient/{id}")
    public Patient getPatientById(@PathVariable Integer id){
        try {
            return patientsService.getPatientById(id);
        }
        catch(NoSuchElementException noSuchElementException) {
            throw new PatientNotFoundException("no patient with this id ");
        }
    }

        @GetMapping("/patientByFullName")
        public Patient getPatientByName(@Valid @PathVariable String family, @Valid @PathVariable String given){
            return patientsService.getPatientByFullName(family, given);
        }

       /* @PostMapping("/patient/update/{id}")

        public Patient updatePatient(@PathVariable("id") String id, @RequestParam(name = "family", required = true) String family, @RequestParam(name = "given", required = true) String given, @RequestParam(name = "date_of_birth", required = false) Date dateOfBirth, @RequestParam(name = "sex", required = false) String sex, @RequestParam(name = "address", required = false) String address, @RequestParam(name = "phone", required = false) String phone){
           System.out.println("date of birth in patient controller is "+dateOfBirth);
            Patient result= new Patient();

            try{
                result = patientsService.updatePatient(id, family, given, dateOfBirth, sex, address, phone);
                logger.info("person updated successfully");
                return result;
            }catch(RuntimeException e){
                logger.error("person not found");
                throw new PatientNotFoundException("patient not found");
            }
        }*/
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
       /* @PostMapping("/patient/add")
        public Iterable<Patient> addPatient(@RequestBody Patient newPatient) {
            Iterable<Patient> listOfPatients = new ArrayList<>();
            try {
                listOfPatients = patientsService.addPatient(newPatient);
                logger.info("person added successfully");
                return listOfPatients;
            } catch (RuntimeException e) {
                logger.error("item not found");
                return listOfPatients;
            }
        }*/

    //curl -d "family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333" -X POST http://localhost:8081/patient/add

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
