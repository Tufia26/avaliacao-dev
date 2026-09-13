package br.com.soc.sistema.action;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.AgendaBusiness;
import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.business.FuncionarioBusiness;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;
import br.com.soc.sistema.vo.FuncionarioVo;

public class CompromissoAction extends Action {

	private CompromissoVo compromissoVo = new CompromissoVo();
	private List<CompromissoVo> compromissos = new ArrayList<>();
	
	private List<FuncionarioVo> funcionarios = new ArrayList<>();
	private List<AgendaVo> agendas = new ArrayList<>();

	private CompromissoBusiness business = new CompromissoBusiness();
	private FuncionarioBusiness funcionarioBusiness = new FuncionarioBusiness();
	private AgendaBusiness agendaBusiness = new AgendaBusiness();

	public String todos() {
		compromissos = business.trazerTodosOsCompromissos();
		return SUCCESS;
	}

	public String novo() {
		carregarCombos();
		return INPUT;
	}

	public String editar() {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().trim().isEmpty()) {
			return REDIRECT;
		}

		compromissoVo = business.buscarCompromissoPor(compromissoVo.getRowid());
		carregarCombos();
		return INPUT;
	}

	public String salvar() {
		try {
			if (compromissoVo.getRowid() != null && !compromissoVo.getRowid().trim().isEmpty()) {
				business.atualizarCompromisso(compromissoVo);
			} else {
				business.salvarCompromisso(compromissoVo);
			}
			return REDIRECT;
		} catch (BusinessException e) {
			addActionError(e.getMessage());
			carregarCombos();
			return INPUT;
		}
	}

	public String excluir() {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().trim().isEmpty()) {
			return REDIRECT;
		}

		try {
			business.excluirCompromisso(compromissoVo.getRowid());
			return REDIRECT;
		} catch (BusinessException e) {
			addActionError(e.getMessage());
			return todos();
		}
	}

	private void carregarCombos() {
		funcionarios = funcionarioBusiness.trazerTodosOsFuncionarios();
		agendas = agendaBusiness.trazerTodasAsAgendas();
	}

	public CompromissoVo getCompromissoVo() {
		return compromissoVo;
	}

	public void setCompromissoVo(CompromissoVo compromissoVo) {
		this.compromissoVo = compromissoVo;
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}

	public void setCompromissos(List<CompromissoVo> compromissos) {
		this.compromissos = compromissos;
	}

	public List<FuncionarioVo> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<FuncionarioVo> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<AgendaVo> getAgendas() {
		return agendas;
	}

	public void setAgendas(List<AgendaVo> agendas) {
		this.agendas = agendas;
	}
}