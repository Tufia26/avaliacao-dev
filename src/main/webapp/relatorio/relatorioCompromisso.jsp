<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF8">
		<title><s:text name="label.relatorios" /></title>
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
		            <a class="nav-link" href="${urlComp}"><s:text name="label.compromissos" /></a>
		            <a class="nav-link active" href="${urlRelat}"><s:text name="label.relatorios" /></a>
		        </div>
		    </div>
		</nav>

		<div class="container">
			<s:if test="hasActionErrors()">
				<div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
					<s:actionerror />
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</s:if>

			<div class="card mt-4 mb-4 shadow-sm">
				<div class="card-header bg-white">
					<h5 class="card-title mb-0 text-primary">
						<s:text name="label.relatorio.compromissos" />
					</h5>
				</div>
				<div class="card-body">
					<s:form id="formRelatorio" method="POST">
						<div class="row g-3 align-items-end">
							<div class="col-md-4">
								<label for="dtInicial" class="form-label font-weight-bold">
									<s:text name="label.data.inicial" />:
								</label>
								<s:textfield 
									type="date" 
									id="dtInicial" 
									name="dataInicial" 
									cssClass="form-control" 
									value="%{dataInicial}" 
								/>
							</div>

							<div class="col-md-4">
								<label for="dtFinal" class="form-label font-weight-bold">
									<s:text name="label.data.final" />:
								</label>
								<s:textfield 
									type="date" 
									id="dtFinal" 
									name="dataFinal" 
									cssClass="form-control" 
									value="%{dataFinal}" 
								/>
							</div>

							<div class="col-md-4 d-flex gap-2">
								<button 
								    type="submit" 
								    class="btn btn-primary flex-fill" 
								    formaction="filtrarRelatorios.action">
								    <s:text name="label.visualizar" />
								</button>
								
								<button 
								    type="submit" 
								    class="btn btn-success flex-fill" 
								    formaction="exportarExcelRelatorios.action">
								    <s:text name="label.exportar.excel" />
								</button>
							</div>
						</div>
					</s:form>
				</div>
			</div>

			<div class="row">
				<div class="col-12">
					<table class="table table-light table-striped align-middle shadow-sm">
						<thead>
							<tr>
								<th style="width: 80px;"><s:text name="label.id" /></th>
								<th><s:text name="label.funcionario" /></th>
								<th><s:text name="label.agenda" /></th>
								<th style="width: 150px;"><s:text name="label.data" /></th>
								<th style="width: 120px;"><s:text name="label.horario" /></th>
							</tr>
						</thead>
						<tbody>
							<s:if test="compromissos != null && compromissos.size() > 0">
								<s:iterator value="compromissos">
									<tr>
										<td>${rowid}</td>
										<td>${nomeFuncionario}</td>
										<td>${nomeAgenda}</td>
										<td>${dataFormatada}</td>
										<td>${horarioCompromisso}</td>
									</tr>
								</s:iterator>
							</s:if>
							<s:else>
								<tr>
									<td colspan="5" class="text-center py-4 text-muted">
										<s:text name="label.nenhum.registro" />
									</td>
								</tr>
							</s:else>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>