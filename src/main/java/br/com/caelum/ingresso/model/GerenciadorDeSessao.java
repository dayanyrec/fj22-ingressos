package br.com.caelum.ingresso.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class GerenciadorDeSessao {
	private List<Sessao> sessoesDaSala;

	public GerenciadorDeSessao(List<Sessao> sessoesDaSala) {
		this.sessoesDaSala = sessoesDaSala;
	}

	public boolean cabe(Sessao sessaoNova) {
		if(terminaAmanha(sessaoNova)) {
			return false;
		}
		return sessoesDaSala.stream()
				.noneMatch(sessaoExistente -> horarioIsConflitante(sessaoExistente, sessaoNova));
	}

	private boolean horarioIsConflitante(Sessao sessaoExistente, Sessao sessaoNova) {
		LocalDateTime i1 = getInicioSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime t1 = getTerminoSessaoComDiaDeHoje(sessaoExistente);
		LocalDateTime i2 = getInicioSessaoComDiaDeHoje(sessaoNova);
		LocalDateTime t2 = getTerminoSessaoComDiaDeHoje(sessaoNova);
		
		boolean sessaoNovaTerminaAntesDaExistente = t2.isBefore(i1);
		boolean sessaoNovaComecaDaExistente = t1.isBefore(i2);
		
		if(sessaoNovaTerminaAntesDaExistente || sessaoNovaComecaDaExistente) {
			return false;
		}
				
		return true;
	}

	private boolean terminaAmanha(Sessao sessao) {
		LocalDateTime terminoSessaoNova = getTerminoSessaoComDiaDeHoje(sessao);
		LocalDateTime ultimoSegundoDeHoje = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
		
		if(terminoSessaoNova.isAfter(ultimoSegundoDeHoje)) {
			return true;
		}
		
		return false;
	}

	private LocalDateTime getTerminoSessaoComDiaDeHoje(Sessao sessao) {
		LocalDateTime inicioSessaoNova = getInicioSessaoComDiaDeHoje(sessao);
		return inicioSessaoNova.plus(sessao.getFilme().getDuracao());
	}

	private LocalDateTime getInicioSessaoComDiaDeHoje(Sessao sessao) {
		LocalDate hoje = LocalDate.now();
		return sessao.getHorario().atDate(hoje);
	}

}
