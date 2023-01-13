/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ws.b.FINALEXAM.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import model.entity.Person;
import model.jpacontroller.PersonJpaController;
import org.springframework.http.HttpEntity;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Rezka Mulya_20200140010
 */
@RestController
@CrossOrigin
@ResponseBody
public class Controller {
    Person data = new Person();
    PersonJpaController ctrl = new PersonJpaController();
    
    //fungsi get menampilkan data dengan id
    @RequestMapping(value = "/GET/{id}")
    public String getNameById(@PathVariable("id") int id){
        try{
            data = ctrl.findPerson(id);
        } catch (Exception e){
            
        }
        return data.getNama();
    }
    
    //fungsi get menampilkan semua data
    @RequestMapping(value = "/GET")
    public List<Person> getAll(){
        List<Person> person = new ArrayList();
        try{
            person = ctrl.findPersonEntities();
            
        }catch(Exception e){
            person = List.of();
        }
        return person;
    }
    
    //fungsi post untuk menambahkan data
    @RequestMapping(value = "/POST",
            method = RequestMethod.POST,
            consumes = APPLICATION_JSON_VALUE)
    public String createData(HttpEntity<String> paket){
        String message = "";
        try{
            String json_receive = paket.getBody();
            
            ObjectMapper map = new ObjectMapper();
            
            Person data = new Person();
            
            data = map.readValue(json_receive, Person.class);
            
            ctrl.create(data);
            message = data.getNama()+ " Berhasil disimpan";
        } catch(Exception e){       
             message = " Failed";
        }
        return message;
    }
    
    //fungsi put untuk mengedit data
    @RequestMapping(value = "/PUT",
            method = RequestMethod.PUT,
            consumes = APPLICATION_JSON_VALUE)
    public String editData(HttpEntity<String> paket){
        String message = " no action ";
        try{
            String json_receive = paket.getBody();
            
            ObjectMapper map = new ObjectMapper();
            
            Person newdata = new Person();
            
            newdata = map.readValue(json_receive, Person.class);
            
            ctrl.edit(newdata);
            message = newdata.getNama()+ " Berhasil diedit";
            
        } catch (Exception e){   
        }
        return message;
    }
    
    //fungsi delete untuk menghapus data
    @RequestMapping(value = "/DELETE",
            method = RequestMethod.DELETE,
            consumes = APPLICATION_JSON_VALUE)
    public String deleteData(HttpEntity<String> paket)throws JsonProcessingException{
        String message = " id tidak ada";
        try{
            String json_receive = paket.getBody();
            
            ObjectMapper map = new ObjectMapper();
            
            Person newdata = new Person();
            
            newdata = map.readValue(json_receive, Person.class);
            
            ctrl.destroy(newdata.getId());
            message = newdata.getNama()+ " Berhasil dihapus";
            
        } catch (Exception e){   
        }
        return message;
        
    }
}

