package ru.makedonskaya.fool.card.game.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import ru.makedonskaya.fool.card.game.service.IMainService;


@Controller
public class FoolController {
	
	@Autowired
	private IMainService service;
	
	@GetMapping("/new/game/fool")
	public String newGame(Model model) {
		service.newGame();
		service.start();
		
		return "redirect:/game/fool";
	}
	
	@GetMapping("/game/fool")
	public String showGame(Model model) {
		model.addAttribute("field", service.getField());
		model.addAttribute("handGamer", service.sortedHand(service.getHandGamer()));
		model.addAttribute("trump",service.showImgTrumpCard());
		model.addAttribute("deckOfCards", service.getNumCardsInDeck());
		model.addAttribute("bot", service.getNumCardsOfBot());
		model.addAttribute("PleerTurn", service.getPleerTurn());
		model.addAttribute("Queue", service.getQueuePleer());
		model.addAttribute("graveyard", service.getSizeGraveyard());
		model.addAttribute("winner", service.showMassage());
		
		return "fool-field";
	}
	
	
	@GetMapping("/post/card/{index}")
	public String getField(@PathVariable("index") Integer index, Model model) {
		service.playGamer(index);
		
		return "redirect:/game/fool";
	}
	
	@GetMapping("/take/card")
	public String takeCards(Model model) {
		service.takeCards();

		return "redirect:/game/fool";
	}
	
	@GetMapping("/clear/field")
	public String clearField(Model model) {
		service.clearField();

		return "redirect:/game/fool";
	}
	
	
		
}
