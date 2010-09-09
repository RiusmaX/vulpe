package br.com.activethread.gmn.ministerio.model.entity;

public enum Mes {

	JANEIRO, FEVEREIRO, MARCO, ABRIL, MAIO, JUNHO, JULHO, AGOSTO, SETEMBRO, OUTUBRO, NOVEMBRO, DEZEMBRO;

	public static Mes getMesPorValor(int valor) {
		for (Mes mes : values()) {
			if (mes.ordinal() == valor) {
				return mes;
			}
		}
		return JANEIRO;
	}
}
