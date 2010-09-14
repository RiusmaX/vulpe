package br.com.activethread.gmn.core.model.commons;

import java.util.Comparator;

import br.com.activethread.gmn.core.model.entity.Publicador;

public class PublicadorGrupoComparator implements Comparator<Publicador> {

	@Override
	public int compare(Publicador o1, Publicador o2) {
		return o1.getGrupo().getNome().compareTo(o2.getGrupo().getNome());
	}

}
