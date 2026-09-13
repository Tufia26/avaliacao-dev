package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.AgendaDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.AgendaFilter;
import br.com.soc.sistema.infra.DisponibilidadeEnum;
import br.com.soc.sistema.vo.AgendaVo;

public class AgendaBusiness {

	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private AgendaDao dao;

	public AgendaBusiness() {
		this.dao = new AgendaDao();
	}

	public List<AgendaVo> trazerTodasAsAgendas() {
		return dao.findAllAgendas();
	}
	
	public void salvarAgenda(AgendaVo agendaVo) {
		try {
			validarDadosAgenda(agendaVo);
			dao.insertAgenda(agendaVo);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao do registro");
		}
	}
	
	public List<AgendaVo> filtrarAgendas(AgendaFilter filter) {
		List<AgendaVo> agendas = new ArrayList<>();

		switch (filter.getOpcoesCombo()) {
			case ID:
				try {
					Integer codigo = Integer.parseInt(filter.getValorBusca());
					AgendaVo vo = dao.findByCodigo(codigo);
					if (vo != null) {
						agendas.add(vo);
					}
				} catch (NumberFormatException e) {
					throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
				}
				break;

			case NOME:
				agendas.addAll(dao.findAllByNome(filter.getValorBusca()));
				break;
		}

		return agendas;
	}

	public AgendaVo buscarAgendaPor(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return dao.findByCodigo(cod);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}

	public void atualizarAgenda(AgendaVo agendaVo) {
		try {
			if (agendaVo.getRowid() == null || agendaVo.getRowid().trim().isEmpty()) {
				throw new IllegalArgumentException("Identificador nao informado para alteracao");
			}
			validarDadosAgenda(agendaVo);
			dao.updateAgenda(agendaVo);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a alteracao do registro");
		}
	}

	public void excluirAgenda(String codigo) {
		try {
			if (codigo == null || codigo.trim().isEmpty()) {
				throw new IllegalArgumentException("Codigo nao informado para exclusao");
			}

			Integer cod = Integer.parseInt(codigo);

			dao.deleteByCodigo(cod);
		} catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a exclusao do registro");
		}
	}

	private void validarDadosAgenda(AgendaVo agendaVo) {
		if(agendaVo == null) {
			throw new IllegalArgumentException("Dados da agenda nao informados");
		}
		if(agendaVo.getNome() == null || agendaVo.getNome().trim().isEmpty()) {
			throw new IllegalArgumentException("Nome nao pode ser em branco");
		}
		if(agendaVo.getPeriodoDisponivel() == null || agendaVo.getPeriodoDisponivel().trim().isEmpty()) {
			throw new IllegalArgumentException("Periodo disponivel e obrigatorio");
		}
		if(DisponibilidadeEnum.buscarPorCodigo(agendaVo.getPeriodoDisponivel()) == null) {
			throw new IllegalArgumentException("Periodo disponivel invalido");
		}
	}
}