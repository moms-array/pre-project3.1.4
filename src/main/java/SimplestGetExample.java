import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class SimplestGetExample {
    static final String url = "http://91.241.64.178:7081/api/users";



    public static void main(String[] args) {
        RestTemplate restTemplate  = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ResponseEntity<String> forEntity = restTemplate.getForEntity(url,String.class);
        String session_id = forEntity.getHeaders().getFirst("Set-cookie");

        System.out.println(forEntity);
        System.out.println(session_id);
        User user = new User(3L,"James","Brown", (byte) 21);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", session_id);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));


        HttpEntity entity = new HttpEntity<>(user,headers);
        try {
            new RestTemplate().postForObject(url,entity,String.class);
        }catch (HttpStatusCodeException e){
            System.out.println(e.getStatusCode());
        }

    }

    static class User implements Serializable {
        @JsonProperty("id")
        private Long id;
        @JsonProperty("name")
        private String name;
        @JsonProperty("lastname")
        private String lastname;
        @JsonProperty("age")
        private Byte age;

        @Override
        public String toString(){
            return "{id:" + id + ", name:" + name + ", lastname:" + lastname + ", age:" + age + "}";
        }
        public User(){

        }

        public User(Long id, String name, String lastname, Byte age) {
            this.id = id;
            this.name = name;
            this.lastname = lastname;
            this.age = age;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public Byte getAge() {
            return age;
        }

        public void setAge(Byte age) {
            this.age = age;
        }

    }

}
