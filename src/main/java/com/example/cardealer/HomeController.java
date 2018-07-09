package com.example.cardealer;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class HomeController {

    @Autowired
    CloudinaryConfig cloudc;

    @Autowired
    CatagoryRepository catagoryRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @GetMapping("/")
    public String index(Model model,@ModelAttribute Category category,@ModelAttribute CarDealer car){
        model.addAttribute("cars", vehicleRepository.findAll());
        model.addAttribute("categories", catagoryRepository.findAll());
        return "index";
    }


    @RequestMapping("/addCategory")
    public String addCategory(Model model){
        model.addAttribute("newCategory", new Category());
        model.addAttribute("count", catagoryRepository.count());
        model.addAttribute("listCategory", catagoryRepository.findAll());
        return "addcategory";
    }

    @PostMapping("/processCategory")
    public String processCategory(@ModelAttribute Category category, Model model){

        catagoryRepository.save(category);
        model.addAttribute("newCategory", category);
        model.addAttribute("listCategory", catagoryRepository.findAll());
        model.addAttribute("count", 1);

        return "addcategory";
    }

    @RequestMapping("/addCar")
    public String addCar(Model model){
        if(catagoryRepository.count() < 1){

            return "redirect:/addCategory";
        }
        else{
            model.addAttribute("car",new CarDealer());
            model.addAttribute("categories", catagoryRepository.findAll());
            return "addcar";
        }

    }

    @PostMapping("/carProcess")
    public String processCar(@ModelAttribute CarDealer cars, @RequestParam("file")MultipartFile file){

        if(file.isEmpty()){
            return "redirect:/addCar";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcestype", "auto"));
            cars.setImage(uploadResult.get("url").toString());


            vehicleRepository.save(cars);


        }catch(IOException e){
            e.printStackTrace();
            return "redirect:/addCar";
        }
        return "redirect:/";
    }

    @RequestMapping("/details/{id}")
    public String details(@PathVariable("id") long id, Model model){


        model.addAttribute("car", vehicleRepository.findById(id).get());
        return "detail";

    }

    @RequestMapping("/categoryDetail/{id}")
    public String catDetails(@PathVariable("id") long id, Model model){

        model.addAttribute("vehicle", vehicleRepository.findAll());
        model.addAttribute("details", catagoryRepository.findById(id).get());
        return "categoryDetail";

    }

    @RequestMapping("/update/{id}")
    public String updateCar(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("car", vehicleRepository.findById(id));
        model.addAttribute("categories", catagoryRepository.findAll());
        return "addcar";
    }

    @RequestMapping("/delete/{id}")
    public String deleteCar(@PathVariable("id") long id){

        vehicleRepository.deleteById(id);
        return "redirect:/";
    }

}
