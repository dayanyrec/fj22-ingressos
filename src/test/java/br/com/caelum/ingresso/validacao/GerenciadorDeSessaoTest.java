package br.com.caelum.ingresso.validacao;

import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.GerenciadorDeSessao;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class GerenciadorDeSessaoTest {
	@Test
	public void garanteQueNaoDevePermitirSessaoNoMesmoHorario() {
		Filme rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		Sala sala = new Sala("Sala 3D", BigDecimal.TEN);
		
		Sessao sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), sala, rogueOne);
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		
		assertFalse(gerenciador.cabe(sessaoDasDez));
	}
	
	@Test
	public void garanteQueNaoDevePermitirSessoesTerminandoDentroDoHorarioDeUmaSessaoJaExistente() {
		Filme rogueOne = new Filme("Rogue One", Duration.ofMinutes(120), "SCI-FI", BigDecimal.ONE);
		Sala sala = new Sala("Sala 3D", BigDecimal.TEN);
		
		Sessao sessaoDasDez = new Sessao(LocalTime.parse("10:00:00"), sala, rogueOne);
		Sessao novaSessao = new Sessao(sessaoDasDez.getHorario().minusHours(1), sala, rogueOne);
		List<Sessao> sessoes = Arrays.asList(sessaoDasDez);
		GerenciadorDeSessao gerenciador = new GerenciadorDeSessao(sessoes);
		
		assertFalse(gerenciador.cabe(novaSessao));
	}
}
