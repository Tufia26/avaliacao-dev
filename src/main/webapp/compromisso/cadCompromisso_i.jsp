<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="UTF8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF8">
		<title><s:text name="label.titulo.pagina.cadastro"/></title>
		<link rel='stylesheet' href='webjars/bootstrap/5.1.3/css/bootstrap.min.css'>
	</head>
	<body class="bg-secondary">

		<div class="container">
			<!-- Alerta para mensagens de erro de validacao (ex: violacao de turno) -->
			<s:if test="hasActionErrors()">
				<div class="alert alert-danger alert-dismissible fade show mt-4" role="alert">
					<s:actionerror />
					<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
				</div>
			</s:if>

			<s:form action="/salvarCompromissos.action">

				<div class="card mt-4 mb-5">
					<div class="card-header">
						<div class="row">
							<div class="col-sm-5">
								<s:url action="todosCompromissos" var="todos"/>
								<a href="${todos}" class="btn btn-success">Compromissos</a>
							</div>
							
							<div class="col-sm">
								<h5 class="card-title">
									<s:if test="compromissoVo.rowid != null && !compromissoVo.rowid.isEmpty()">
										Editar Compromisso
									</s:if>
									<s:else>
										Novo Compromisso
									</s:else>
								</h5>
							</div>
						</div>
					</div>
					
					<div class="card-body">
						<!-- Codigo / ID (Somente Leitura) -->
						<div class="row align-items-center">
							<label for="id" class="col-sm-2 col-form-label text-center">
								Código:
							</label>	

							<div class="col-sm-2">
								<s:textfield cssClass="form-control" id="id" name="compromissoVo.rowid" readonly="true"/>							
							</div>	
						</div>
						
						<!-- Selecao do Funcionario -->
						<div class="row align-items-center mt-3">
							<label for="funcionario" class="col-sm-2 col-form-label text-center">
								Funcionário:
							</label>	

							<div class="col-sm-5">
								<s:select 
									cssClass="form-select" 
									id="funcionario" 
									name="compromissoVo.codigoFuncionario" 
									list="funcionarios" 
									headerKey="" 
									headerValue="Escolha..." 
									listKey="rowid" 
									listValue="nome"
									value="compromissoVo.codigoFuncionario"
								/>
							</div>	
						</div>

						<!-- Selecao da Agenda -->
						<div class="row align-items-center mt-3">
							<label for="agenda" class="col-sm-2 col-form-label text-center">
								Agenda:
							</label>	

							<div class="col-sm-5">
								<s:select 
									cssClass="form-select" 
									id="agenda" 
									name="compromissoVo.codigoAgenda" 
									list="agendas" 
									headerKey="" 
									headerValue="Escolha..." 
									listKey="rowid" 
									listValue="nome"
									value="compromissoVo.codigoAgenda"
								/>
							</div>	
						</div>

						<!-- Data do Compromisso (HTML5 Date Picker) -->
						<div class="row align-items-center mt-3">
							<label for="data" class="col-sm-2 col-form-label text-center">
								Data:
							</label>	

							<div class="col-sm-3">
								<s:textfield 
									type="date" 
									cssClass="form-control" 
									id="data" 
									name="compromissoVo.dataCompromisso"
								/>							
							</div>	
						</div>

						<!-- Horario do Compromisso (HTML5 Time Picker) -->
						<div class="row align-items-center mt-3">
							<label for="horario" class="col-sm-2 col-form-label text-center">
								Horário:
							</label>	

							<div class="col-sm-2">
								<s:textfield 
									type="time" 
									cssClass="form-control" 
									id="horario" 
									name="compromissoVo.horarioCompromisso"
								/>							
							</div>	
						</div>
					</div>

					<div class="card-footer">
						<div class="form-row">
							<button type="submit" class="btn btn-primary col-sm-4 offset-sm-1">Salvar</button>
							<button type="reset" class="btn btn-secondary col-sm-4 offset-sm-2">Limpar Formulario</button>
						</div>
					</div>
				</div>
			</s:form>			
		</div>
		
		<script src="webjars/bootstrap/5.1.3/js/bootstrap.bundle.min.js"></script>
	</body>
</html>