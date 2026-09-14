package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.exception.TechnicalException;
import br.com.soc.sistema.vo.CompromissoVo;

public class CompromissoDao extends Dao {

	public void insertCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder("INSERT INTO compromisso ")
								.append("(cd_funcionario, cd_agenda, dt_compromisso, hr_compromisso) ")
								.append("VALUES (?, ?, ?, ?)");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setLong(1, Long.parseLong(compromissoVo.getCodigoFuncionario()));
			ps.setLong(2, Long.parseLong(compromissoVo.getCodigoAgenda()));
			ps.setString(3, compromissoVo.getDataCompromisso());
			ps.setString(4, compromissoVo.getHorarioCompromisso());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao inserir compromisso no banco de dados.", e);
		}
	}
	
	public List<CompromissoVo> findAllCompromissos() {
		StringBuilder query = new StringBuilder("SELECT ")
								.append("c.rowid, c.cd_funcionario, c.cd_agenda, c.dt_compromisso, c.hr_compromisso, ")
								.append("f.nm_funcionario, a.nm_agenda ")
								.append("FROM compromisso c ")
								.append("INNER JOIN funcionario f ON f.rowid = c.cd_funcionario ")
								.append("INNER JOIN agenda a ON a.rowid = c.cd_agenda ")
								.append("ORDER BY c.dt_compromisso ASC, c.hr_compromisso ASC");

		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery()) {

			List<CompromissoVo> compromissos = new ArrayList<>();
			while (rs.next()) {
				compromissos.add(mapearResultSetParaVo(rs));
			}

			return compromissos;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao buscar todos os compromissos.", e);
		}
	}
	
	public CompromissoVo findByCodigo(String rowid) {
		StringBuilder query = new StringBuilder("SELECT ")
								.append("c.rowid, c.cd_funcionario, c.cd_agenda, c.dt_compromisso, c.hr_compromisso, ")
								.append("f.nm_funcionario, a.nm_agenda ")
								.append("FROM compromisso c ")
								.append("INNER JOIN funcionario f ON f.rowid = c.cd_funcionario ")
								.append("INNER JOIN agenda a ON a.rowid = c.cd_agenda ")
								.append("WHERE c.rowid = ?");

		try(Connection con = getConexao();
			 PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setLong(1, Long.parseLong(rowid));

			try(ResultSet rs = ps.executeQuery()) {
				if(rs.next()) {
					return mapearResultSetParaVo(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao buscar compromisso por codigo.", e);
		}

		return null;
	}
	
	public List<CompromissoVo> findCompromissosByPeriodo(String dataInicial, String dataFinal) {
		StringBuilder query = new StringBuilder("SELECT ")
				.append("c.rowid, c.cd_funcionario, c.cd_agenda, c.dt_compromisso, c.hr_compromisso, ")
				.append("f.nm_funcionario, a.nm_agenda ")
				.append("FROM compromisso c ")
				.append("INNER JOIN funcionario f ON f.rowid = c.cd_funcionario ")
				.append("INNER JOIN agenda a ON a.rowid = c.cd_agenda ")
				.append("WHERE c.dt_compromisso BETWEEN ? AND ? ")
				.append("ORDER BY c.dt_compromisso ASC, c.hr_compromisso ASC");

		try (Connection con = getConexao();
			 PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setString(1, dataInicial);
			ps.setString(2, dataFinal);

			try (ResultSet rs = ps.executeQuery()) {
				List<CompromissoVo> lista = new ArrayList<>();
				while (rs.next()) {
					CompromissoVo vo = new CompromissoVo();
					vo.setRowid(rs.getString("rowid"));
					vo.setCodigoFuncionario(rs.getString("cd_funcionario"));
					vo.setCodigoAgenda(rs.getString("cd_agenda"));
					vo.setDataCompromisso(rs.getString("dt_compromisso"));
					vo.setHorarioCompromisso(rs.getString("hr_compromisso"));
					vo.setNomeFuncionario(rs.getString("nm_funcionario"));
					vo.setNomeAgenda(rs.getString("nm_agenda"));
					lista.add(vo);
				}
				return lista;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao buscar compromissos por período.", e);
		}
	}

	public void updateCompromisso(CompromissoVo compromissoVo) {
		StringBuilder query = new StringBuilder("UPDATE compromisso SET ")
								.append("cd_funcionario = ?, ")
								.append("cd_agenda = ?, ")
								.append("dt_compromisso = ?, ")
								.append("hr_compromisso = ? ")
								.append("WHERE rowid = ?");
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setLong(1, Long.parseLong(compromissoVo.getCodigoFuncionario()));
			ps.setLong(2, Long.parseLong(compromissoVo.getCodigoAgenda()));
			ps.setString(3, compromissoVo.getDataCompromisso());
			ps.setString(4, compromissoVo.getHorarioCompromisso());
			ps.setLong(5, Long.parseLong(compromissoVo.getRowid()));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao atualizar compromisso.", e);
		}
	}

	public void deleteByCodigo(String rowid) {
		StringBuilder query = new StringBuilder("DELETE FROM compromisso WHERE rowid = ?");

		try(Connection con = getConexao();
			 PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setLong(1, Long.parseLong(rowid));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao excluir compromisso.", e);
		}
	}

	public void deleteByCodigoFuncionario(String codigoFuncionario) {
		StringBuilder query = new StringBuilder("DELETE FROM compromisso ")
								.append("WHERE cd_funcionario = ?");

		try(Connection con = getConexao();
			 PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setLong(1, Long.parseLong(codigoFuncionario));
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao excluir compromissos do funcionario.", e);
		}
	}

	public boolean hasCompromissosByAgenda(String codigoAgenda) {
		StringBuilder query = new StringBuilder("SELECT COUNT(1) FROM compromisso ")
								.append("WHERE cd_agenda = ?");

		try(Connection con = getConexao();
			 PreparedStatement ps = con.prepareStatement(query.toString())) {

			ps.setLong(1, Long.parseLong(codigoAgenda));

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new TechnicalException("Erro ao verificar compromissos da agenda.", e);
		}

		return false;
	}

	private CompromissoVo mapearResultSetParaVo(ResultSet rs) throws SQLException {
		CompromissoVo vo = new CompromissoVo();
		vo.setRowid(rs.getString("rowid"));
		vo.setCodigoFuncionario(rs.getString("cd_funcionario"));
		vo.setCodigoAgenda(rs.getString("cd_agenda"));
		vo.setDataCompromisso(rs.getString("dt_compromisso"));
		vo.setHorarioCompromisso(rs.getString("hr_compromisso"));
		vo.setNomeFuncionario(rs.getString("nm_funcionario"));
		vo.setNomeAgenda(rs.getString("nm_agenda"));
		return vo;
	}
}