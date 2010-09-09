package br.com.activethread.gmn.ministerio.model.entity;

public enum Mes {

	JANEIRO(0), FEVEREIRO(1), MARCO(2), ABRIL(3), MAIO(4), JUNHO(5), JULHO(6), AGOSTO(7), SETEMBRO(8), OUTUBRO(9), NOVEMBRO(10), DEZEMBRO(11);

	int valor;

	Mes(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return this.valor;
	}

	public static Mes getMesPorValor(int valor) {
		for (Mes mes : values()) {
			if (mes.getValor() == valor) {
				return mes;
			}
		}
		return JANEIRO;
	}
}
