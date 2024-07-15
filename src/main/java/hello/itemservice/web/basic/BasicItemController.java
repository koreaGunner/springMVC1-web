package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    //@Autowired -> //생성자가 한개만 있을땐 생략가능
    //@RequiredArgsConstructor -> final이 있는 객체에 대한 생성자를 만들어준다
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item(itemName, price, quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add") //model도 생략가능
    public String addItemV2(@ModelAttribute("item") Item item/*, Model model*/) {
        
        //item객체에 알아서 setter로 데이터를 넣어준다
        itemRepository.save(item);
        
        //주석 처리해도 가능하다
        //-> @ModelAttribute는 파라미터로 받아서 setter로 데이터를 넣어주는 것과
        //   model.addAttribute도 같이 진행한다
//        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add") //model도 생략가능
    public String addItemV3(@ModelAttribute Item item) {

        itemRepository.save(item);

        //@ModelAttribute에 name 부분을 생략해도 가능하다
        // 디폴트 룰이 존재한다
        //객체의 제일 앞의 대문자를 소문자 처리한다. Item -> item
//        model.addAttribute("item", item);

        return "basic/item";
    }

    @PostMapping("/add") //@ModelAttribute도 생략가능
    public String addItemV4(Item item) {

        itemRepository.save(item);

//        model.addAttribute("item", item);

        return "basic/item";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
