package ru.makedonskaya.tictac;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TicTacGameController {
	
	@Autowired
	TicTacService service;
	
	@Autowired
	TicTacBotService botService;
	
	@GetMapping("/game/new")
	public String showNewField(Model model) {
		model.addAttribute("field", service.getNewPlayField());
		model.addAttribute("winner", service.getWinner());
		
		return "play-field";
	}	
	
	@GetMapping("/game/selection/{row_id}/{col_id}")
	public String getField(@PathVariable("row_id") int row, @PathVariable("col_id") int col, Model model) {
		model.addAttribute("field", service.safekeeping(row, col));
		model.addAttribute("winner", service.getWinner());
	      
		return "play-field";
	}
	
	@GetMapping("/game/new/bot")
	public String newGame(Model model) {
		model.addAttribute("field", botService.getNewPlayField());
		model.addAttribute("winner", botService.getWinner());
		
		return "game-bot";
	}
	
	@GetMapping("/game/bot/selection/{row_id}/{col_id}")
	public String getB(@PathVariable("row_id") int row, @PathVariable("col_id") int col, Model model) {
		model.addAttribute("field", botService.safekeeping(row, col));
		model.addAttribute("winner", botService.getWinner());
	      
		return "game-bot";
	}

}
