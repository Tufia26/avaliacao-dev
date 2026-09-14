package br.com.soc.sistema.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.business.CompromissoBusiness;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.infra.Action;
import br.com.soc.sistema.infra.RelatorioCompromissoExcel;
import br.com.soc.sistema.vo.CompromissoVo;

public class RelatorioCompromissoAction extends Action {

	private String dataInicial;
	private String dataFinal;
	private List<CompromissoVo> compromissos = new ArrayList<>();

	private InputStream inputStream;
	private String contentDisposition;

	private CompromissoBusiness business = new CompromissoBusiness();


	public String abrir() {
		return SUCCESS;
	}


	public String filtrar() {
		try {
			compromissos = business.buscarCompromissosPorPeriodo(dataInicial, dataFinal);
			return SUCCESS;
		} catch (BusinessException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}


	public String exportarExcel() {
		try {
			compromissos = business.buscarCompromissosPorPeriodo(dataInicial, dataFinal);
			
			if (compromissos.isEmpty()) {
				addActionError("Não existem compromissos no período informado para exportação.");
				return INPUT;
			}

			inputStream = RelatorioCompromissoExcel.gerar(compromissos);
			contentDisposition = "attachment; filename=\"relatorio_compromissos.xlsx\"";

			return "excel";
		} catch (BusinessException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
	}

	public String getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(String dataInicial) {
		this.dataInicial = dataInicial;
	}

	public String getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(String dataFinal) {
		this.dataFinal = dataFinal;
	}

	public List<CompromissoVo> getCompromissos() {
		return compromissos;
	}

	public void setCompromissos(List<CompromissoVo> compromissos) {
		this.compromissos = compromissos;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
}