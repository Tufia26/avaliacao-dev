package br.com.soc.sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.soc.sistema.vo.AgendaVo;

public class AgendaDao extends Dao {

	public void insertAgenda(AgendaVo agendaVo){
		StringBuilder query = new StringBuilder("INSERT INTO agenda (nm_agenda, cd_disponibilidade) VALUES (?, ?)");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {
			
			int i = 1;
			ps.setString(i++, agendaVo.getNome());
			ps.setString(i++, agendaVo.getPeriodoDisponivel());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<AgendaVo> findAllAgendas(){
		StringBuilder query = new StringBuilder("SELECT rowid, nm_agenda, cd_disponibilidade FROM agenda");
		try(
			Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString());
			ResultSet rs = ps.executeQuery()){
			
			List<AgendaVo> agendas = new ArrayList<>();
			while (rs.next()) {
				AgendaVo vo = new AgendaVo();
				vo.setRowid(rs.getString("rowid"));
				vo.setNome(rs.getString("nm_agenda"));
				vo.setPeriodoDisponivel(rs.getString("cd_disponibilidade"));
				agendas.add(vo);
			}
			return agendas;
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Collections.emptyList();
	}
	
	public List<AgendaVo> findAllByNome(String nome){
		StringBuilder query = new StringBuilder("SELECT rowid, nm_agenda, cd_disponibilidade FROM agenda ")
								.append("WHERE lower(nm_agenda) LIKE lower(?)");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {
			int i = 1;
			
			ps.setString(i++, "%" + nome + "%");
			
			try(ResultSet rs = ps.executeQuery()) {
				List<AgendaVo> agendas = new ArrayList<>();
				
				while (rs.next()) {
					AgendaVo vo = new AgendaVo();
					vo.setRowid(rs.getString("rowid"));
					vo.setNome(rs.getString("nm_agenda"));
					vo.setPeriodoDisponivel(rs.getString("cd_disponibilidade"));
					agendas.add(vo);
				}
				return agendas;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return Collections.emptyList();
	}
	
	public AgendaVo findByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder("SELECT rowid, nm_agenda, cd_disponibilidade FROM agenda ")
								.append("WHERE rowid = ?");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {
			int i = 1;
			
			ps.setInt(i++, codigo);
			
			try (ResultSet rs = ps.executeQuery()) {
				AgendaVo vo = null;
				
				while (rs.next()) {
					vo = new AgendaVo();
					vo.setRowid(rs.getString("rowid"));
					vo.setNome(rs.getString("nm_agenda"));
					vo.setPeriodoDisponivel(rs.getString("cd_disponibilidade"));
				}
				return vo;
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateAgenda(AgendaVo agendaVo) {
		StringBuilder query = new StringBuilder("UPDATE agenda SET nm_agenda = ?, cd_disponibilidade = ? ")
								.append("WHERE rowid = ?");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {
			int i = 1;
			
			ps.setString(i++, agendaVo.getNome());
			ps.setString(i++, agendaVo.getPeriodoDisponivel());
			ps.setLong(i++, Long.parseLong(agendaVo.getRowid()));
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteByCodigo(Integer codigo) {
		StringBuilder query = new StringBuilder("DELETE FROM agenda ")
								.append("WHERE rowid = ?");
		
		try(Connection con = getConexao();
			PreparedStatement ps = con.prepareStatement(query.toString())) {
			int i = 1;
			
			ps.setInt(i++, codigo);
			ps.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}