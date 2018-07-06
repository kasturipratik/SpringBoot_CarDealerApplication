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
    public String index(Model model){
        model.addAttribute("categories", catagoryRepository.findAll());
        model.addAttribute("cars", vehicleRepository.findAll());
        return "index";
    }


    @RequestMapping("/addCategory")
    public String addCategory(Model model){
        model.addAttribute("category", new Category());
        return "addcategory";
    }

    @PostMapping("/processCategory")
    public String processCategory(@ModelAttribute Category category){
        catagoryRepository.save(category);
        return "redirect:/";
    }

    @RequestMapping("/addCar")
    public String addCar(Model model){
        model.addAttribute("car",new CarDealer());
        model.addAttribute("categories", catagoryRepository.findAll());

        return "addcar";
    }

    @PostMapping("/carProcess")
    public String processCar(@ModelAttribute("car") CarDealer cars, @RequestParam("file")MultipartFile file){
        if(file.isEmpty()){
            return "redirect:/addCar";
        }
        try{
            Map uploadResult = cloudc.upload(file.getBytes(), ObjectUtils.asMap("resourcestype", "auto"));
            cars.setImage(uploadResult.get("url").toString());

            Set<CarDealer> carDealers = new HashSet<CarDealer>();
            carDealers.add(cars);

            /*Category category = new Category();
            category.setCars(carDealers);*/
         vehicleRepository.save(cars);
    /*    catagoryRepository.save(category);*/

        }catch(IOException e){
            e.printStackTrace();
            return "redirect:/addCar";
        }
        return "redirect:/";
    }

    @RequestMapping("/details/{id}")
    public String details(@PathVariable("id") long id, Model model){
        model.addAttribute("detail", vehicleRepository.findById(id));
        return "detail";

    }

    @RequestMapping("/detail/{id}")
    public String catDetails(@PathVariable("id") long id, Model model){
        model.addAttribute("details", catagoryRepository.findById(id));
        return "categoryDetails";

    }

}
