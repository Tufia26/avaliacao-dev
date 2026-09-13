package br.com.soc.sistema.business;

import java.util.ArrayList;
import java.util.List;

import br.com.soc.sistema.dao.FuncionarioDao;
import br.com.soc.sistema.exception.BusinessException;
import br.com.soc.sistema.filter.FuncionarioFilter;
import br.com.soc.sistema.vo.FuncionarioVo;

public class FuncionarioBusiness {

	private static final String FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO = "Foi informado um caracter no lugar de um numero";
	private FuncionarioDao dao;
	
	public FuncionarioBusiness() {
		this.dao = new FuncionarioDao();
	}
	
	public List<FuncionarioVo> trazerTodosOsFuncionarios(){
		return dao.findAllFuncionarios();
	}	
	
	public void salvarFuncionario(FuncionarioVo funcionarioVo) {
		try {
			if(funcionarioVo.getNome().isEmpty())
				throw new IllegalArgumentException("Nome nao pode ser em branco");
			
			dao.insertFuncionario(funcionarioVo);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a inclusao do registro");
		}
		
	}	
	
	public List<FuncionarioVo> filtrarFuncionarios(FuncionarioFilter filter){
		List<FuncionarioVo> funcionarios = new ArrayList<>();
		
		switch (filter.getOpcoesCombo()) {
			case ID:
				try {
					Integer codigo = Integer.parseInt(filter.getValorBusca());
					funcionarios.add(dao.findByCodigo(codigo));
				}catch (NumberFormatException e) {
					throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
				}
			break;

			case NOME:
				funcionarios.addAll(dao.findAllByNome(filter.getValorBusca()));
			break;
		}
		
		return funcionarios;
	}
	
	public FuncionarioVo buscarFuncionarioPor(String codigo) {
		try {
			Integer cod = Integer.parseInt(codigo);
			return dao.findByCodigo(cod);
		}catch (NumberFormatException e) {
			throw new BusinessException(FOI_INFORMADO_CARACTER_NO_LUGAR_DE_UM_NUMERO);
		}
	}
	
	public void atualizarFuncionario(FuncionarioVo funcionarioVo) {
		try {
			if(funcionarioVo.getNome() == null || funcionarioVo.getNome().trim().isEmpty()) {
				throw new IllegalArgumentException("Nome nao pode ser em branco");
			}
			if(funcionarioVo.getRowid() == null || funcionarioVo.getRowid().trim().isEmpty()) {
				throw new IllegalArgumentException("Identificador nao informado para alteracao");
			}
			
			dao.updateFuncionario(funcionarioVo);
		} catch (IllegalArgumentException e) {
			throw new BusinessException(e.getMessage());
		} catch (Exception e) {
			throw new BusinessException("Nao foi possivel realizar a alteracao do registro");
		}
	}

	public void excluirFuncionario(String codigo) {
		try {
			if(codigo == null || codigo.trim().isEmpty()) {
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
}
