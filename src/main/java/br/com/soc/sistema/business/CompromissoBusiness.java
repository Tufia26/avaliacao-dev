package br.com.soc.sistema.business;

import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.dao.CompromissoDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.vo.AgendaVo;
import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoBusiness {

	private static final String FOI_INFORMADO_CARACTER_INVALIDO = "Não foi informado um código válido para a busca.";
	
	private CompromissoDao dao = new CompromissoDao();
	private AgendaDao agendaDao = new AgendaDao();

	public CompromissoBusiness() {
		this.dao = new CompromissoDao();
		this.agendaDao = new AgendaDao();
	}

	public void salvarCompromisso(CompromissoVo compromissoVo) {
		validarCamposObrigatorios(compromissoVo);
		validarDisponibilidadeHorario(compromissoVo);
		dao.insertCompromisso(compromissoVo);
	}

	public void atualizarCompromisso(CompromissoVo compromissoVo) {
		if (compromissoVo.getRowid() == null || compromissoVo.getRowid().trim().isEmpty()) {
			throw new BusinessException("Código do compromisso é obrigatório para alteração.");
		}

		validarCamposObrigatorios(compromissoVo);
		validarDisponibilidadeHorario(compromissoVo);
		dao.updateCompromisso(compromissoVo);
	}

	public void excluirCompromisso(String rowid) {
		try {
			if (rowid == null || rowid.trim().isEmpty()) {
				throw new BusinessException("Código inválido para exclusão.");
			}
			dao.deleteByCodigo(rowid);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_INVALIDO);
		}
	}

	public CompromissoVo buscarCompromissoPor(String rowid) {
		try {
			if (rowid == null || rowid.trim().isEmpty()) {
				throw new BusinessException(FOI_INFORMADO_CARACTER_INVALIDO);
			}
			return dao.findByCodigo(rowid);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_INVALIDO);
		}
	}
	
	public List<CompromissoVo> buscarCompromissosPorPeriodo(String dataInicial, String dataFinal) {
		if (dataInicial == null || dataInicial.trim().isEmpty()) {
			throw new BusinessException("A Data Inicial é obrigatória para o relatório.");
		}
		if (dataFinal == null || dataFinal.trim().isEmpty()) {
			throw new BusinessException("A Data Final é obrigatória para o relatório.");
		}

		if (dataInicial.compareTo(dataFinal) > 0) {
			throw new BusinessException("A Data Inicial não pode ser posterior à Data Final.");
		}

		return dao.findCompromissosByPeriodo(dataInicial.trim(), dataFinal.trim());
	}

	public List<CompromissoVo> trazerTodosOsCompromissos() {
		return dao.findAllCompromissos();
	}

	private void validarCamposObrigatorios(CompromissoVo vo) {
		if (vo == null) {
			throw new BusinessException("Os dados do compromisso não foram preenchidos.");
		}
		if (vo.getCodigoFuncionario() == null || vo.getCodigoFuncionario().trim().isEmpty()) {
			throw new BusinessException("Selecione um funcionário para o compromisso.");
		}
		if (vo.getCodigoAgenda() == null || vo.getCodigoAgenda().trim().isEmpty()) {
			throw new BusinessException("Selecione uma agenda para o compromisso.");
		}
		if (vo.getDataCompromisso() == null || vo.getDataCompromisso().trim().isEmpty()) {
			throw new BusinessException("A data do compromisso é obrigatória.");
		}
		if (vo.getHorarioCompromisso() == null || vo.getHorarioCompromisso().trim().isEmpty()) {
			throw new BusinessException("O horário do compromisso é obrigatório.");
		}
	}

	private void validarDisponibilidadeHorario(CompromissoVo vo) {
		Integer idAgenda = Integer.parseInt(vo.getCodigoAgenda());
		AgendaVo agenda = agendaDao.findByCodigo(idAgenda);

		if (agenda == null) {
			throw new BusinessException("Agenda selecionada não foi encontrada no sistema.");
		}

		String periodo = agenda.getPeriodoDisponivel();
		int hora = extrairHora(vo.getHorarioCompromisso());

		boolean isTurnoManha = (hora >= 0 && hora < 12);
		boolean isTurnoTarde = (hora >= 12 && hora < 24);

		// 1 = Manha | 2 = Tarde | 3 = Ambos
		if ("1".equals(periodo) && !isTurnoManha) {
			throw new BusinessException("Horário inválido: a agenda '" + agenda.getNome() 
				+ "' atende apenas no período da MANHÃ (até 11:59).");
		} else if ("2".equals(periodo) && !isTurnoTarde) {
			throw new BusinessException("Horário inválido: a agenda '" + agenda.getNome() 
				+ "' atende apenas no período da TARDE (a partir de 12:00).");
		}
	}

	private int extrairHora(String horario) {
		try {
			String[] partes = horario.trim().split(":");
			return Integer.parseInt(partes[0]);
		} catch (Exception e) {
			throw new BusinessException("Formato de horário inválido. Utilize o formato HH:mm.");
		}
	}
}