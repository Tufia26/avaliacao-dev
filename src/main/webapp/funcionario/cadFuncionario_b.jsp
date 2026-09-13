<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF8">
		<title><s:text name="label.titulo.pagina.consulta"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark mb-4 shadow-sm">
		    <div class="container">
		        <span class="navbar-brand font-weight-bold">Sistema SOC</span>
		        <div class="navbar-nav">
		            <s:url action="todosFuncionarios" var="urlFunc" />
		            <s:url action="todosAgendas" var="urlAgend" />
		            <s:url action="todosCompromissos" var="urlComp" />
		            
		            <a class="nav-link active" href="${urlFunc}">Funcionários</a>
		            <a class="nav-link" href="${urlAgend}">Agendas</a>
		            <a class="nav-link" href="${urlComp}">Compromissos</a>
		        </div>
		    </div>
		</nav>
		<div class="container">
			<div class="row mt-5 mb-2">
				<div class="col-sm p-0">
					<s:form action="/filtrarFuncionarios.action">
						<div class="input-group">
							<span class="input-group-text">
								<strong><s:text name="label.buscar.por"/></strong>
							</span>	
								<s:select  
									cssClass="form-select" 
									name="filtrar.opcoesCombo" 
									list="listaOpcoesCombo"  
									headerKey=""  
									headerValue="Escolha..." 
									listKey="%{codigo}" 
									listValueKey="%{descricao}"
									value="filtrar.opcoesCombo.codigo"									
								/>
								
								<s:textfield cssClass="form-control" id="nome" name="filtrar.valorBusca"/>
								<button class="btn btn-primary" type="submit"><s:text name="label.pesquisar"/></button>
						</div>
					</s:form>			
				</div>				
			</div>

			<div class="row">
				<table class="table table-light table-striped align-middle">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.nome"/></th>
							<th class="text-end mt-5"><s:text name="label.acao"/></th>
						</tr>
					</thead>
					
					<tbody>
						<s:iterator value="funcionarios">
							<tr>
								<td>${rowid}</td>
								<td>${nome}</td>
								<td class="text-end">
									<s:url action="editarFuncionarios" var="editar">
										<s:param name="funcionarioVo.rowid" value="rowid"></s:param>
									</s:url>

									<a href="${editar}" class="btn btn-warning text-white">
										<s:text name="label.editar"/>
									</a>

									<a href="#" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#confirmarExclusao" data-bs-id="${rowid}">
										<s:text name="label.excluir"/>
									</a>
								</td>
							</tr>
						</s:iterator>
					</tbody>
					
					<tfoot class="table-secondary">
						<tr>
							<td colspan="3">
								<s:url action="novoFuncionarios" var="novo"/>
								
								<a href="${novo}" class="btn btn-success">
									<s:text name="label.novo"/>
								</a>
							</td>
						</tr>
					</tfoot>				
				</table>
			</div>
		</div>
		
		<div class="modal fade" id="confirmarExclusao" 
			data-bs-backdrop="static" data-bs-keyboard="false"
			tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title"><s:text name="label.modal.titulo"/></h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
		      </div>
		      
		      <div class="modal-body">
		      	<span><s:text name="label.modal.corpo"/></span>
		      </div>
		      
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">
					<s:text name="label.nao"/>
				</button>
		        	
				<a id="excluir" href="#" class="btn btn-primary" style="width: 75px;">
					<s:text name="label.sim"/>
				</a>						
		      </div>
		    </div>		    
		  </div>
		</div>
		
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
		<script>
			var modalExclusao = document.getElementById('confirmarExclusao');
			modalExclusao.addEventListener('show.bs.modal', function (event) {
				var botao = event.relatedTarget;
				var id = botao.getAttribute('data-bs-id');
				var linkSim = document.getElementById('excluir');
				linkSim.href = 'excluirFuncionarios.action?funcionarioVo.rowid=' + id;
			});
		</script>
	</body>
</html>