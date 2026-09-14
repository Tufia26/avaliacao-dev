<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
		        <span class="navbar-brand font-weight-bold"><s:text name="label.sistema.soc" /></span>
		        <div class="navbar-nav">
		            <s:url action="todosFuncionarios" var="urlFunc" />
		            <s:url action="todosAgendas" var="urlAgend" />
		            <s:url action="todosCompromissos" var="urlComp" />
		            <s:url action="abrirRelatorios" var="urlRelat" />
		            
		            <a class="nav-link" href="${urlFunc}"><s:text name="label.funcionarios" /></a>
		            <a class="nav-link" href="${urlAgend}"><s:text name="label.agendas" /></a>
		            <a class="nav-link active" href="${urlComp}"><s:text name="label.compromissos" /></a>
		            <a class="nav-link" href="${urlRelat}"><s:text name="label.relatorios" /></a>
		        </div>
		    </div>
		</nav>
		<div class="container">
			<s:if test="hasActionErrors()">
				<div class="alert alert-danger alert-dismissible fade show" role="alert">
					<s:actionerror />
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</s:if>

			<div class="row mt-5">
				<table class="table table-light table-striped align-middle">
					<thead>
						<tr>
							<th><s:text name="label.id"/></th>
							<th><s:text name="label.funcionario"/></th>
							<th><s:text name="label.agenda"/></th>
							<th><s:text name="label.data"/></th>
							<th><s:text name="label.horario"/></th>
							<th class="text-end mt-5"><s:text name="label.acao"/></th>
						</tr>
					</thead>
					
					<tbody>
						<s:iterator value="compromissos">
							<tr>
								<td>${rowid}</td>
								<td>${nomeFuncionario}</td>
								<td>${nomeAgenda}</td>
								<td>${dataFormatada}</td>
								<td>${horarioCompromisso}</td>
								<td class="text-end">
									<s:url action="editarCompromissos" var="editar">
										<s:param name="compromissoVo.rowid" value="rowid"></s:param>
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
							<td colspan="6">
								<s:url action="novoCompromissos" var="novo"/>
								
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
				linkSim.href = 'excluirCompromissos.action?compromissoVo.rowid=' + id;
			});
		</script>
	</body>
</html>