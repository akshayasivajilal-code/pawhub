package com.example.demo;

import com.example.demo.entity.Pet;
import com.example.demo.repository.PetRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PetDataLoader {

    @Bean
    CommandLineRunner loadPets(PetRepository petRepository) {
        return args -> {
            // Load sample pets only when database is empty
            if (petRepository.count() == 0) {

                // ================= DOGS =================
                Pet pet1 = new Pet();
                pet1.setName("Buddy");
                pet1.setType("Dog");
                pet1.setBreed("Jack Russell");
                pet1.setAge(2);
                pet1.setGender("Male");
                pet1.setDescription("Friendly, high-energy and playful companion dog.");
                pet1.setImage("puppy1.jpg");
                pet1.setStatus("AVAILABLE");

                Pet pet2 = new Pet();
                pet2.setName("Charlie");
                pet2.setType("Dog");
                pet2.setBreed("Labrador");
                pet2.setAge(3);
                pet2.setGender("Male");
                pet2.setDescription("Loyal, affectionate, and great with kids.");
                pet2.setImage("puppy2.jpg");
                pet2.setStatus("AVAILABLE");

                Pet pet3 = new Pet();
                pet3.setName("Luna");
                pet3.setType("Dog");
                pet3.setBreed("Beagle");
                pet3.setAge(2);
                pet3.setGender("Female");
                pet3.setDescription("Curious, cheerful and loves outdoor walks.");
                pet3.setImage("puppy3.jpg");
                pet3.setStatus("AVAILABLE");

                Pet pet4 = new Pet();
                pet4.setName("Bella");
                pet4.setType("Dog");
                pet4.setBreed("Golden Retriever");
                pet4.setAge(4);
                pet4.setGender("Female");
                pet4.setDescription("Gentle, sweet-natured, and wonderfully trained.");
                pet4.setImage("puppy4.jpg");
                pet4.setStatus("AVAILABLE");

                Pet pet5 = new Pet();
                pet5.setName("Snow");
                pet5.setType("Dog");
                pet5.setBreed("Husky");
                pet5.setAge(3);
                pet5.setGender("Male");
                pet5.setDescription("Majestic, active and playful Siberian Husky.");
                pet5.setImage("puppy5.jpg");
                pet5.setStatus("AVAILABLE");

                Pet pet6 = new Pet();
                pet6.setName("Rio");
                pet6.setType("Dog");
                pet6.setBreed("German Shepherd");
                pet6.setAge(2);
                pet6.setGender("Male");
                pet6.setDescription("Intelligent, alert and loyal protector.");
                pet6.setImage("puppy6.jpg");
                pet6.setStatus("AVAILABLE");

                // ================= CATS =================
                Pet pet7 = new Pet();
                pet7.setName("Milo");
                pet7.setType("Cat");
                pet7.setBreed("Persian Cat");
                pet7.setAge(1);
                pet7.setGender("Male");
                pet7.setDescription("Quiet, affectionate and calm indoor fluffy kitten.");
                pet7.setImage("cat1.jpg");
                pet7.setStatus("AVAILABLE");

                Pet pet8 = new Pet();
                pet8.setName("Lily");
                pet8.setType("Cat");
                pet8.setBreed("British Shorthair");
                pet8.setAge(2);
                pet8.setGender("Female");
                pet8.setDescription("Independent, easy-going and loves lap naps.");
                pet8.setImage("cat2.jpg");
                pet8.setStatus("AVAILABLE");

                Pet pet9 = new Pet();
                pet9.setName("Simba");
                pet9.setType("Cat");
                pet9.setBreed("Bengal Cat");
                pet9.setAge(2);
                pet9.setGender("Male");
                pet9.setDescription("Striking rosette coat, agile, energetic and vocal.");
                pet9.setImage("cat3.jpg");
                pet9.setStatus("AVAILABLE");

                Pet pet10 = new Pet();
                pet10.setName("Coco");
                pet10.setType("Cat");
                pet10.setBreed("Ragdoll");
                pet10.setAge(1);
                pet10.setGender("Female");
                pet10.setDescription("Silky coat, docile, relaxed and adores cuddles.");
                pet10.setImage("cat4.jpg");
                pet10.setStatus("AVAILABLE");

                Pet pet11 = new Pet();
                pet11.setName("Nala");
                pet11.setType("Cat");
                pet11.setBreed("Siamese");
                pet11.setAge(3);
                pet11.setGender("Female");
                pet11.setDescription("Sleek, talkative, loving and social companion.");
                pet11.setImage("cat5.jpg");
                pet11.setStatus("AVAILABLE");

                Pet pet12 = new Pet();
                pet12.setName("Leo");
                pet12.setType("Cat");
                pet12.setBreed("Maine Coon");
                pet12.setAge(3);
                pet12.setGender("Male");
                pet12.setDescription("Gentle giant with a heart of gold and lush fur.");
                pet12.setImage("cat6.jpg");
                pet12.setStatus("AVAILABLE");

                // ================= SAVE =================
                petRepository.save(pet1);
                petRepository.save(pet2);
                petRepository.save(pet3);
                petRepository.save(pet4);
                petRepository.save(pet5);
                petRepository.save(pet6);
                petRepository.save(pet7);
                petRepository.save(pet8);
                petRepository.save(pet9);
                petRepository.save(pet10);
                petRepository.save(pet11);
                petRepository.save(pet12);

                System.out.println("🐾 PAWHUB: 6 dogs + 6 cats initialized successfully!");
            }
        };
    }
}