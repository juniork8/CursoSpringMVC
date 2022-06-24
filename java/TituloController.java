package com.algaworks.cobranca.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;
import com.algaworks.cobranca.repository.filter.TituloFilter;
import com.algaworks.cobranca.service.CadastroTituloService;

import antlr.collections.List;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	private static final String CADASTRO_VIEW = "cadastroTitulo";
	
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		 return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
		public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
			if(errors.hasErrors()) {
				return CADASTRO_VIEW;
			}
			try {
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Titulo salvo com sucesso");
			return "redirect:/titulo/novo";
			} catch (IllegalArgumentException e) {
				errors.rejectValue("dataVencimento", null, e.getMessage());
				return CADASTRO_VIEW;
			}
	}
		
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro")TituloFilter filtro) {
		
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();
		List todosTitulos = (List) titulos.findByDescricaoContaining(descricao);
		
		ModelAndView mv = new ModelAndView("PesquisarTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
	@Autowired
	private Titulos titulos;
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(titulo);
		return mv;
	}
	
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable long codigo, RedirectAttributes attributes) {
		cadastroTituloService.excluir(codigo);
		
		attributes.addAttribute("mensagem", "Título excluído com sucesso");
		return "redirect:/titulos";
	}
		
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable long codigo) {
		return cadastroTituloService.receber(codigo);
	}
	
	@ModelAttribute("todosStatusTitulo")
	public List todosStatusTitulo() {
		return (List) Arrays.asList(StatusTitulo.values());
	}
	
}

