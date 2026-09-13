package br.com.soc.sistema.vo;

public class CompromissoVo {

	private String rowid;
	private String codigoFuncionario;
	private String codigoAgenda;
	private String dataCompromisso; // Formato YYYY-MM-DD
	private String horarioCompromisso; // Formato HH:mm

	private String nomeFuncionario;
	private String nomeAgenda;

	public CompromissoVo() {}

	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getCodigoFuncionario() {
		return codigoFuncionario;
	}
	public void setCodigoFuncionario(String codigoFuncionario) {
		this.codigoFuncionario = codigoFuncionario;
	}
	public String getCodigoAgenda() {
		return codigoAgenda;
	}
	public void setCodigoAgenda(String codigoAgenda) {
		this.codigoAgenda = codigoAgenda;
	}
	public String getDataCompromisso() {
		return dataCompromisso;
	}
	public void setDataCompromisso(String dataCompromisso) {
		this.dataCompromisso = dataCompromisso;
	}
	public String getHorarioCompromisso() {
		return horarioCompromisso;
	}
	public void setHorarioCompromisso(String horarioCompromisso) {
		this.horarioCompromisso = horarioCompromisso;
	}
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}
	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}
	public String getNomeAgenda() {
		return nomeAgenda;
	}
	public void setNomeAgenda(String nomeAgenda) {
		this.nomeAgenda = nomeAgenda;
	}
	public String getDataFormatada() {
		if(dataCompromisso != null && dataCompromisso.trim().length() == 10) {
			String[] partes = dataCompromisso.split("-");
			if(partes.length == 3) {
				return partes[2] + "/" + partes[1] + "/" + partes[0];
			}
		}
		return dataCompromisso;
	}

	@Override
	public String toString() {
		return "CompromissoVo [rowid=" + rowid + ", codigoFuncionario=" + codigoFuncionario 
				+ ", codigoAgenda=" + codigoAgenda + ", dataCompromisso=" + dataCompromisso 
				+ ", horarioCompromisso=" + horarioCompromisso + ", nomeFuncionario=" + nomeFuncionario 
				+ ", nomeAgenda=" + nomeAgenda + "]";
	}
}