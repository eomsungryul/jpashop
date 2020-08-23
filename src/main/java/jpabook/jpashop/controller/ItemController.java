package jpabook.jpashop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ItemController {
	private final ItemService itemService;

	@GetMapping(value = "/items/new")
	public String createForm(Model model) {
		model.addAttribute("form", new BookForm());
		return "items/createItemForm";
	}

	@PostMapping(value = "/items/new")
	public String create(BookForm form) {
		Book book = new Book();
		book.setName(form.getName());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		itemService.saveItem(book);
		return "redirect:/items";
	}

	/**
	 * 상품 목록
	 */
	@GetMapping(value = "/items")
	public String list(Model model) {
		List<Item> items = itemService.findItems();
		model.addAttribute("items", items);
		return "items/itemList";
	}

	/**
	 * 상품 수정 폼
	 */
	@GetMapping(value = "/items/{itemId}/edit")
	public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
		Book item = (Book) itemService.findOne(itemId);
		BookForm form = new BookForm();
		form.setId(item.getId());
		form.setName(item.getName());
		form.setPrice(item.getPrice());
		form.setStockQuantity(item.getStockQuantity());
		form.setAuthor(item.getAuthor());
		form.setIsbn(item.getIsbn());
		model.addAttribute("form", form);
		return "items/updateItemForm";
	}

	/**
	 * 상품 수정
	 */
	@PostMapping(value = "/items/{itemId}/edit")
	public String updateItem(@ModelAttribute("form") BookForm form) {
		//new로 했기 떄문에 준영속 엔티티임! 
		//수정하는 두가지 방법
		
//		변경 감지 기능 사용
//		병합( merge ) 사용
		//가장 좋은 해결 방법
		//엔티티를 변경할 때는 항상 변경 감지를 사용하세요

		
//		Book book = new Book();
//		book.setId(form.getId());
//		book.setName(form.getName());
//		book.setPrice(form.getPrice());
//		book.setStockQuantity(form.getStockQuantity());
//		book.setAuthor(form.getAuthor());
//		book.setIsbn(form.getIsbn());
//		itemService.saveItem(book);
		System.out.println(form);
		
		itemService.updateItem(form.getId(), form.getName(), form.getPrice(),form.getStockQuantity());
		
		return "redirect:/items";
	}
}